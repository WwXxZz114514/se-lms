package com.szuse.f4.controller;

import com.szuse.f4.model.Admin;
import com.szuse.f4.mapper.AdminMapper;
import com.szuse.f4.common.exception.BadRequestException;
import com.szuse.f4.common.exception.UnauthorizedException;
import com.szuse.f4.common.exception.Created;
import com.szuse.f4.common.ResponseJSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

  @Autowired
  private AdminMapper adminMapper;

  @PostMapping("/users/admin/login")
  public ResponseJSON login(HttpServletRequest request,
      @RequestBody Admin admin) {

    String username = admin.getUsername();
    String password = admin.getPassword();
    if (username == null || password == null) {
      throw new BadRequestException("Invalid parameters");
    }
    if (request.getSession().getAttribute("admin") != null) {
      throw new BadRequestException("Already logged in");
    }
    Admin u = adminMapper.getAdminByUsername(username);
    if (u == null || !u.getPassword().equals(password)) {
      throw new UnauthorizedException("Invalid username or password");
    }
    request.getSession().setAttribute("admin", u);
    return new ResponseJSON(200, "success");
  }

  @PostMapping("/users/admin/register")
  public ResponseJSON register(@RequestBody Admin admin) {
    String username = admin.getUsername();
    String tel = admin.getTel();
    String password = admin.getPassword();
    Admin newAdmin = new Admin();
    newAdmin.setUsername(username);
    newAdmin.setTel(tel);
    newAdmin.setPassword(password);
    try {
      adminMapper.insertAdmin(newAdmin);
    } catch (Exception e) {
      throw new BadRequestException("Admin already exists");
    }
    throw new Created("success");
  }

  @PostMapping("/users/admin/change-password")
  public ResponseJSON changePassword(HttpServletRequest request,
      @RequestParam("username") String username,
      @RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword) {
    Admin u = adminMapper.getAdminByUsername(username);
    if (request.getSession().getAttribute("admin") == null) {
      throw new UnauthorizedException("Not logged in");
    }
    if (u == null) {
      throw new BadRequestException("Admin does not exist");
    }
    if (!u.getPassword().equals(oldPassword)) {
      throw new BadRequestException("Invalid password");
    }
    u.setPassword(newPassword);
    adminMapper.updatePassword(u);
    return new ResponseJSON(200, "success");
  }

  @PostMapping("/users/admin/logout")
  public ResponseJSON logout(HttpServletRequest request) {
    request.getSession().removeAttribute("admin");
    return new ResponseJSON(200, "success");
  }

}
