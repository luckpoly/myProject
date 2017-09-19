package com.brandsh.tiaoshi.model;

import java.util.List;

/**
 * Created by libokang on 15/10/19.
 */
public class CityModel {


    /**
     * respCode : SUCCESS
     * respMsg : 操作成功
     * data : [{"cityId":"310100","code":"021","name":"上海市","shortName":"上海","lng":"121.472644","lat":"31.231706","pinyin":"Shanghai","openStatus":"YES"},{"cityId":"110100","code":"010","name":"北京市","shortName":"北京","lng":"116.405285","lat":"39.904989","pinyin":"Beijing","openStatus":"NO"},{"cityId":"120100","code":"022","name":"天津市","shortName":"天津","lng":"117.190182","lat":"39.125596","pinyin":"Tianjin","openStatus":"NO"},{"cityId":"130100","code":"0311","name":"石家庄市","shortName":"石家庄","lng":"114.502461","lat":"38.045474","pinyin":"Shijiazhuang","openStatus":"NO"},{"cityId":"130200","code":"0315","name":"唐山市","shortName":"唐山","lng":"118.175393","lat":"39.635113","pinyin":"Tangshan","openStatus":"NO"},{"cityId":"130300","code":"0335","name":"秦皇岛市","shortName":"秦皇岛","lng":"119.586579","lat":"39.942531","pinyin":"Qinhuangdao","openStatus":"NO"},{"cityId":"130400","code":"0310","name":"邯郸市","shortName":"邯郸","lng":"114.490686","lat":"36.612273","pinyin":"Handan","openStatus":"NO"},{"cityId":"130500","code":"0319","name":"邢台市","shortName":"邢台","lng":"114.508851","lat":"37.0682","pinyin":"Xingtai","openStatus":"NO"},{"cityId":"130600","code":"0312","name":"保定市","shortName":"保定","lng":"115.482331","lat":"38.867657","pinyin":"Baoding","openStatus":"NO"},{"cityId":"130700","code":"0313","name":"张家口市","shortName":"张家口","lng":"114.884091","lat":"40.811901","pinyin":"Zhangjiakou","openStatus":"NO"},{"cityId":"130800","code":"0314","name":"承德市","shortName":"承德","lng":"117.939152","lat":"40.976204","pinyin":"Chengde","openStatus":"NO"},{"cityId":"130900","code":"0317","name":"沧州市","shortName":"沧州","lng":"116.857461","lat":"38.310582","pinyin":"Cangzhou","openStatus":"NO"},{"cityId":"131000","code":"0316","name":"廊坊市","shortName":"廊坊","lng":"116.713873","lat":"39.529244","pinyin":"Langfang","openStatus":"NO"}]
     * debugInfo : code=1|msg=
     */

    private String respCode;
    private String respMsg;
    private String debugInfo;
    /**
     * cityId : 310100
     * code : 021
     * name : 上海市
     * shortName : 上海
     * lng : 121.472644
     * lat : 31.231706
     * pinyin : Shanghai
     * openStatus : YES
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
        private String cityId;
        private String code;
        private String name;
        private String shortName;
        private String lng;
        private String lat;
        private String pinyin;
        private String openStatus;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = openStatus;
        }
    }
}
