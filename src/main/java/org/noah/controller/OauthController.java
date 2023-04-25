package org.noah.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.noah.entity.*;
import org.noah.enums.CommonError;
import org.noah.service.AuthService;
import org.noah.service.SysLogService;
import org.noah.service.UserService;
import org.noah.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OauthController {
    private final Logger log = LoggerFactory.getLogger(OauthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginParam loginParam) {
        log.info("Login. Username={}, Password={}", loginParam.getUsername(), loginParam.getPassword());

        // 登录状态检查
        loginCheck();

        // 获取密码
        String password = loginParam.getPassword();

        // 校验用户
        UserEntity userEntity = userService.getByUserName(loginParam.getUsername());
        if (userEntity == null) {
            return ResponseEntity.fail(CommonError.NO_USER);
        }
        // 校验密码
        password = JwtUtils.md5(password, userEntity.getSalt());
        if (!password.equals(userEntity.getPassword())) {
            return ResponseEntity.fail(CommonError.LOGIN_ERROR.getErrCode(), "密码错误", null);
        }

        // 密码正确，生成token
        String token = authService.create(userEntity);

        login(token);

        // 记录登录日志
        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setType(0);
        sysLogEntity.setUserId(userEntity.getId());
        sysLogEntity.setTime(new Date());
        sysLogService.save(sysLogEntity);

        log.info("用户登录成功");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("accessToken", token);
        return ResponseEntity.success(resultMap);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        log.info("Logout.");
        Subject subject = SecurityUtils.getSubject();
        AuthEntity authEntity = (AuthEntity) subject.getPrincipal();
        if (authEntity == null) {
            return ResponseEntity.fail(CommonError.LOGIN_LOST);
        }else {
            UserEntity userEntity = (UserEntity) authEntity.getAuth();

            if (subject.isAuthenticated()) {
                subject.logout();

                Date logoutDate = new Date();

                // 记录登出日志
                SysLogEntity sysLogEntity = new SysLogEntity();
                sysLogEntity.setType(1);
                sysLogEntity.setUserId(userEntity.getId());
                sysLogEntity.setTime(logoutDate);
                sysLogService.save(sysLogEntity);

                // 获取最近一次login时间
                sysLogEntity = sysLogService.getLastLoginDateByUserId(userEntity.getId());
                Date lastLoginDate = sysLogEntity.getTime();

                // 计算时间差
                long nd = 1000 * 24 * 60 * 60;
                long nh = 1000 * 60 * 60;
                long nm = 1000 * 60;
                long ns = 1000;
                // 获得两个时间的毫秒时间差异
                long diff = logoutDate.getTime() - lastLoginDate.getTime();
                // 计算差多少天
                long day = diff / nd;
                // 计算差多少小时
                long hour = diff % nd / nh;
                // 计算差多少分钟
                long min = diff % nd % nh / nm;
                // 计算差多少秒
                long sec = diff % nd % nh % nm / ns;
                Double min2 = sec / 60.0;
                log.info("UserId={}, time_diff={}", userEntity.getId(), min2);

                userEntity.setOnlineTime(userEntity.getOnlineTime() + min2);
                userService.updateById(userEntity);

                return ResponseEntity.success();
            }else {
                return ResponseEntity.fail(CommonError.LOGIN_LOST);
            }
        }
    }



    @PostMapping("/info")
    public ResponseEntity info() {
        log.info("Get Login User Info");
        Subject subject = SecurityUtils.getSubject();
        AuthEntity authEntity = (AuthEntity) subject.getPrincipal();
        if (authEntity == null) {
            return ResponseEntity.fail(CommonError.LOGIN_LOST);
        }else {
            UserEntity userEntity = (UserEntity) authEntity.getAuth();
            userEntity.setRoles(authEntity.getRoles());
            return ResponseEntity.success(userEntity);
        }
    }

    private void loginCheck() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
    }

    private void login(String token) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            subject.login(new AuthToken(token));
        }
    }
}
