package com.globits.demo.controller;

import com.globits.demo.model.Role;
import com.globits.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable("id") Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.orElse(null);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable("id") Long id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return "Deleted successfully role id: " + id;
    }
}
