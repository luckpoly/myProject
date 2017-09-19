package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/5/27.
 */
public class DianpuYouhuiModel {

    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : [{"discountId":"1","subtract":"10","full":"20","intro":""}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    /**
     * discountId : 1
     * subtract : 10
     * full : 20
     * intro :
     */

    private List<DataBean> data;


    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String discountId;
        private String subtract;
        private String full;
        private String intro;

        public String getDiscountId() {
            return discountId;
        }

        public void setDiscountId(String discountId) {
            this.discountId = discountId;
        }

        public String getSubtract() {
            return subtract;
        }

        public void setSubtract(String subtract) {
            this.subtract = subtract;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }
}
