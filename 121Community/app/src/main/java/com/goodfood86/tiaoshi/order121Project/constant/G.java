package com.goodfood86.tiaoshi.order121Project.constant;

/**
 * Created by Administrator on 2016/3/24.
 */
public final class G {
    /**
     * 服务器数据
     *
     * @author wangwuhao
     */
    public static final class Host {
        // 主机地址
        private static final String HOST = "http://test.121-home.com/dataApi/";
//                private static final String HOST = "http://demo.121-home.com/dataApi/";
//                private static final String HOST = "http://api.121-home.com/dataApi/";
        private static final String HOST_V = "http://test.community.121-home.com/v1/";
        private static final String HOST_LINK = "http://test.community.121-home.com/";
        //获取预约详情url
        public static final String DATE_URl = HOST_LINK+"site/module-category-item?code=";
        //登录
        public static final String LOGIN = HOST + "default/login";
        //获取用户信息
        public static final String USERINFO = HOST + "default/userInfo";
        //获取注册验证码
        public static final String GET_YANZHENGMA = HOST + "default/verify";
        //注册
        public static final String REGISTER = HOST + "default/reg";

        //忘记密码
        public static final String RESTPASSWORD = HOST + "default/restPassword";
        //修改密码
        public static final String UPDATAPASSWORD = HOST + "default/modifyPassword";
        //广告推荐位
        public static final String GETPOSID = HOST + "default/getPosid";
        //图片base64上传
        public static String IMGBASE64 = HOST + "upload/imgBase64";
        //修改用户信息
        public static String UPDATE_INFO = HOST + "default/editUserInfo";
        //用户意见反馈
        public static final String COMMID = HOST + "default/addUserFeedback";
        //头像上传
        public static String UPDATA_HRADIMG = HOST + "default/uploadHeadShot";

        //历史地址列表
        public static String HISTORY_ADDRESSLIST = HOST + "default/listUserAddress";

        //添加用户历史地址
        public static String ADD_HISTORYADDRESS = HOST + "default/addUserAddress";

        //修改用户历史地址
        public static String EDIT_HISTORYADDRESS = HOST + "default/editUserAddress";

        //删除用户历史地址
        public static String DEL_HISTORYADDRESS = HOST + "default/removeUserAddress";

        //获取已经开通城市列表
        public static String CITYLIST = HOST + "default/getOpenCity";
        //好友短信分享
        public static String INVITE = HOST + "default/invite";
        //获取价格表
        public static String GET_MONEYTABLE = HOST + "order/getRulesProductPrice";

        //获取订单列表
        public static String ORDER_LIST = HOST + "order/userList";

        //创建订单
        public static String CREATE_ORDER = HOST + "order/add";

        //支付成功的回调
        public static final String SUCCESS_PAY = HOST + "order/payNotice";
        //获取交易明细
        public static String GET_Billlist = HOST + "order/userJournalAccount";

        //获取消息列表
        public static String GET_MSGLIST = HOST + "default/noticeList";
        //获取消息详情
        public static String GET_MSGMSG = HOST + "default/noticeDetail";
        //删除消息
        public static String DELETE_MSGLIST = HOST + "default/noticeDelete";
        //获取文档（轮播图、关于我们、常见问题）
        public static String GET_WORD = HOST + "default/words";

        //订单评价
        public static String ORDER_EVALUATE = HOST + "order/orderComment";

        //微信支付，下单
        public static String WEIXINPAY = HOST + "order/weixinPayOrder";
        //获取七牛配置
        public static final String GET_TOKEN = HOST_V + "pub/qiniu-info";
        //发布活动
        public static final String CREATE_ACTIVITY = HOST_V + "pub/custom-activity-create";
        //活动列表
        public static final String ACTIVITY_LIST = HOST_V + "pub/custom-activity";
        //活动内容
        public static final String ACTIVITY_DETAIL = HOST_V + "pub/custom-activity-detail";
        //报名活动
        public static final String BAOMING_ACTIVITY = HOST_V + "pub/custom-activity-apply-create";
        //模块分类
        public static final String MODULE_CATEGORY = HOST_V + "pub/module-category";
        //获取文档
        public static final String PUB_DOC = HOST_V + "pub/doc";
        //开始预约
        public static final String ACTION_YUYUE = HOST_V + "pub/module-category-make-create";
        //预约列表
        public static final String YUYUE_LIST = HOST_V + "pub/module-category-make";
        //首页活动列表
        public static final String CUSTOM_ACTYVITY = HOST_V + "pub/custom-activity-index";
        //24小时药店列表
        public static final String PHARMACY = HOST_V + "pub/pharmacy";
        //24小时药店详情
        public static final String PHARMACY_DETAIL = HOST_V + "pub/pharmacy-detail";
        // (外链)文档详细内容（资讯类）
        public static final String ITEM_DETAIL = HOST_LINK + "doc/items-detail?docItemId=";
       // 文档内容
       public static final String DOC_DETAIL = HOST_LINK + "doc/detail?code=";
        // 文档列表
        public static final String LIST_ITEM = HOST_LINK + "doc/items-list?code=";
        // 发布快速咨询
        public static final String CREATE_ZIXUN = HOST_V + "pub/medical-consult-create";
        //课程分类
        public static final String COURSE_TYPE = HOST_V + "pub/course-type";
        //课程列表
        public static final String COURSE_LIST = HOST_V + "pub/course";
        //课程详情
        public static final String COURSE_DETAIL = HOST_V + "pub/course-detail";
        //朋友圈
        public static final String BLOG = HOST_V + "pub/blog";
        //我的朋友圈
        public static final String MY_BLOG = HOST_V + "pub/blog-my";
        //删除朋友圈博文
        public static final String BLOG_DEL = HOST_V + "pub/blog-del";
        //发布朋友圈博文
        public static final String BLOG_FABU = HOST_V + "pub/blog-create";
        //休闲娱乐
        public static final String LEISURE = HOST_V + "pub/leisure";
        //休闲娱乐详情
        public static final String LEISURE_DETAIL = HOST_V + "pub/leisure-detail";
        //医疗咨询列表
        public static final String MEDICAL_CONSULT = HOST_V + "pub/medical-consult";
        //医疗咨询详情
        public static final String MEDICAL_DETAIL = HOST_V + "pub/medical-consult-detail";
        //删除医疗咨询
        public static final String MEDICAL_DELETE = HOST_V + "pub/medical-consult-del";
        //版本更新
        public static final String VERSION = HOST_V + "pub/version";

    }

    public static final class SP {
        // SharedPreferences名字
        public static final String APP_NAME = "PEISONG121";

        //是否第一次进入
        public static final String IS_FIRST_IN = "IS_FIRST_IN";

        //用户帐号密码信息
        public static final String LOGIN_NAME = "LOGIN_NAME";
        public static final String LOGIN_PWD = "LOGIN_PWD";
        //忘记密码页面唯一标识
        public static final int RORDOTPSW = 11;


    }
}
