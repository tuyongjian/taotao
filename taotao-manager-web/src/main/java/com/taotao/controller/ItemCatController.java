package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by len on 2018/4/20.
 * 查询商品分类
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 商品类目 查询 父节点查询子节点
     * @param id
     * @return
     */
    @RequestMapping(value = "item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(
            @RequestParam(value = "id" ,defaultValue = "0")long id){
        List<EasyUITreeNode> list = this.itemCatService.getItemCatList(id);
        return list;
    }
}
