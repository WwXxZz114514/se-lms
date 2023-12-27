package com.szuse.f4.controller;

import com.szuse.f4.model.Admin;
import com.szuse.f4.mapper.AdminMapper;
import com.szuse.f4.common.exception.BadRequestException;
import com.szuse.f4.common.exception.UnauthorizedException;
import com.szuse.f4.common.ResponseJSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

  @Autowired
  private AdminMapper adminMapper;

  @PostMapping("/admin/login")
  public ResponseJSON login(HttpServletRequest request,
      HttpServletRequest response,
      @RequestParam("username") String username,
      @RequestParam("password") String password) {

    if (username == null || password == null) {
      throw new BadRequestException("Invalid parameters");
    }
    if (request.getSession().getAttribute("admin") != null) {
      throw new BadRequestException("Already logged in");
    }
    Admin u = adminMapper.getAdminByUsername(username);
    if (u == null || !u.getPassword().equals(password)) {
      throw new BadRequestException("Invalid username or password");
    }
    request.getSession().setAttribute("admin", u);
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/admin/register")
  public ResponseJSON register(@RequestParam("username") String username,
      @RequestParam("tel") String tel,
      @RequestParam("password") String password) {
    Admin u = adminMapper.getAdminByUsername(username);
    if (u != null) {
      throw new BadRequestException("Admin already exists");
    }
    Admin newAdmin = new Admin();
    newAdmin.setUsername(username);
    newAdmin.setTel(tel);
    newAdmin.setPassword(password);
    adminMapper.insertAdmin(newAdmin);
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/admin/change-password")
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
    return new ResponseJSON(0, "success");
  }

  @PostMapping("/admin/logout")
  public ResponseJSON logout(HttpServletRequest request) {
    request.getSession().removeAttribute("admin");
    return new ResponseJSON(0, "success");
  }

}
