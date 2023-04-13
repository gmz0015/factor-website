package org.noah.controller;

import lombok.extern.slf4j.Slf4j;
import org.noah.entity.ResponseEntity;
import org.noah.entity.UserEntity;
import org.noah.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity list() {
        log.info("Get role list.");
        return ResponseEntity.success(roleService.list());
    }
}
