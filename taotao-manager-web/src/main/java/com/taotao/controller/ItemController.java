package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by len on 2018/4/18.
 */
@Controller
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemService itemService;


    @RequestMapping(value = "item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = this.itemService.getItemById(itemId);
        logger.info("查询结果为[{}]",tbItem.toString());
        return tbItem;
    }

    //查询商品
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult result = this.itemService.getItemList(page,rows);
        return  result;
    }


    @RequestMapping(value = "/item/save")
    @ResponseBody
    public TaotaoResult saveItem(TbItem item,String desc){
        TaotaoResult result = itemService.addItem(item,desc);
        return result;
    }
}
