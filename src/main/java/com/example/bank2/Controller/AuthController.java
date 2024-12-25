package com.example.bank2.Controller;

import com.example.bank2.API.ApiResponse;
import com.example.bank2.Model.User;
import com.example.bank2.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //admin

    @GetMapping("/get-all-user")
    public ResponseEntity getAllUser (){
        return ResponseEntity.status(200).body(authService.getAllUser());
    }



}
