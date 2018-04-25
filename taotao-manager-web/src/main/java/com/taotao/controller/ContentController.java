package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by len on 2018/4/25.
 * 内容管理
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 添加内容
     * @param tbContent
     * @return
     */
    @RequestMapping(value = "/content/save")
    @ResponseBody
    public TaotaoResult addContent(TbContent tbContent){
        TaotaoResult result = contentService.addContent(tbContent);
        return result;
    }

    /**
     * 查询内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getItemCatList(
            @RequestParam(value = "categoryId" ,defaultValue = "0")Long categoryId,
            Integer page,Integer rows){
        EasyUIDataGridResult list = this.contentService.getContentList(categoryId,page,rows);
        return list;
    }
}
