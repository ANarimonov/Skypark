package com.anarimonov.skypark.controller;

import com.anarimonov.skypark.dto.AdminDto;
import com.anarimonov.skypark.payload.ApiResponse;
import com.anarimonov.skypark.payload.JwtUtil;
import com.anarimonov.skypark.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public HttpEntity<?> adminLogin(@RequestBody AdminDto adminDto) {
        UserDetails userDetails = adminService.loadUserByUsername(adminDto.getUsername());
        if (passwordEncoder.matches(adminDto.getPassword(),userDetails.getPassword())) {
            String token = JwtUtil.generateToken(adminDto.getUsername());
            return ResponseEntity.ok(new ApiResponse(token));
        }
        return ResponseEntity.badRequest().body("Username or password incorrect");
    }
}

