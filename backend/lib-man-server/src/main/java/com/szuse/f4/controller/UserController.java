package com.szuse.f4.controller;

import com.szuse.f4.model.User;
import com.szuse.f4.mapper.UserMapper;
import com.szuse.f4.common.exception.BadRequestException;
import com.szuse.f4.common.exception.UnauthorizedException;
import com.szuse.f4.common.ResponseJSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @PostMapping("/user/login")
  public ResponseJSON login(HttpServletRequest request,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password) {
    
    if (username == null || password == null) {
      throw new BadRequestException("Invalid parameters");
    }
    if (request.getSession().getAttribute("user") != null) {
      throw new BadRequestException("Already logged in");
    }
    User u = userMapper.getUserByUsername(username);
    if (u == null || !u.getPassword().equals(password)) {
      throw new BadRequestException("Invalid username or password");
    }
    request.getSession().setAttribute("user", u);
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/user/register")
  public ResponseJSON register(@RequestParam("username") String username,
                         @RequestParam("tel") String tel,
                         @RequestParam("password") String password) {
    User u = userMapper.getUserByUsername(username);
    if (u != null) {
      throw new BadRequestException("User already exists");
    }
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setTel(tel);
    newUser.setPassword(password);
    userMapper.insertUser(newUser);
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/user/change-password")
  public ResponseJSON changePassword(HttpServletRequest request,
                               @RequestParam("username") String username,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
    User u = userMapper.getUserByUsername(username);
    if (request.getSession().getAttribute("user") == null) {
      throw new UnauthorizedException("Not logged in");
    }
    if (u == null) {
      throw new BadRequestException("User does not exist");
    }
    if (!u.getPassword().equals(oldPassword)) {
      throw new BadRequestException("Invalid password");
    }
    u.setPassword(newPassword);
    userMapper.updatePassword(u);
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/user/logout")
  public ResponseJSON logout(HttpServletRequest request) {
    request.getSession().removeAttribute("user");
    return new ResponseJSON(0, "success");
  }

}
