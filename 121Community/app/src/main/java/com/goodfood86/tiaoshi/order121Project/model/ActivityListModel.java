package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class ActivityListModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":20,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"13","list":[{"id":"25","name":"你猜\n是\n谁？","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1481883639830.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481883640152.jpg","startTime":"1484518740","endTime":"1484518740","applyTime":"1481840340","omName":"71907","userIcon":"http://7xn4ae.com1.z0.glb.clouddn.com/user_avatar_66-1464310558.jpg","applyCount":"5","isApply":"OFF"},{"id":"26","name":"冬游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1482198518538.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198521698.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198525605.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198528680.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198528983.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198529207.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198529418.jpg","startTime":"1484876760","endTime":"1484876760","applyTime":"1482198360","omName":"哈哈","userIcon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481016206883.jpg","applyCount":"3","isApply":"OFF"},{"id":"17","name":"明天中午一起晒太阳","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470989107472.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989107857.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989108246.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989108698.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989109025.jpg","startTime":"1481155680","endTime":"1470442080","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"18","name":"旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470989313974.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989314268.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989314471.jpg","startTime":"1473293280","endTime":"1470528480","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"22","name":"好想吃","img":"http://7xn4ae.com1.z0.glb.clouddn.com//Fn7q85yutHIkZy37U4sJzei5sEQg.jpg","startTime":"1471170300","endTime":"1471011948","applyTime":"1471001148","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"20","name":"明后哦哦哦","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470992355411.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470992356845.jpg","startTime":"1471121820","endTime":"1471035420","applyTime":"1470949020","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"14","name":"周末旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470983150254.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152072.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152369.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152794.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"15","name":"活动","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470984247053.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470984247414.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470984256083.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"16","name":"活动测试","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470985916392.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470985916684.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470985919302.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"19","name":"周末一日游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470991745014.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470991745296.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470991745533.jpg","startTime":"1473626820","endTime":"1473626820","applyTime":"1470948420","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"}]}
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 20
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 13
     * list : [{"id":"25","name":"你猜\n是\n谁？","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1481883639830.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481883640152.jpg","startTime":"1484518740","endTime":"1484518740","applyTime":"1481840340","omName":"71907","userIcon":"http://7xn4ae.com1.z0.glb.clouddn.com/user_avatar_66-1464310558.jpg","applyCount":"5","isApply":"OFF"},{"id":"26","name":"冬游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1482198518538.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198521698.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198525605.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198528680.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198528983.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198529207.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1482198529418.jpg","startTime":"1484876760","endTime":"1484876760","applyTime":"1482198360","omName":"哈哈","userIcon":"http://7xn4ae.com1.z0.glb.clouddn.com/1481016206883.jpg","applyCount":"3","isApply":"OFF"},{"id":"17","name":"明天中午一起晒太阳","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470989107472.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989107857.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989108246.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989108698.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989109025.jpg","startTime":"1481155680","endTime":"1470442080","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"18","name":"旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470989313974.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989314268.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470989314471.jpg","startTime":"1473293280","endTime":"1470528480","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"22","name":"好想吃","img":"http://7xn4ae.com1.z0.glb.clouddn.com//Fn7q85yutHIkZy37U4sJzei5sEQg.jpg","startTime":"1471170300","endTime":"1471011948","applyTime":"1471001148","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"20","name":"明后哦哦哦","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470992355411.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470992356845.jpg","startTime":"1471121820","endTime":"1471035420","applyTime":"1470949020","omName":"-","userIcon":"-","applyCount":"0","isApply":"OFF"},{"id":"14","name":"周末旅游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470983150254.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152072.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152369.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470983152794.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"15","name":"活动","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470984247053.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470984247414.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470984256083.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"16","name":"活动测试","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470985916392.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470985916684.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470985919302.jpg","startTime":"1473293280","endTime":"1473293280","applyTime":"1470614880","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"},{"id":"19","name":"周末一日游","img":"http://7xn4ae.com1.z0.glb.clouddn.com/1470991745014.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470991745296.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1470991745533.jpg","startTime":"1473626820","endTime":"1473626820","applyTime":"1470948420","omName":"-","userIcon":"-","applyCount":"2","isApply":"OFF"}]
     */

    private DataBean data;

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

    public static class DataBean {
        private int limit;
        private int prePage;
        private int start;
        private int currentPage;
        private int nextPage;
        private String totalCount;
        /**
         * id : 25
         * name : 你猜
         是
         谁？
         * img : http://7xn4ae.com1.z0.glb.clouddn.com/1481883639830.jpg,http://7xn4ae.com1.z0.glb.clouddn.com/1481883640152.jpg
         * startTime : 1484518740
         * endTime : 1484518740
         * applyTime : 1481840340
         * omName : 71907
         * userIcon : http://7xn4ae.com1.z0.glb.clouddn.com/user_avatar_66-1464310558.jpg
         * applyCount : 5
         * isApply : OFF
         */

        private List<ListBean> list;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String id;
            private String name;
            private String img;
            private String startTime;
            private String endTime;
            private String applyTime;
            private String omName;
            private String userIcon;
            private String applyCount;
            private String isApply;

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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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

            public String getOmName() {
                return omName;
            }

            public void setOmName(String omName) {
                this.omName = omName;
            }

            public String getUserIcon() {
                return userIcon;
            }

            public void setUserIcon(String userIcon) {
                this.userIcon = userIcon;
            }

            public String getApplyCount() {
                return applyCount;
            }

            public void setApplyCount(String applyCount) {
                this.applyCount = applyCount;
            }

            public String getIsApply() {
                return isApply;
            }

            public void setIsApply(String isApply) {
                this.isApply = isApply;
            }
        }
    }
}
