package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by len on 2018/4/25.
 */
public interface ContentCategoryService {

    /**
     * 树状查询
     * @param parentId
     * @return
     */
    List<EasyUITreeNode> getContentCatList(long parentId);

    /**
     * 新增内容管理
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult insertContentCat(long parentId,String name);
}
