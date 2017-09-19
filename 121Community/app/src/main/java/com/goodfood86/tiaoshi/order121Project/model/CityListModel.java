package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CityListModel {


    /**
     * respCode : 0
     * respMsg : 获取地址成功！
     * data : {"city":"上海市|南京市"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * city : 上海市|南京市
     */

    private DataEntity data;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private String city;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }
}
