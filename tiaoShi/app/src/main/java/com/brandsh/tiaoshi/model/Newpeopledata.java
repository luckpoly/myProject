package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by tiashiwang on 2016/4/20.
 */
public class Newpeopledata {

    /**
     * resCode : SUCCESS
     * resnMsg : 操作成功
     * data : {"name":"新用户专享","code":"APPNewUser","icon":"","intro":"","content":"","link":"http://86goodfood.com/link/APPNewUser.html","createTime":"","docs":[]}
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    /**
     * name : 新用户专享
     * code : APPNewUser
     * icon :
     * intro :
     * content :
     * link : http://86goodfood.com/link/APPNewUser.html
     * createTime :
     * docs : []
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
        private String code;
        private String icon;
        private String intro;
        private String content;
        private String link;
        private String createTime;
        private List<?> docs;

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<?> getDocs() {
            return docs;
        }

        public void setDocs(List<?> docs) {
            this.docs = docs;
        }
    }
}
