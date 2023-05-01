package org.noah.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.noah.entity.*;
import org.noah.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FactorLogService factorLogService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserEntity userEntity) {
        log.info("Register User. UserEntity={}", userEntity);
        userEntity = userService.register(userEntity.getUsername(), userEntity.getPassword());

        FactorLogEntity factorLogEntity = new FactorLogEntity();
        factorLogEntity.setTime(new Date());
        factorLogEntity.setUserId(userEntity.getId());
        factorLogEntity.setOnlineTime(0D);
        factorLogEntity.setScore(0D);
        factorLogService.save(factorLogEntity);

        return ResponseEntity.success();
    }

    @PostMapping("/list")
    public ResponseEntity list(@RequestBody PageEntity page) {
        log.info("Get User List. PageEntity={}", page);
        IPage<UserEntity> iPage = userService.getListByPage(page);

        return ResponseEntity.success(iPage);
    }

    @PostMapping("/list/log")
    public ResponseEntity listLog(@RequestBody PageEntity page) {
        log.info("Get User Log List. PageEntity={}", page);
        IPage<SysLogEntity> iPage = sysLogService.getListByPage(page);
        List<SysLogEntity> sysLogEntities = iPage.getRecords();
        for (SysLogEntity sysLogEntity : sysLogEntities) {
            UserEntity userEntity = userService.getById(sysLogEntity.getUserId());
            sysLogEntity.setUsername(userEntity.getUsername());
        }
        iPage.setRecords(sysLogEntities);
        return ResponseEntity.success(iPage);
    }

    @GetMapping("/info")
    public ResponseEntity info(@RequestParam Long id) {
        log.info("Get User info. ID={}", id);
        UserEntity userEntity = userService.getById(id);
        List<UserRoleEntity> userRoleEntities = userRoleService.getByUserId(id);
        Set<String> roles = new HashSet<>();
        for (UserRoleEntity userRoleEntity : userRoleEntities) {
            RoleEntity roleEntity = roleService.getById(userRoleEntity.getRoleId());
            roles.add(roleEntity.getId().toString());
        }
        userEntity.setRoles(roles);

        userEntity.setPassword(null);
        userEntity.setSalt(null);
        return ResponseEntity.success(userEntity);
    }

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody UserEntity userEntity) {
        log.info("Edit User info. UserEntity={}", userEntity);
        boolean result = userService.updateById(userEntity);

        // 删除旧的用户角色映射关系
        userRoleService.deleteByUserId(userEntity.getId());
        // 建立新的用户角色映射关系
        for (String roleId : userEntity.getRoles()) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userEntity.getId());
            userRoleEntity.setRoleId(Long.valueOf(roleId));
            userRoleService.save(userRoleEntity);
        }
        return ResponseEntity.success(result);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.info("Delete User. ID={}", id);

        boolean result = userRoleService.deleteByUserId(id);
        result = result && userService.removeById(id);

        return ResponseEntity.success(result);
    }
}
