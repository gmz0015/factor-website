package org.noah.controller;

import org.noah.entity.ResponseEntity;
import org.noah.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    private Logger log = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity list() {
        log.info("Get role list.");
        return ResponseEntity.success(roleService.list());
    }
}
