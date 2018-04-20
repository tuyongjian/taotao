package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2018/4/20.
 *
 * 适用于分页操作的公共类
 *
 */
public class EasyUIDataGridResult implements Serializable{

    /**
     * 一共多少条数据
     */
    private Long  total;

    /**
     * 数据集合
     */
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
