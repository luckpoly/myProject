package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class LlqDetailsModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"name":"测试","startTime":"1482090000","endTime":"1482090000","applyTime":"1481571600","minPeople":"0","maxPeople":"4","omPhone":"18300709012","omName":"万明玮","omQq":"","omWeixin":"","capita":"200","content":"去玩啊啊","imgs":"http://7xn4ae.com1.z0.glb.clouddn.com/1481614894844.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895066.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895286.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895515.jpg","isApply":"ON","applyCount":"3","applay":[{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com//Fmv0UAdZXEzbIkQWzLF4ii98T8wI","nickname":"娜娜","phone":"15376335161"},{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","nickname":"流量姐姐","phone":"13601800020"},{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853657508.jpg","nickname":"习惯","phone":"18300709012"}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * name : 测试
     * startTime : 1482090000
     * endTime : 1482090000
     * applyTime : 1481571600
     * minPeople : 0
     * maxPeople : 4
     * omPhone : 18300709012
     * omName : 万明玮
     * omQq :
     * omWeixin :
     * capita : 200
     * content : 去玩啊啊
     * imgs : http://7xn4ae.com1.z0.glb.clouddn.com/1481614894844.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895066.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895286.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481614895515.jpg
     * isApply : ON
     * applyCount : 3
     * applay : [{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com//Fmv0UAdZXEzbIkQWzLF4ii98T8wI","nickname":"娜娜","phone":"15376335161"},{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853333348.jpg","nickname":"流量姐姐","phone":"13601800020"},{"icon":"http://7xn4ae.com1.z0.glb.clouddn.com/1474853657508.jpg","nickname":"习惯","phone":"18300709012"}]
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
        private String name;
        private String startTime;
        private String endTime;
        private String applyTime;
        private String minPeople;
        private String maxPeople;
        private String omPhone;
        private String omName;
        private String omQq;
        private String omWeixin;
        private String capita;
        private String content;
        private String imgs;
        private String isApply;
        private String applyCount;
        /**
         * icon : http://7xn4ae.com1.z0.glb.clouddn.com//Fmv0UAdZXEzbIkQWzLF4ii98T8wI
         * nickname : 娜娜
         * phone : 15376335161
         */

        private List<ApplayBean> applay;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getMinPeople() {
            return minPeople;
        }

        public void setMinPeople(String minPeople) {
            this.minPeople = minPeople;
        }

        public String getMaxPeople() {
            return maxPeople;
        }

        public void setMaxPeople(String maxPeople) {
            this.maxPeople = maxPeople;
        }

        public String getOmPhone() {
            return omPhone;
        }

        public void setOmPhone(String omPhone) {
            this.omPhone = omPhone;
        }

        public String getOmName() {
            return omName;
        }

        public void setOmName(String omName) {
            this.omName = omName;
        }

        public String getOmQq() {
            return omQq;
        }

        public void setOmQq(String omQq) {
            this.omQq = omQq;
        }

        public String getOmWeixin() {
            return omWeixin;
        }

        public void setOmWeixin(String omWeixin) {
            this.omWeixin = omWeixin;
        }

        public String getCapita() {
            return capita;
        }

        public void setCapita(String capita) {
            this.capita = capita;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getIsApply() {
            return isApply;
        }

        public void setIsApply(String isApply) {
            this.isApply = isApply;
        }

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }

        public List<ApplayBean> getApplay() {
            return applay;
        }

        public void setApplay(List<ApplayBean> applay) {
            this.applay = applay;
        }

        public static class ApplayBean {
            private String icon;
            private String nickname;
            private String phone;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
