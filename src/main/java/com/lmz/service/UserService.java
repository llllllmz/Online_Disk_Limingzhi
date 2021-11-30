package com.lmz.service;

import com.lmz.mapper.UserMapper;
import com.lmz.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User login(String username, String password) {
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return userMapper.login(map);
    }

    public int register(String username, String password) {
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return userMapper.register(map);
    }

    public List<User> userlist() {
        return userMapper.userlist();
    }

    public void chmod(String username, String stat) {
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("stat",stat);
        userMapper.chmod(map);
    }
}
