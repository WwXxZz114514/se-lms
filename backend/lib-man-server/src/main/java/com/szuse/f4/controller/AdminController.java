package com.szuse.f4.controller;

import com.szuse.f4.model.Admin;
import com.szuse.f4.mapper.AdminMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

  @Autowired
  private AdminMapper adminMapper;

  @PostMapping("/admin/login")
  public String login(HttpServletRequest request,
                      @RequestParam("username") String username,
                      @RequestParam("tel") String tel,
                      @RequestParam("password") String password) {
    if (username == null || tel == null || password == null) {
      return "Invalid parameters";
    }
    if (request.getSession().getAttribute("admin") != null) {
      return "Already logged in";
    }
    Admin u = adminMapper.getAdminByUsername(username);
    if (u != null && u.getPassword().equals(password)) {
      request.getSession().setAttribute("admin", u);
      return "success";
    }
    return "Wrong UID or password";
  }

  @PostMapping("/admin/register")
  public String register(@RequestParam("username") String username,
                         @RequestParam("tel") String tel,
                         @RequestParam("password") String password) {
    Admin u = adminMapper.getAdminByUsername(username);
    if (u != null) {
      return "Admin already exists";
    }
    Admin newAdmin = new Admin();
    newAdmin.setUsername(username);
    newAdmin.setTel(tel);
    newAdmin.setPassword(password);
    adminMapper.insertAdmin(newAdmin);
    return "success";
  }

  @PostMapping("/admin/change-password")
  public String changePassword(HttpServletRequest request,
                               @RequestParam("username") String username,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
    Admin u = adminMapper.getAdminByUsername(username);
    if (request.getSession().getAttribute("admin") == null) {
      return "Not logged in";
    }
    if (u == null) {
      return "Admin does not exist";
    }
    if (!u.getPassword().equals(oldPassword)) {
      return "Wrong password";
    }
    u.setPassword(newPassword);
    adminMapper.updatePassword(u);
    return "success";
  }

  @PostMapping("/admin/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().removeAttribute("admin");
    return "success";
  }


}
