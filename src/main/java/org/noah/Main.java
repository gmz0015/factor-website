package org.noah;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.noah.entity.RoleEntity;
import org.noah.entity.UserEntity;
import org.noah.entity.UserRoleEntity;
import org.noah.service.RoleService;
import org.noah.service.UserRoleService;
import org.noah.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(value = {"org.noah"})
@MapperScan(value= {"org.noah.mapper"})
public class Main implements CommandLineRunner {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查默认角色是否存在
//        RoleEntity roleEntity = roleService.getByName("admin");
//        if (roleEntity == null) {
//            roleEntity = new RoleEntity();
//            roleEntity.setName("admin");
//            roleEntity.setStatus(1);
//            roleService.save(roleEntity);
//        }
//        // 检查默认用户是否存在
//        UserEntity userEntity = userService.getByUserName("admin");
//        if (userEntity == null) {
//            userEntity = new UserEntity();
//            userEntity.setUsername("admin");
//            userEntity.setPassword("64936f84dc9f352ac94b567e789fb28a");
//            userEntity.setSalt("93w8ro2D+JWBhHxMJ33Zb2Ba0LfVyk0evLdQEbGoXxk=");
//            userEntity.setStatus(1);
//            userService.save(userEntity);
//        }
//        // 检查默认用户角色关系是否存在
//        List<UserRoleEntity> userRoleEntities = userRoleService.getByUserId(userEntity.getId());
//        if (userRoleEntities.isEmpty()) {
//            UserRoleEntity userRoleEntity = new UserRoleEntity();
//            userRoleEntity.setRoleId(roleEntity.getId());
//            userRoleEntity.setUserId(userEntity.getId());
//            userRoleService.save(userRoleEntity);
//        }


        log.info("Service has started. 服务启动完成。");
    }
}