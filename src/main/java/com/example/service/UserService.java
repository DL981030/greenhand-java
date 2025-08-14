package com.example.service;

import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void createUser(UserRequest request);
}
