package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by len on 2018/4/18.
 */
public interface ItemService {

    TbItem getItemById(long itemId);

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getItemList(int page,int rows);

    /**
     * 添加商品
     * @param tbItem
     * @param desc
     * @return
     */
    TaotaoResult addItem(TbItem tbItem,String desc);

}
