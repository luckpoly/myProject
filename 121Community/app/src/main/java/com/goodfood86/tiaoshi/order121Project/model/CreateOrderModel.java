package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/4/11.
 */
public class CreateOrderModel {

    /**
     * respCode : 0
     * respMsg : OK
     * data : {"orderNo":"20151213456212"}
     */

    private int respCode;
    private String respMsg;
    /**
     * orderNo : 20151213456212
     */

    private DataEntity data;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
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

    public static class DataEntity {
        private String orderNo;

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderNo() {
            return orderNo;
        }
    }
}
