package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ImgBase64Model {

    /**
     * respCode : 0
     * respMsg : OK
     * data : {"key":"201604051517011459840621538962928199824009.jpg","url":"7xq12g.com1.z0.glb.clouddn.com/201604051517011459840621538962928199824009.jpg"}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * key : 201604051517011459840621538962928199824009.jpg
     * url : 7xq12g.com1.z0.glb.clouddn.com/201604051517011459840621538962928199824009.jpg
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
        private String key;
        private String url;

        public void setKey(String key) {
            this.key = key;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return key;
        }

        public String getUrl() {
            return url;
        }
    }
}
