package com.chtwm.insurance.agency.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by YangDeming on 2018/3/22.
 */
@Controller
@RequestMapping("/demo")
@Api(description = "理顾宝DEMO")
public class DemoController {

    @RequestMapping("/home")
    @ResponseBody
    public Object  home( @RequestParam @ApiParam(required = true,value = "测试参数") String demo){
        demo += "asdfas";
        return  "demo:"+demo;
    }
}
