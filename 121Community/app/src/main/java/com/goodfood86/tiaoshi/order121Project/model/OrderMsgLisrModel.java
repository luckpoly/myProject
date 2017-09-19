package com.goodfood86.tiaoshi.order121Project.model;


import java.util.List;

/**
 * Created by tiashiwang on 2016/4/8.
 */
public class OrderMsgLisrModel {


    /**
     * respCode : 0
     * respMsg : OK
     * data : {"total":2,"list":[{"id":211,"no":"131201604131158108012879","addTime":"2016-04-13 11:58:10","amount":8,"opt":-1,"jType":4},{"id":210,"no":"131201604131158098487505","addTime":"2016-04-13 11:58:09","amount":8,"opt":-1,"jType":4}]}
     * debugInfo : []
     */

    private int respCode;
    private String respMsg;
    /**
     * total : 2
     * list : [{"id":211,"no":"131201604131158108012879","addTime":"2016-04-13 11:58:10","amount":8,"opt":-1,"jType":4},{"id":210,"no":"131201604131158098487505","addTime":"2016-04-13 11:58:09","amount":8,"opt":-1,"jType":4}]
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
        private int total;
        /**
         * id : 211
         * no : 131201604131158108012879
         * addTime : 2016-04-13 11:58:10
         * amount : 8
         * opt : -1
         * jType : 4
         */

        private List<ListBean> list;

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
            private String no;
            private String addTime;
            private String amount;
            private String opt;
            private String jType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getOpt() {
                return opt;
            }

            public void setOpt(String opt) {
                this.opt = opt;
            }

            public String getJType() {
                return jType;
            }

            public void setJType(String jType) {
                this.jType = jType;
            }
        }
    }
}