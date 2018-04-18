package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by len on 2018/4/18.
 */
@Controller
public class ItemController {

    @Autowired
    public ItemService itemService;


    @RequestMapping(value = "item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        return this.itemService.getItemById(itemId);
    }
}
