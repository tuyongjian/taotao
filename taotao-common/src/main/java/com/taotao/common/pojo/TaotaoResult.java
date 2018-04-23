package com.taotao.common.pojo;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2018/4/23.
 * 自定义响应实体类
 */
public class TaotaoResult implements Serializable{

    private static final ObjectMapper mapper = new ObjectMapper();

    private Integer status;

    private String msg;

    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public TaotaoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TaotaoResult( Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static TaotaoResult build(Integer status , String msg, Object data){
        return new TaotaoResult(status,msg,data);
    }

    public static TaotaoResult build(Integer status , String msg){
        return new TaotaoResult(status,msg,null);
    }

    public static TaotaoResult ok(Object data) {
        return new TaotaoResult(data);
    }

    public static TaotaoResult ok() {
        return new TaotaoResult(null);
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static TaotaoResult formatToPoJo(String jsonData,Class<?> clazz){
        try {
            if(clazz==null){
                return mapper.readValue(jsonData,TaotaoResult.class);
            }
            JsonNode jsonNode = mapper.readTree(jsonData);
            JsonNode data  = jsonNode.get("data");
            Object obj = null;
            if(clazz != null){
                if(data.isObject()){
                    obj = mapper.readValue(data.traverse(),clazz);
                }else if (data.isTextual()){
                    obj = mapper.readValue(data.asText(),clazz);
                }
            }
            return  build(jsonNode.get("status").intValue(),jsonNode.get("msg").asText(),obj);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static TaotaoResult format(String json) {
        try {
            return mapper.readValue(json, TaotaoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static TaotaoResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = mapper.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = mapper.readValue(data.traverse(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }


}
