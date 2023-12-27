package com.szuse.f4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.szuse.f4.model.Area;
import com.szuse.f4.mapper.AreaMapper;
import com.szuse.f4.common.ResponseJSON;
import com.szuse.f4.common.exception.*;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class AreaController {
    
    @Autowired
    private AreaMapper areaMapper;

    @GetMapping("/area/{areaId}")
    public ResponseJSON getArea(@PathVariable("areaId") int areaId) {
        
        return new ResponseJSON(0, "success", areaMapper.getAreaByAreaId(areaId));
    }

    @GetMapping("/area")
    public ResponseJSON getAreas() {
        
        return new ResponseJSON(0, "success", areaMapper.getAreas());
    }

    @PostMapping("/area")
    public ResponseJSON insertArea(HttpServletRequest request,
        @RequestBody Area area) {
        
        if (request.getSession().getAttribute("admin") == null) {
            throw new UnauthorizedException("Authorized admin only");
        }
        areaMapper.insertArea(area);
        return new ResponseJSON(0, "success");
    }

    @PutMapping("/area")
    public ResponseJSON updateArea(HttpServletRequest request,
        @RequestBody Area area) {
        
        if (request.getSession().getAttribute("admin") == null) {
            throw new UnauthorizedException("Authorized admin only");
        }
        areaMapper.updateArea(area);
        return new ResponseJSON(0, "success");
    }

    @DeleteMapping("/area")
    public ResponseJSON deleteArea(HttpServletRequest request,
        @RequestParam("areaId") int areaId) {
        
        if (request.getSession().getAttribute("admin") == null) {
            throw new UnauthorizedException("Authorized admin only");
        }
        areaMapper.deleteAreaByAreaId(areaId);
        return new ResponseJSON(0, "success");
    }

}
