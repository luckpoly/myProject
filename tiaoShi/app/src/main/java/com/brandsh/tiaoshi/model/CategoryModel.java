package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by libokang on 15/10/23.
 */
public class CategoryModel {

    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : [{"id":"2","pid":"1","icon":"\"\"","code":"ShopDomestic","name":"国产水果","nodes":[]},{"id":"130","pid":"1","icon":"\"\"","code":"ShopImport","name":"进口商品","nodes":[]}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;

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

    private String debugInfo;
    /**
     * id : 2
     * pid : 1
     * icon : ""
     * code : ShopDomestic
     * name : 国产水果
     * nodes : []
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
        private String id;
        private String pid;
        private String icon;
        private String code;
        private String name;
        private List<?> nodes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<?> getNodes() {
            return nodes;
        }

        public void setNodes(List<?> nodes) {
            this.nodes = nodes;
        }
    }
}
