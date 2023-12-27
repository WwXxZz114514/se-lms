package com.szuse.f4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.szuse.f4.model.Area;
import com.szuse.f4.mapper.AreaMapper;
import com.alibaba.fastjson2.JSONObject;
import com.szuse.f4.common.ResponseJSON;
import com.szuse.f4.common.exception.*;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class AreaController {

  @Autowired
  private AreaMapper areaMapper;

  @GetMapping("/areas")
  public ResponseJSON getAreas(@RequestParam("area_id") int areaId) {
    JSONObject returnObject = new JSONObject();
    Area[] areas;
    if (areaId != 0) {
      areas = new Area[] { areaMapper.getAreaByAreaId(areaId) };
    } else {
      areas = areaMapper.getAreas();
    }
    returnObject.put("areas_info", areas);
    return new ResponseJSON(200, "success", returnObject);
  }

  @PostMapping("/areas")
  public ResponseJSON insertArea(HttpServletRequest request,
      @RequestBody Area area) {

    if (request.getSession().getAttribute("admin") == null) {
      throw new UnauthorizedException("Authorized admin only");
    }
    areaMapper.insertArea(area);
    return new ResponseJSON(200, "success");
  }

  @PutMapping("/areas")
  public ResponseJSON updateArea(HttpServletRequest request,
      @RequestBody Area area) {

    if (request.getSession().getAttribute("admin") == null) {
      throw new UnauthorizedException("Authorized admin only");
    }
    areaMapper.updateArea(area);
    return new ResponseJSON(200, "success");
  }

  @DeleteMapping("/areas")
  public ResponseJSON deleteArea(HttpServletRequest request,
      @RequestParam("areaId") int areaId) {

    if (request.getSession().getAttribute("admin") == null) {
      throw new UnauthorizedException("Authorized admin only");
    }
    areaMapper.deleteAreaByAreaId(areaId);
    return new ResponseJSON(200, "success");
  }

}
