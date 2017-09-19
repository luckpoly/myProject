package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */

public class MyZixunListModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"id":"8","userName":"13601800020","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","name":"给我送","phone":"13601809554","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1482028852832.jpg","reply":"","intro":"给我送","createTime":"1482028850"},{"id":"7","userName":"13601800020","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","name":"活泼","phone":"123601809554","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1482028840167.jpg","reply":"","intro":"给我婆婆","createTime":"1482028838"},{"id":"6","userName":"13601800020","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","name":"哈密瓜","phone":"1313194978787","imgs":"","reply":"","intro":"好我情绪破","createTime":"1482028811"},{"id":"5","userName":"13601800020","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","name":"入口","phone":"136018","imgs":"","reply":"","intro":"后屋","createTime":"1481961866"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * id : 8
     * userName : 13601800020
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg
     * name : 给我送
     * phone : 13601809554
     * imgs : http://7xn4ae.com1.z0.glb.clouddn.com/1482028852832.jpg
     * reply :
     * intro : 给我送
     * createTime : 1482028850
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
        private String id;
        private String userName;
        private String icon;
        private String name;
        private String phone;
        private String imgs;
        private String reply;
        private String intro;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
