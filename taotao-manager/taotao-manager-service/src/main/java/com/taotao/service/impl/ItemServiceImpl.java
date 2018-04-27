package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/4/18.
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    //测试消息发送
    @Resource(name="itemTopic")
    private Topic itemTopic;

    @Override
    public TbItem getItemById(long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {

        //分页处理
        PageHelper.startPage(page,rows);

        //查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);

        //创建返回结果对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);

        //取总记录数
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc) {

        final long itemId = IDUtils.getItemId();
        tbItem.setId(itemId);

        tbItem.setStatus((byte)1);
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        // 插入到商品表
        tbItemMapper.insert(tbItem);
        // 商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 插入商品描述
        tbItemDescMapper.insert(itemDesc);

        // 商品添加完成后发送一个MQ消息
        jmsTemplate.send(itemTopic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 创建一个消息对象
                // 要在匿名内部类访问局部变量itemId，itemId需要用final修饰
                TextMessage message = session.createTextMessage(itemId + "");
                System.out.println("发送成功");
                return message;
            }
        });

        return TaotaoResult.ok();
    }
}
