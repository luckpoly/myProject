package com.brandsh.tiaoshi.model;

import java.io.Serializable;

/**
 * Created by sisi on 16/3/21.
 */
public class AboutUsJsonData implements Serializable {

    /**
     * respCode : 0
     * respMsg : ok
     * data : {"about_us_id":"1","company_name":"挑食网","company_description":"挑食公司简介","service_tel":"021-67900080","service_tel_time":"周一至周五 9:30--18:30","url":"www.mi56.com","weibo":"www.mi56.com","weixin":"mi56","email":"hu@163.com","address":"上海市杨浦区","img_url":"http://api.tiaoshi.86goodfood.com/uploads/images/head_photo.jpg","status":"0","add_time":"0","update_time":"0"}
     * debugInfo :
     */

    private int respCode;
    private String respMsg;
    /**
     * about_us_id : 1
     * company_name : 挑食网
     * company_description : 挑食公司简介
     * service_tel : 021-67900080
     * service_tel_time : 周一至周五 9:30--18:30
     * url : www.mi56.com
     * weibo : www.mi56.com
     * weixin : mi56
     * email : hu@163.com
     * address : 上海市杨浦区
     * img_url : http://api.tiaoshi.86goodfood.com/uploads/images/head_photo.jpg
     * status : 0
     * add_time : 0
     * update_time : 0
     */

    private DataEntity data;
    private String debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity implements Serializable{
        private String about_us_id;
        private String company_name;
        private String company_description;
        private String service_tel;
        private String service_tel_time;
        private String url;
        private String weibo;
        private String weixin;
        private String email;
        private String address;
        private String img_url;
        private String status;
        private String add_time;
        private String update_time;

        public void setAbout_us_id(String about_us_id) {
            this.about_us_id = about_us_id;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public void setCompany_description(String company_description) {
            this.company_description = company_description;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public void setService_tel_time(String service_tel_time) {
            this.service_tel_time = service_tel_time;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setWeibo(String weibo) {
            this.weibo = weibo;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getAbout_us_id() {
            return about_us_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getCompany_description() {
            return company_description;
        }

        public String getService_tel() {
            return service_tel;
        }

        public String getService_tel_time() {
            return service_tel_time;
        }

        public String getUrl() {
            return url;
        }

        public String getWeibo() {
            return weibo;
        }

        public String getWeixin() {
            return weixin;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getStatus() {
            return status;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }
    }
}
