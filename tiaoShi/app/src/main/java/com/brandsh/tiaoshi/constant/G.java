package com.brandsh.tiaoshi.constant;

/**
 * 该类为自己维护的本应用用到的一些全局常量， 具体的局部变量应再定义一个静态内部类来保存。 Globle的首字母，类似于R文件
 * @author libokang
 */
public final class G {
    /**
     * 服务器数据
     *
     * @author libokang
     */
    public static final class Host {
        // 主机地址api
        private static final String HOST = "http://api.86goodfood.com/v1/";
//         主机地址demo
//        private static final String HOST = "https://demo.86goodfood.com/v1/";
//        private static final String HOST = "http://test.86goodfood.com/v1/";
        private static final String HOST_LINK = "http://api.86goodfood.com/link/";

        //验证码
        public static final String GET_CODE = HOST + "pub/verify-code";

        //获取产品分类类表
        public static final String GOODS_CATEGORY = HOST + "pub/goods-type";

        //获取产品分类内容
        public static final String GOODS_CATEGORY_ITEM = HOST + "pub/goods-type-item";

        // 登录
        public static final String LOGIN = HOST + "user/login";

        // 注册
        public static final String REGISTER = HOST + "user/reg";

        //找回密码
        public static final String FORGET_PWD = HOST + "user/forget-password";

        //获取用户个人信息
        public static final String USER_INFO = HOST + "user/user-info";

        // 发现列表
        public static final String FIND = HOST + "user/goods-list";

        // 店铺内所有商品列表
        public static final String STORE_PRODUCT_LIST = HOST + "user/goods-list";

        //店铺详情
        public static final String STORE_DETAIL = HOST + "user/shop-detail";

        //商品详情
        public static final String PRODUCT_DETAIL = HOST + "pub/goods-detail";

        //关注或取消关注店铺
        public static final String GUANZHU = HOST + "user/follow";

        //个人关注列表
        public static final String GUANZHU_LIST = HOST + "user/follow-list";

        //我的消息列表
        public static final String MY_MESSAGE = HOST + "user/msg";

        //删除消息
        public static final String DELETE_MESSAGE = HOST + "user/delete-msg";

        //消息详情
        public static final String GET_MESSAGE_DETAIL = HOST + "user/msg-detail";

        //修改个人资料（昵称、头像、性别）
        public static final String CHANGE_USERINFO = HOST + "user/update-user-info";

        // 首页店铺列表
        public static final String HOME_STORE_LIST = HOST + "user/get-shop-list";

        //添加收货地址
        public static final String ADD_ADDRESS = HOST + "user/add-address";

        //获取用户地址列表
        public static final String ADDRESS_LIST = HOST + "user/get-address";

        //编辑收货地址
        public static final String MODIFY_ADDRESS = HOST + "user/update-address";

        //删除收货地址
        public static final String DEL_ADDRESS = HOST + "user/delete-address";

        //确认订单
        public static final String CONFIRM_ORDER = HOST + "pub/create-order";

        //我的订单
        public static final String MY_ORDER = HOST + "user/order-list";
        //我的果汁分享订单
        public static final String MY_JUICE_ORDER = HOST + "user/order-share-list";

        //添加评价
        public static final String ADD_EVALUATION = HOST + "user/add-evaluation";

        //商铺评价列表
        public static final String LIST_EVALUATION = HOST + "user/evaluation-list";

        //支付成功的回调
        public static final String SUCCESS_PAY = HOST + "callback/alipay";
        //活动商品列表
        public static final String ACTIVITY_LIST = HOST + "user/activity-goods-list";
        //获取文档
        public static final String GET_WORD = HOST + "pub/docs";
        //获取七牛配置
        public static final String GET_TOKEN = HOST + "pub/qiniu-info";
        //获取用户平台优惠券列表和支付时可使用优惠券
        public static final String GET_DISCOUNT_LIST = HOST + "user/coupon-list";
        //获取商铺可使用优惠，满full元减subtract
        public static final String GET_YOUHUI = HOST + "user/shop-discount-list";
        //获取订单详情
        public static final String ORDER_DETAIL = HOST + "user/order-detail";
        //版本更新
        public static final String UPDATEVERSION = HOST + "pub/version";
        //计算商铺购买商品的配送费
        public static final String GET_PRICE = HOST + "pub/shop-delivery-price";
        //删除订单
        public static final String ORDER_DELETE = HOST + "user/delete-order";
        //取消订单
        public static final String ORDER_CANCEL = HOST + "user/cancel-order";
        //确认收货付款
        public static final String CONFIRM_PAY = HOST + "user/confirm-pay";
        //退款
        public static final String REFUND = HOST + "user/refund";
        //获取微信支付id
        public static final String WEIXIN_ORDER = HOST + "pub/weixin-order";
        //获取分享果汁产品列表
        public static final String JUICE_GOODS_LIST = HOST + "user/share-goods-list";
        //获取分享果汁产品详情
        public static final String JUICE_GOODS_DETAIL = HOST + "user/share-goods-detail";
        //获取分享二维码
        public static final String GET_GOODS_CODE = HOST + "user/order-share-code";
        //编辑分享信息
        public static final String CREATE_SHARE = HOST + "user/order-update-share";
        //获取分享领取列表
        public static final String SHARE_GET_LIST = HOST + "user/order-share-get-list";
        //提交领取分享果汁的地址
        public static final String SHARE_SET_ADDRESS = HOST + "user/order-share-set-address";
        //链接——加盟代理费
        public static final String PROXY_GOODS=HOST_LINK+"proxy-goods";
        //链接——果汁套餐列表
        public static final String PICK_LIST=HOST_LINK+"pack-goods-list";
        //链接——优惠券详情
        public static final String COUPON_DETAIL=HOST_LINK+"coupon-view?couponUseCode=";
        //果汁月套餐订单
        public static final String MONTHORDER=HOST+"user/juice-package-order-list";
        //果汁月套餐分订单详情
        public static final String MONTHORDER_DEAIL=HOST+"user/get-branches-order";
        //设置果汁月套餐分订单是否配送
        public static final String IS_SEND=HOST+"user/update-goods-delivery";
        //修改果汁月套餐分订单内容
        public static final String UPDATE_MONTH=HOST+"user/update-branches-order";
        //获取果汁套餐时间
        public static final String GET_TIME=HOST+"pub/get-delivery-time";
        //优惠券领取
        public static final String GET_COUPON=HOST+"user/coupon-receive";
        //使用优惠券后核算价格
        public static final String GET_COUPON_PRICE=HOST+"pub/goods-price-count";
        //获取分享店铺id
        public static final String GET_SHARE_ID=HOST+"pub/ts-shop";
        //获取优惠券即将过期数量
        public static final String GET_OVERDUE_NO=HOST+"user/coupon-expire-hint-count";
        //检测微信优惠券是否可用
        public static final String IS_WX_COUPON=HOST+"pub/wx-coupon-goods-enabled";
        //充值明细
        public static final String TOP_UP=HOST+"user/top-up";
        //邀请记录
        public static final String INVITE=HOST+"user/invite";
        //充值创建订单
        public static final String CREATE_ORDER_EXT=HOST+"pub/create-order-ext";
        //可用支付方式
        public static final String ORDER_PAY_WEY=HOST+"pub/order-pay-way";
        //余额支付
        public static final String ORDER_PAY_PLAT=HOST+"pub/order-pay-plat";
        //设置默认地址
        public static final String SET_DEFAULT_ADDS=HOST+"user/set-default-address";
        //添加地址时获取推荐手机号码
        public static final String RECO_PHONE=HOST+"user/address-reco-phone";
        //获取城市列表
        public static final String GET_CITY=HOST+"pub/city-list";
        //提交反馈
        public static final String FEEDBACK=HOST+"pub/feedback";
        //手机登录
        public static final String PHONE_LOGIN=HOST+"user/tel-login";
        //密码登录
        public static final String ACCOUNT_LOGIN=HOST+"user/account-login";
        //刷新token
        public static final String REFRESH_TOKEN=HOST+"user/refresh-access-token";
        //第三方登录
        public static final String EXTERNAL_LOGIN=HOST+"user/external-login";
        //第三方绑定（微信）
        public static final String EXTERNAL_BIND=HOST+"user/external-bind";
        //第三方登录扩展（QQ）
        public static final String EXTERNAL_LOGIN_EXT=HOST+"user/external-login-ext";
        //第三方绑定扩展（QQ）
        public static final String EXTERNAL_BIND_EXT=HOST+"user/external-bind-ext";
        //更换手机号码
        public static final String CHANGE_TEL=HOST+"user/change-tel";
        //检测手机
        public static final String VER_SMS_CODE=HOST+"pub/verify-sms-code";
        //检测是否可以解绑（唯一账号）
        public static final String EXTERNAL_ONLY=HOST+"user/external-only";
        //第三方解绑
        public static final String EXTERNAL_UNBIND=HOST+"user/external-unbind";
        //首页获取分类类表
        public static final String GOOS_CATEGORY=HOST+"pub/goods-category";
            //
        public static final String ORDER_CATEGORY=HOST+"user/order-status-count";
    }

    /**
     * 偏好设置相关，SP = SharedPreferences
     * @author libokang
     */
    public static final class SP {

        // SharedPreferences名字
        public static final String APP_NAME = "TIAOSHI";

        //用户帐号密码信息
        public static final String LOGIN_NAME = "LOGIN_NAME";
        public static final String LOGIN_PWD = "LOGIN_PWD";

        //账单列表标识
        public static final String LIST_Older = "LIST_OLDER";

        public static boolean isfalse = true;

        //果汁分享店铺id、
        public static String JUICE_SHORP_ID = "261";
        //社区超市店铺id、
        public static String SUPERMARKET_ID = "1";
        //环境
        public static String URL_TYPE= Host.HOST.substring(7,Host.HOST.indexOf(".")).toUpperCase();
    }
}
