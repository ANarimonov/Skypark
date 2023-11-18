package com.anarimonov.skypark.service;

import com.anarimonov.skypark.entity.Admin;
import com.anarimonov.skypark.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdminService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            List<SimpleGrantedAuthority> strings = new ArrayList<>();
            strings.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(admin.getUsername(), admin.getPassword(), strings);
        }
        throw new UsernameNotFoundException("Username not found");
    }

    public HttpEntity<?> deleteAdmin(String currentAdminId, String deleteAdminUsername) {
        if (!adminRepository.findById(Long.parseLong(currentAdminId)).get().getUsername().equalsIgnoreCase(deleteAdminUsername)) {
            adminRepository.deleteAdminByUsername(deleteAdminUsername);
            return ResponseEntity.ok("successfully deleted");
        } else return ResponseEntity.badRequest().body("Cannot remove himself from admin");
    }
}
