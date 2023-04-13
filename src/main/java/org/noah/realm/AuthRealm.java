package org.noah.realm;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.noah.entity.*;
import org.noah.service.AuthService;
import org.noah.service.RoleService;
import org.noah.service.UserRoleService;
import org.noah.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private AuthService authService;

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private UserRoleService userRoleService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 权限信息认证(包括角色以及权限)是用户访问controller的时候才进行验证(redis存储的此处权限信息)
     * 触发检测用户权限时才会调用此方法，例如checkRole,checkPermission
     *
     * @param principals 身份信息
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============Shiro权限认证开始============ [ roles、permissions ]==========");
        AuthEntity authEntity = null;
        if (principals != null) {
            authEntity = (AuthEntity) principals.getPrimaryPrincipal();
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(authEntity.getRoles());
        log.info("===============Shiro权限认证成功==============");
        return authorizationInfo;
    }

    /**
     * 用户信息认证是在用户进行登录的时候进行验证(不存redis)
     * 也就是说验证用户输入的账号和密码是否正确，错误抛出异常
     *
     * @param auth 用户登录的账号密码信息
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();
        log.info("doGetAuthenticationInfo 验证请求Token:{}", token);
        Claims claims = authService.parse(token);
        String type = (String) claims.get("type");
        String subject = claims.getSubject();

        // 用户Token验证
        UserEntity userEntity = userService.getById(JSON.parseObject(subject, UserEntity.class).getId());
        if (userEntity == null) {
            log.error("该用户不存在");
            throw new AuthenticationException();
        } else if (userEntity.getStatus() != 1) {
            log.error("该用户被禁用，请联系管理员");
            throw new AuthenticationException();
        } else {
            // 获取用户权限列表
            List<UserRoleEntity> userRoleEntities = userRoleService.getByUserId(userEntity.getId());
            Set<String> roles = new HashSet<>();
            for (UserRoleEntity userRoleEntity : userRoleEntities) {
                RoleEntity roleEntity = roleService.getById(userRoleEntity.getRoleId());
                roles.add(roleEntity.getName());
            }
            return new SimpleAuthenticationInfo(new AuthEntity<UserEntity>(userEntity.getId(), userEntity, roles), token, getName());
        }
    }

    @Override
    /** 最终的判断方法（权限集合是在Realm实现时添加的权限列表，如info.add("system:edit:1")） */
    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {
        log.debug("AuthorizationInfo: {}", info);
        log.debug("permission: {}", permission);
        Collection<Permission> perms = this.getPermissions(info);
        if (perms != null && !perms.isEmpty()) {
            Iterator permissionIterator = perms.iterator();

            while(permissionIterator.hasNext()) {
                Permission perm = (Permission) permissionIterator.next();
                if (perm.implies(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}