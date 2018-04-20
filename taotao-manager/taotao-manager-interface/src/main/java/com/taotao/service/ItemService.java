package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

/**
 * Created by len on 2018/4/18.
 */
public interface ItemService {

    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page,int rows);
}
