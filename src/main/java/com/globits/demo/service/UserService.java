package com.globits.demo.service;

import com.globits.demo.model.User;
import com.globits.demo.model.Role;
import com.globits.demo.repository.UserRepository;
import com.globits.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createUser(User user) {
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            java.util.Set<Role> existingRoles = new java.util.HashSet<>();
            for (Role role : user.getRoles()) {
                if (role.getId() != null) {
                    roleRepository.findById(role.getId()).ifPresent(existingRoles::add);
                }
            }
            user.setRoles(existingRoles);
        }
        return userRepository.save(user);

       // muốn thêm role mới mà không mất role cũ
        // User existingUser = userRepository.findById(user.getId()).orElseThrow();
        // Set<Role> currentRoles = existingUser.getRoles();
        // for (Role role : user.getRoles()) {
        //     if (role.getId() != null) {
        //         roleRepository.findById(role.getId()).ifPresent(currentRoles::add);
        //     }
        // }
        // existingUser.setRoles(currentRoles);
        // return userRepository.save(existingUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updated) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (updated.getPerson() == null) {
                updated.setPerson(existingUser.getPerson());
            }

            updated.setId(id);
            if (updated.getRoles() != null && !updated.getRoles().isEmpty()) {
                java.util.Set<Role> existingRoles = new java.util.HashSet<>();
                for (Role role : updated.getRoles()) {
                    if (role.getId() != null) {
                        roleRepository.findById(role.getId()).ifPresent(existingRoles::add);
                    }
                }
                updated.setRoles(existingRoles);
            }
            return userRepository.save(updated);
        }
        return null; 
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
