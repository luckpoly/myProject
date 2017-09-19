package com.goodfood86.tiaoshi.order121Project.model;

/**
 * Created by Administrator on 2016/12/13.
 */

public class YaodianDetailModel {

    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"id":"1","name":"药店测试","icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481013601595648.jpg","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481013617510606.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013617671549.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013617811550.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013618157009.jpg","address":"dddd","distance":"","addressDetail":"3333","intro":"eeeeeeeee","adviceBar":"eeeeeeeeeee","doctors":"eeeeeeeeeeeee"}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * id : 1
     * name : 药店测试
     * icon : http://7xn4ae.com1.z0.glb.clouddn.com/1481013601595648.jpg
     * imgs : http://7xn4ae.com1.z0.glb.clouddn.com/1481013617510606.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013617671549.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013617811550.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481013618157009.jpg
     * address : dddd
     * distance :
     * addressDetail : 3333
     * intro : eeeeeeeee
     * adviceBar : eeeeeeeeeee
     * doctors : eeeeeeeeeeeee
     */

    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String icon;
        private String imgs;
        private String address;
        private String distance;
        private String addressDetail;
        private String intro;
        private String adviceBar;
        private String doctors;
        private String tel;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getAdviceBar() {
            return adviceBar;
        }

        public void setAdviceBar(String adviceBar) {
            this.adviceBar = adviceBar;
        }

        public String getDoctors() {
            return doctors;
        }

        public void setDoctors(String doctors) {
            this.doctors = doctors;
        }
    }
}
