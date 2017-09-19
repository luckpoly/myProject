package com.brandsh.tiaoshi.model;

/**
 * Created by apple on 16/2/23.
 */
public class MessageDetailJsonData {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : {"msgId":"4","type":2,"title":"提现","content":"提现成功","desc":"提现成功","viewStatus":1,"createTime":1465876978}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * msgId : 4
     * type : 2
     * title : 提现
     * content : 提现成功
     * desc : 提现成功
     * viewStatus : 1
     * createTime : 1465876978
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
