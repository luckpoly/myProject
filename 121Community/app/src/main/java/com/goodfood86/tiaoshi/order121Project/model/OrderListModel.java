package com.goodfood86.tiaoshi.order121Project.model;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class OrderListModel {


    /**
     * respCode : 0
     * respMsg : OK
     * data : {"start":0,"limit":10,"prePage":1,"nextPage":2,"currentPage":1,"total":4,"list":[{"id":275,"orderNo":"20160414698405","commission":"8","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460627973,"desc":"","product":[{"productName":"哈哈哈哈","productUnit":"3公斤"}],"senderName":"妈","senderPhone":"15675500177","senderAddress":"一兆韦德","senderAddressDetail":"5楼202","senderLng":"121.48330688477","senderLat":"31.316019058228","recipientName":"吴旭","recipientPhone":"18010905991","recipientAddress":"丰镇小区","recipientAddressDetail":"22栋6楼","recipientLng":"121.47875213623","recipientLat":"31.305307388306","orderDistance":"1.686","orderDistance_unit":"m"},{"id":274,"orderNo":"20160414975918","commission":"20","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460626439,"desc":"","product":[{"productName":"哈哈哈","productUnit":"3公斤"}],"senderName":"mt1","senderPhone":"15282238419","senderAddress":"上海市宝山区三门路靠近复旦软件园(高境)","senderAddressDetail":"","senderLng":"121.48781585693","senderLat":"31.312454223633","recipientName":"范亚","recipientPhone":"15167105227","recipientAddress":"不错生煎王(霍山路店)","recipientAddressDetail":"","recipientLng":"121.50941467285","recipientLat":"31.252613067627","orderDistance":"8.041","orderDistance_unit":"m"},{"id":273,"orderNo":"20160414325928","commission":"8","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460625195,"desc":"面包很好吃","product":[{"productName":"面包","productUnit":"3公斤"}],"senderName":"吴旭","senderPhone":"18010905991","senderAddress":"三门家具","senderAddressDetail":"","senderLng":"121.49610137939","senderLat":"31.315584182739","recipientName":"李四","recipientPhone":"13567896400","recipientAddress":"三门居委会联络点","recipientAddressDetail":"","recipientLng":"121.48094177246","recipientLat":"31.311183929443","orderDistance":"1.866","orderDistance_unit":"m"},{"id":272,"orderNo":"20160414828284","commission":"10","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460615665,"desc":"","product":[{"productName":"问问????","productUnit":"3公斤"}],"senderName":"吴旭","senderPhone":"18010905991","senderAddress":"HapaLab","senderAddressDetail":"","senderLng":"121.51075744629","senderLat":"31.3150806427","recipientName":"苑心刚","recipientPhone":"13127563889","recipientAddress":"三门居委会联络点","recipientAddressDetail":"","recipientLng":"121.48094177246","recipientLat":"31.311183929443","orderDistance":"3.403","orderDistance_unit":"m"}]}
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
     * total : 4
     * list : [{"id":275,"orderNo":"20160414698405","commission":"8","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460627973,"desc":"","product":[{"productName":"哈哈哈哈","productUnit":"3公斤"}],"senderName":"妈","senderPhone":"15675500177","senderAddress":"一兆韦德","senderAddressDetail":"5楼202","senderLng":"121.48330688477","senderLat":"31.316019058228","recipientName":"吴旭","recipientPhone":"18010905991","recipientAddress":"丰镇小区","recipientAddressDetail":"22栋6楼","recipientLng":"121.47875213623","recipientLat":"31.305307388306","orderDistance":"1.686","orderDistance_unit":"m"},{"id":274,"orderNo":"20160414975918","commission":"20","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460626439,"desc":"","product":[{"productName":"哈哈哈","productUnit":"3公斤"}],"senderName":"mt1","senderPhone":"15282238419","senderAddress":"上海市宝山区三门路靠近复旦软件园(高境)","senderAddressDetail":"","senderLng":"121.48781585693","senderLat":"31.312454223633","recipientName":"范亚","recipientPhone":"15167105227","recipientAddress":"不错生煎王(霍山路店)","recipientAddressDetail":"","recipientLng":"121.50941467285","recipientLat":"31.252613067627","orderDistance":"8.041","orderDistance_unit":"m"},{"id":273,"orderNo":"20160414325928","commission":"8","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460625195,"desc":"面包很好吃","product":[{"productName":"面包","productUnit":"3公斤"}],"senderName":"吴旭","senderPhone":"18010905991","senderAddress":"三门家具","senderAddressDetail":"","senderLng":"121.49610137939","senderLat":"31.315584182739","recipientName":"李四","recipientPhone":"13567896400","recipientAddress":"三门居委会联络点","recipientAddressDetail":"","recipientLng":"121.48094177246","recipientLat":"31.311183929443","orderDistance":"1.866","orderDistance_unit":"m"},{"id":272,"orderNo":"20160414828284","commission":"10","ps_type":0,"status":0,"statusPay":0,"isAppraise":0,"createTime":1460615665,"desc":"","product":[{"productName":"问问????","productUnit":"3公斤"}],"senderName":"吴旭","senderPhone":"18010905991","senderAddress":"HapaLab","senderAddressDetail":"","senderLng":"121.51075744629","senderLat":"31.3150806427","recipientName":"苑心刚","recipientPhone":"13127563889","recipientAddress":"三门居委会联络点","recipientAddressDetail":"","recipientLng":"121.48094177246","recipientLat":"31.311183929443","orderDistance":"3.403","orderDistance_unit":"m"}]
     */

    private DataEntity data;
    private List<?> debugInfo;

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setDebugInfo(List<?> debugInfo) {
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

    public List<?> getDebugInfo() {
        return debugInfo;
    }

    public static class DataEntity {
        private int start;
        private int limit;
        private int prePage;
        private int nextPage;
        private int currentPage;
        private int total;
        /**
         * id : 275
         * orderNo : 20160414698405
         * commission : 8
         * ps_type : 0
         * status : 0
         * statusPay : 0
         * isAppraise : 0
         * createTime : 1460627973
         * desc :
         * product : [{"productName":"哈哈哈哈","productUnit":"3公斤"}]
         * senderName : 妈
         * senderPhone : 15675500177
         * senderAddress : 一兆韦德
         * senderAddressDetail : 5楼202
         * senderLng : 121.48330688477
         * senderLat : 31.316019058228
         * recipientName : 吴旭
         * recipientPhone : 18010905991
         * recipientAddress : 丰镇小区
         * recipientAddressDetail : 22栋6楼
         * recipientLng : 121.47875213623
         * recipientLat : 31.305307388306
         * orderDistance : 1.686
         * orderDistance_unit : m
         */

        private List<ListEntity> list;

        public void setStart(int start) {
            this.start = start;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getStart() {
            return start;
        }

        public int getLimit() {
            return limit;
        }

        public int getPrePage() {
            return prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int id;
            private String orderNo;
            private String commission;
            private String premium;
            private int ps_type;
            private int status;
            private int statusPay;
            private int isAppraise;
            private int createTime;
            private String desc;
            private String senderName;
            private String senderPhone;
            private String senderAddress;
            private String senderAddressDetail;
            private String senderLng;
            private String senderLat;
            private String recipientName;
            private String recipientPhone;
            private String recipientAddress;
            private String recipientAddressDetail;
            private String recipientLng;
            private String recipientLat;
            private String orderDistance;
            private String orderDistance_unit;
            /**
             * productName : 哈哈哈哈
             * productUnit : 3公斤
             */

            private List<ProductEntity> product;

            public void setId(int id) {
                this.id = id;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public void setPs_type(int ps_type) {
                this.ps_type = ps_type;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setStatusPay(int statusPay) {
                this.statusPay = statusPay;
            }

            public void setIsAppraise(int isAppraise) {
                this.isAppraise = isAppraise;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setSenderName(String senderName) {
                this.senderName = senderName;
            }

            public void setSenderPhone(String senderPhone) {
                this.senderPhone = senderPhone;
            }

            public void setSenderAddress(String senderAddress) {
                this.senderAddress = senderAddress;
            }

            public void setSenderAddressDetail(String senderAddressDetail) {
                this.senderAddressDetail = senderAddressDetail;
            }

            public void setSenderLng(String senderLng) {
                this.senderLng = senderLng;
            }

            public void setSenderLat(String senderLat) {
                this.senderLat = senderLat;
            }

            public void setRecipientName(String recipientName) {
                this.recipientName = recipientName;
            }

            public void setRecipientPhone(String recipientPhone) {
                this.recipientPhone = recipientPhone;
            }

            public void setRecipientAddress(String recipientAddress) {
                this.recipientAddress = recipientAddress;
            }

            public void setRecipientAddressDetail(String recipientAddressDetail) {
                this.recipientAddressDetail = recipientAddressDetail;
            }

            public void setRecipientLng(String recipientLng) {
                this.recipientLng = recipientLng;
            }

            public void setRecipientLat(String recipientLat) {
                this.recipientLat = recipientLat;
            }

            public void setOrderDistance(String orderDistance) {
                this.orderDistance = orderDistance;
            }

            public void setOrderDistance_unit(String orderDistance_unit) {
                this.orderDistance_unit = orderDistance_unit;
            }

            public void setProduct(List<ProductEntity> product) {
                this.product = product;
            }

            public int getId() {
                return id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public String getCommission() {
                return commission;
            }

            public int getPs_type() {
                return ps_type;
            }

            public int getStatus() {
                return status;
            }

            public int getStatusPay() {
                return statusPay;
            }

            public int getIsAppraise() {
                return isAppraise;
            }

            public int getCreateTime() {
                return createTime;
            }

            public String getDesc() {
                return desc;
            }

            public String getSenderName() {
                return senderName;
            }

            public String getSenderPhone() {
                return senderPhone;
            }

            public String getSenderAddress() {
                return senderAddress;
            }

            public String getSenderAddressDetail() {
                return senderAddressDetail;
            }

            public String getSenderLng() {
                return senderLng;
            }

            public String getSenderLat() {
                return senderLat;
            }

            public String getRecipientName() {
                return recipientName;
            }

            public String getRecipientPhone() {
                return recipientPhone;
            }

            public String getRecipientAddress() {
                return recipientAddress;
            }

            public String getRecipientAddressDetail() {
                return recipientAddressDetail;
            }

            public String getRecipientLng() {
                return recipientLng;
            }

            public String getRecipientLat() {
                return recipientLat;
            }

            public String getOrderDistance() {
                return orderDistance;
            }

            public String getOrderDistance_unit() {
                return orderDistance_unit;
            }

            public List<ProductEntity> getProduct() {
                return product;
            }

            public String getPremium() {
                return premium;
            }

            public void setPremium(String premium) {
                this.premium = premium;
            }

            public static class ProductEntity {
                private String productName;
                private String productUnit;

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public void setProductUnit(String productUnit) {
                    this.productUnit = productUnit;
                }

                public String getProductName() {
                    return productName;
                }

                public String getProductUnit() {
                    return productUnit;
                }
            }
        }
    }
}
