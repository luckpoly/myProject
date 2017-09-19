package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17.
 */

public class HomeCategoryGVModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":20,"pid":0,"name":"水果","code":"Fruit","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1469688971897114.jpg","imgs":"","nodes":[]},{"id":19,"pid":0,"name":"果汁","code":"Juice","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1469688981196663.jpg","imgs":"","nodes":[]},{"id":21,"pid":0,"name":"零食","code":"Snacks","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1469688951428043.jpg","imgs":"","nodes":[]},{"id":76,"pid":0,"name":"蔬菜粮油","code":"Grain","icon":"","imgs":"","nodes":[]}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 20
     * pid : 0
     * name : 水果
     * code : Fruit
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1469688971897114.jpg
     * imgs :
     * nodes : []
     */

    private List<DataBean> data;

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
        private int id;
        private int pid;
        private String name;
        private String code;
        private String icon;
        private String imgs;
        private List<?> nodes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public List<?> getNodes() {
            return nodes;
        }

        public void setNodes(List<?> nodes) {
            this.nodes = nodes;
        }
    }
}
