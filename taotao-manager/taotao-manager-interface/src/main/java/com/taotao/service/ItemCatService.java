package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by len on 2018/4/20.
 */
public interface ItemCatService  {

    /**
     *
     * 树结构查询
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getItemCatList(long parentId);
}
