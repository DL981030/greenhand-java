package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.UserRequest;
import com.example.dto.UserResponse;
import com.example.entity.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Lock lock = new ReentrantLock();

    @Autowired
    private UserDao userDao;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userDao.selectById(id);
        UserResponse response = new UserResponse();
        if (user != null) {
            BeanUtils.copyProperties(user, response);
        }
        return response;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userDao.selectList(null);
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(user, response);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createUser(UserRequest request) {
        lock.lock();
        try {
            User user = new User();
            BeanUtils.copyProperties(request, user);
            userDao.insert(user);
            logger.info("User created: {}", user.getName());
        } finally {
            lock.unlock();
        }
    }
}
