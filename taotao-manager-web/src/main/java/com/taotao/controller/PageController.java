package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by len on 2018/4/19.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/showIndex")
    public String showIndex(){
        return "index";
    }

    /**
     * 页面跳转
     * @param page
     * @return
     */
    @RequestMapping(value = "{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
