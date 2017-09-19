package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by apple on 16/2/23.
 */
public class MyMessageListJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"limit":10,"prePage":0,"start":0,"currentPage":1,"nextPage":2,"totalCount":"2","list":[{"msgId":4,"type":2,"title":"提现","content":"提现成功","desc":"提现成功","viewStatus":0,"createTime":1465876978},{"msgId":3,"type":2,"title":"提现","content":"提现成功","desc":"提现成功","viewStatus":0,"createTime":1465876978}]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * limit : 10
     * prePage : 0
     * start : 0
     * currentPage : 1
     * nextPage : 2
     * totalCount : 2
     * list : [{"msgId":4,"type":2,"title":"提现","content":"提现成功","desc":"提现成功","viewStatus":0,"createTime":1465876978},{"msgId":3,"type":2,"title":"提现","content":"提现成功","desc":"提现成功","viewStatus":0,"createTime":1465876978}]
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
        private int limit;
        private int prePage;
        private int start;
        private int currentPage;
        private int nextPage;
        private String totalCount;
        /**
         * msgId : 4
         * type : 2
         * title : 提现
         * content : 提现成功
         * desc : 提现成功
         * viewStatus : 0
         * createTime : 1465876978
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
            private String msgId;
            private String type;
            private String title;
            private String content;
            private String desc;
            private String viewStatus;
            private String createTime;

            public String getMsgId() {
                return msgId;
            }

            public void setMsgId(String msgId) {
                this.msgId = msgId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getViewStatus() {
                return viewStatus;
            }

            public void setViewStatus(String viewStatus) {
                this.viewStatus = viewStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
