package com.szuse.f4.controller;

import com.szuse.f4.model.User;
import com.szuse.f4.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @PostMapping("/user/login")
  public String login(HttpServletRequest request,
                      @RequestParam("username") String username,
                      @RequestParam("tel") String tel,
                      @RequestParam("password") String password) {
    if (username == null || tel == null || password == null) {
      return "Invalid parameters";
    }
    if (request.getSession().getAttribute("user") != null) {
      return "Already logged in";
    }
    User u = userMapper.getUserByUsername(username);
    if (u != null && u.getPassword().equals(password)) {
      request.getSession().setAttribute("user", u);
      return "success";
    }
    return "Wrong UID or password";
  }

  @PostMapping("/user/register")
  public String register(@RequestParam("username") String username,
                         @RequestParam("tel") String tel,
                         @RequestParam("password") String password) {
    User u = userMapper.getUserByUsername(username);
    if (u != null) {
      return "User already exists";
    }
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setTel(tel);
    newUser.setPassword(password);
    userMapper.insertUser(newUser);
    return "success";
  }

  @PostMapping("/user/change-password")
  public String changePassword(HttpServletRequest request,
                               @RequestParam("username") String username,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
    User u = userMapper.getUserByUsername(username);
    if (request.getSession().getAttribute("user") == null) {
      return "Not logged in";
    }
    if (u == null) {
      return "User does not exist";
    }
    if (!u.getPassword().equals(oldPassword)) {
      return "Wrong password";
    }
    u.setPassword(newPassword);
    userMapper.updatePassword(u);
    return "success";
  }

  @PostMapping("/user/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().removeAttribute("user");
    return "success";
  }

}
