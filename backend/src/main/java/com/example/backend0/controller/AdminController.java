package com.example.backend0.controller;

import com.example.backend0.entity.Platform;
import com.example.backend0.result.Result;
import com.example.backend0.result.ResultFactory;
import com.example.backend0.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description
 **/
@RestController
public class AdminController {
    @Autowired
    PlatformService platformService;
    @PostMapping("/admin/platform/add")
    public Result upperPlatform(@RequestParam("platformName")String platformName){
        Platform platform=new Platform();
        platform.setPlatformName(platformName);
        return ResultFactory.buildSuccessResult(platformService.save(platform));
    }

}
