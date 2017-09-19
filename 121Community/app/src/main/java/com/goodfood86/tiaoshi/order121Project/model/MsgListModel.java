package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/12.
 */
public class MsgListModel {

    /**
     * respCode : 0
     * respMsg : OK
     * data : {"start":0,"limit":10,"prePage":1,"nextPage":2,"currentPage":1,"total":1,"list":[{"id":471,"type":0,"typeName":"普通消息","subject":"普通消息","content":"test 测试","status":0,"statusDesc":"未读","dateTime":"2016-04-12 10:17:23"}]}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * start : 0
     * limit : 10
     * prePage : 1
     * nextPage : 2
     * currentPage : 1
     * total : 1
     * list : [{"id":471,"type":0,"typeName":"普通消息","subject":"普通消息","content":"test 测试","status":0,"statusDesc":"未读","dateTime":"2016-04-12 10:17:23"}]
     */

    private DataBean data;
    private List<?> debugInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<?> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        private int start;
        private int limit;
        private int prePage;
        private int nextPage;
        private int currentPage;
        private int total;
        /**
         * id : 471
         * type : 0
         * typeName : 普通消息
         * subject : 普通消息
         * content : test 测试
         * status : 0
         * statusDesc : 未读
         * dateTime : 2016-04-12 10:17:23
         */

        private List<ListBean> list;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

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

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int id;
            private int type;
            private String typeName;
            private String subject;
            private String content;
            private int status;
            private String statusDesc;
            private String dateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatusDesc() {
                return statusDesc;
            }

            public void setStatusDesc(String statusDesc) {
                this.statusDesc = statusDesc;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }
        }
    }
}
