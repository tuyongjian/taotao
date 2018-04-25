package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/4/25.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(long parentId) {
        // 根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        // 设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        // 返回结果为List
        List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            // 添加到列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCat(long parentId, String name) {
        // 创建一个内容分类对象
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        // 新添加的节点都是叶子节点
        contentCategory.setIsParent(false);
        // 排序方法默认设置为1
        contentCategory.setSortOrder(1);
        // 分类状态：1(正常)，2(删除)
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        // 插入节点
        tbContentCategoryMapper.insert(contentCategory);
        // 判断父节点是否为叶子节点，为何要判断？这里如果父节点为叶子节点增加一个子节点之后要更新为父节点
        TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            // 更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        return TaotaoResult.ok(contentCategory); // contentCategory对象里面是包含id属性的
    }
}
