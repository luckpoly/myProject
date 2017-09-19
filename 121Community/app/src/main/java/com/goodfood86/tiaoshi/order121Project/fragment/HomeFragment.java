package com.goodfood86.tiaoshi.order121Project.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.goodfood86.tiaoshi.order121Project.R;
import com.goodfood86.tiaoshi.order121Project.activity.AdvertisementActivity;
import com.goodfood86.tiaoshi.order121Project.activity.BaseWebViewShowActivity;
import com.goodfood86.tiaoshi.order121Project.activity.ChooseCityActivity;
import com.goodfood86.tiaoshi.order121Project.activity.EducationActivity;
import com.goodfood86.tiaoshi.order121Project.activity.FriendsQuanActivity;
import com.goodfood86.tiaoshi.order121Project.activity.JiazhengActivity;
import com.goodfood86.tiaoshi.order121Project.activity.KuaisuZixunActivity;
import com.goodfood86.tiaoshi.order121Project.activity.LoginActivity;
import com.goodfood86.tiaoshi.order121Project.activity.MainActivity;
import com.goodfood86.tiaoshi.order121Project.activity.MedicalActivity;
import com.goodfood86.tiaoshi.order121Project.activity.OldManActivity;
import com.goodfood86.tiaoshi.order121Project.activity.SearchActivity;
import com.goodfood86.tiaoshi.order121Project.activity.ShowZixunListActivity;
import com.goodfood86.tiaoshi.order121Project.activity.WebViewShowActivity;
import com.goodfood86.tiaoshi.order121Project.activity.YaodianListActivity;
import com.goodfood86.tiaoshi.order121Project.activity.YuleActivity;
import com.goodfood86.tiaoshi.order121Project.activity.YuyueActivity;
import com.goodfood86.tiaoshi.order121Project.activity.ZixunDetailActivity;
import com.goodfood86.tiaoshi.order121Project.adapter.BaseGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.CustomAdapter;
import com.goodfood86.tiaoshi.order121Project.adapter.PartnersGridviewAdapter;
import com.goodfood86.tiaoshi.order121Project.application.Order121Application;
import com.goodfood86.tiaoshi.order121Project.constant.G;
import com.goodfood86.tiaoshi.order121Project.model.CityListModel;
import com.goodfood86.tiaoshi.order121Project.model.CustomActivityModel;
import com.goodfood86.tiaoshi.order121Project.model.GlobalLoginModel;
import com.goodfood86.tiaoshi.order121Project.model.GridViewModel;
import com.goodfood86.tiaoshi.order121Project.model.MoneyTableModel;
import com.goodfood86.tiaoshi.order121Project.model.PubDocModel;
import com.goodfood86.tiaoshi.order121Project.model.RTokenModel;
import com.goodfood86.tiaoshi.order121Project.model.UpdateVersionModel;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyCallBack;
import com.goodfood86.tiaoshi.order121Project.myRequestCallBack.MyRequestCallBack;
import com.goodfood86.tiaoshi.order121Project.rongyun.GroupListActivity;
import com.goodfood86.tiaoshi.order121Project.utils.AppUtil;
import com.goodfood86.tiaoshi.order121Project.utils.MD5;
import com.goodfood86.tiaoshi.order121Project.utils.OkHttpManager;
import com.goodfood86.tiaoshi.order121Project.utils.RongHttp;
import com.goodfood86.tiaoshi.order121Project.utils.SignUtil;
import com.goodfood86.tiaoshi.order121Project.utils.ToastUtil;
import com.goodfood86.tiaoshi.order121Project.versionUpdate.DownLoadService;
import com.goodfood86.tiaoshi.order121Project.verticalbanner.Model01;
import com.goodfood86.tiaoshi.order121Project.verticalbanner.SampleAdapter03;
import com.goodfood86.tiaoshi.order121Project.verticalbanner.VerticalBannerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/7/13.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private List<String> picUrlList = new ArrayList<>();
    private List<PubDocModel.DataBean.NodesBean> wordsEntityList;
    @ViewInject(R.id.convenient_banner)
    private ConvenientBanner convenient_banner;
    @ViewInject(R.id.tv_home_address)
    private TextView tv_home_address;
    @ViewInject(R.id.gv_group)
    private GridView gv_group;
    @ViewInject(R.id.gv_group_partners)
    private GridView gv_group_partners;
    @ViewInject(R.id.my_recycler_huodong)
    private RecyclerView my_recycler_huodong;
    @ViewInject(R.id.ll_go_huoban)
    private LinearLayout ll_go_huoban;
    @ViewInject(R.id.ll_go_yaodian)
    private LinearLayout ll_go_yaodian;
    @ViewInject(R.id.ll_go_jiazheng)
    private LinearLayout ll_go_jiazheng;
    @ViewInject(R.id.ll_go_llq)
    private LinearLayout ll_go_llq;
    //跳转
    @ViewInject(R.id.iv_weixiu)
    private ImageView iv_weixiu;
    @ViewInject(R.id.iv_jiankang)
    private ImageView iv_jiankang;
    @ViewInject(R.id.iv_yuer)
    private ImageView iv_yuer;
    @ViewInject(R.id.iv_yimiao)
    private ImageView iv_yimiao;
    @ViewInject(R.id.iv_go_chaxun)
    private ImageView iv_go_chaxun;
    @ViewInject(R.id.iv_go_yaodian)
    private ImageView iv_go_yaodian;
    @ViewInject(R.id.iv_go_liaotianshi)
    private ImageView iv_go_liaotianshi;
    @ViewInject(R.id.iv_go_baojie)
    private ImageView iv_go_baojie;
    @ViewInject(R.id.iv_go_yuershi)
    private ImageView iv_go_yuershi;
    @ViewInject(R.id.iv_go_shendubao)
    private ImageView iv_go_shendubao;
    @ViewInject(R.id.iv_go_qiyebao)
    private ImageView iv_go_qiyebao;
    @ViewInject(R.id.iv_go_baomu)
    private ImageView iv_go_baomu;
    @ViewInject(R.id.iv_go_jiadian)
    private ImageView iv_go_jiadian;
    @ViewInject(R.id.iv_go_yizhen)
    private ImageView iv_go_yizhen;
    @ViewInject(R.id.iv_go_xiuxian)
    private ImageView iv_go_xiuxian;
    @ViewInject(R.id.iv_go_friend)
    private ImageView iv_go_friend;
    @ViewInject(R.id.iv_go_chuangye)
    private ImageView iv_go_chuangye;
    @ViewInject(R.id.iv_go_gouwu)
    private ImageView iv_go_gouwu;
    @ViewInject(R.id.iv_go_huodong)
    private ImageView iv_go_huodong;
    @ViewInject(R.id.iv_home_yanzi)
    private ImageView iv_home_yanzi;
    @ViewInject(R.id.ed_go_sosuo)
    private TextView ed_go_sosuo;
    @ViewInject(R.id.iv_go_gongyi)
    private ImageView iv_go_gongyi;
    private VerticalBannerView banner03;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener aMapLocationListener;
    private AMapLocationClientOption aMapLocationClientOption;
    private int ACCESS_FINE_LOCATION_CODE = 0x11;
    private AlertDialog.Builder locationBuilder;
    private List<GridViewModel> listGridData;
    private CustomAdapter customAdapter;
    private List<CustomActivityModel.DataBean> dataBeen;
    private String cityString;
    private String[] cityList;
    private final static int RESQUEST_CHOOSECITY = 3;
    private String mCity;
    private UpdateVersionModel updateVersionModel;
    private Dialog dialog2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment, null);
            ((MainActivity) getActivity()).tab_host.setCurrentTab(0);
            initView();
            initListener();
            initData();
            if (!Order121Application.isLogin()) {
                httpsend();
            }
            aotoUpdate();
            registerMessageReceiver();
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return view;
    }

    private void initView() {
        ViewUtils.inject(this, view);
        convenient_banner.startTurning(4000);
        locationBuilder = new AlertDialog.Builder(getActivity()).setTitle("系统提示");
        //6.0申请地图权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_CODE);
        } else {
            initLocation();
        }
        BaseGridviewAdapter baseGridviewAdapter = new BaseGridviewAdapter(getActivity(), setData());
        gv_group.setAdapter(baseGridviewAdapter);
        dataBeen = new ArrayList<>();
        customAdapter = new CustomAdapter(getActivity(), dataBeen);
        //设置水平布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        my_recycler_huodong.setLayoutManager(mLayoutManager);
        my_recycler_huodong.setAdapter(customAdapter);
    }
    private List<GridViewModel> setData() {
        listGridData = new ArrayList();
        String str[] = { "家政", "配送", "教育","医疗","邻里圈", "生鲜粮油", "生活缴费", "违章查询", "快递查询",  "公益活动"};
        int pic[] = {  R.mipmap.jiazheng_it, R.mipmap.peisong_it, R.mipmap.jiaoyu_it, R.mipmap.yiliao_it,R.mipmap.linliquan_it,R.mipmap.gouwu_it, R.mipmap.shenghuo_it, R.mipmap.weizhang_it, R.mipmap.kuaidi_it, R.mipmap.gongyi_it};
        for (int i = 0; i < 10; i++) {
            GridViewModel model1 = new GridViewModel(str[i], getResources().getDrawable(pic[i]));
            listGridData.add(model1);
        }
        return listGridData;
    }

    private void initListener() {
        tv_home_address.setOnClickListener(this);
        iv_weixiu.setOnClickListener(this);
        iv_jiankang.setOnClickListener(this);
        iv_yuer.setOnClickListener(this);
        iv_yimiao.setOnClickListener(this);
        ll_go_huoban.setOnClickListener(this);
        ll_go_yaodian.setOnClickListener(this);
        ll_go_jiazheng.setOnClickListener(this);
        ll_go_llq.setOnClickListener(this);
        iv_go_chaxun.setOnClickListener(this);
        iv_go_yaodian.setOnClickListener(this);
        iv_go_liaotianshi.setOnClickListener(this);
        iv_go_baojie.setOnClickListener(this);
        iv_go_yuershi.setOnClickListener(this);
        iv_go_shendubao.setOnClickListener(this);
        iv_go_qiyebao.setOnClickListener(this);
        iv_go_baomu.setOnClickListener(this);
        iv_go_jiadian.setOnClickListener(this);
        iv_go_yizhen.setOnClickListener(this);
        iv_go_xiuxian.setOnClickListener(this);
        iv_go_friend.setOnClickListener(this);
        iv_go_chuangye.setOnClickListener(this);
        iv_go_gouwu.setOnClickListener(this);
        iv_go_huodong.setOnClickListener(this);
        iv_home_yanzi.setOnClickListener(this);
        iv_go_gongyi.setOnClickListener(this);
        ed_go_sosuo.setOnClickListener(this);
        gv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (listGridData.get(position).getTypeName()) {
                    case "生鲜粮油":
                        Intent intent3 = getActivity().getPackageManager().getLaunchIntentForPackage("com.brandsh.tiaoshi");
                        if (intent3 != null) {
                            startActivity(intent3);
                        } else {
                            Toast.makeText(getActivity(), "您还没有安装挑食网APP，快快去下载吧！", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "邻里圈":
                        startActivity(new Intent(getActivity(), OldManActivity.class));
                        break;
                    case "医疗":
                        //社区医疗
                        startActivity(new Intent(getActivity(), MedicalActivity.class));
                        break;
                    case "家政":
                        startActivity(new Intent(getActivity(), JiazhengActivity.class));
                        break;
                    case "教育":
                        //社区教育
                        startActivity(new Intent(getActivity(), EducationActivity.class));
                        break;
                    case "公益活动":
                        //广告
                        startActivity(new Intent(getActivity(), AdvertisementActivity.class));
                        break;
                    case "生活缴费":
                        Intent intent = new Intent(getActivity(), BaseWebViewShowActivity.class);
                        intent.putExtra("title", "生活缴费");
                        intent.putExtra("url", "http://pms.xkhouse.com/Service/ZlylWap/tongGuide?cityId=21");
                        startActivity(intent);
                        break;
                    case "违章查询":
                        Intent intent1 = new Intent(getActivity(), BaseWebViewShowActivity.class);
                        intent1.putExtra("title", "违章查询");
                        intent1.putExtra("url", "http://m.weizhang8.cn/");
                        startActivity(intent1);
                        break;
                    case "快递查询":
                        Intent intent2 = new Intent(getActivity(), BaseWebViewShowActivity.class);
                        intent2.putExtra("title", "快递查询");
                        intent2.putExtra("url", " https://m.kuaidi100.com/");
                        startActivity(intent2);
                        break;
                    case "配送":
                        //速递
                        ((MainActivity) getActivity()).tab_host.setCurrentTab(2);
                        break;
                }
                listGridData.get(position).getTypeName();
            }
        });
    }

    private void initData() {
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.CITYLIST, new MyRequestCallBack(getActivity(), handler, 8, new CityListModel()));
//        首页头条
        HashMap map1 = new HashMap();
        map1.put("code", "CommunityTop");
        map1.put("actReq", SignUtil.getRandom());
        map1.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(map1);
        map1.put("sign", MD5.getMD5(sign1));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map1, new MyCallBack(4, getContext(), new PubDocModel(), handler));
        //首页banner图
        HashMap map = new HashMap();
        map.put("code", "BannerIndex");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map, new MyCallBack(1, getContext(), new PubDocModel(), handler));
        //获取价格表
        Order121Application.getGlobalHttpUtils().send(HttpRequest.HttpMethod.POST, G.Host.GET_MONEYTABLE, new MyRequestCallBack(getActivity(), handler, 2, new MoneyTableModel()));
        //获取合作伙伴
        HashMap map2 = new HashMap();
        map2.put("code", "Partner");
        map2.put("nodesLimit", "5");
        map2.put("actReq", SignUtil.getRandom());
        map2.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign2 = SignUtil.getSign(map2);
        map2.put("sign", MD5.getMD5(sign2));
        OkHttpManager.postAsync(G.Host.PUB_DOC, map2, new MyCallBack(6, getContext(), new PubDocModel(), handler));
        //活动列表
        HashMap map3 = new HashMap();
        map3.put("code", "Partner");
        map3.put("actReq", SignUtil.getRandom());
        map3.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign3 = SignUtil.getSign(map3);
        map3.put("sign", MD5.getMD5(sign3));
        OkHttpManager.postAsync(G.Host.CUSTOM_ACTYVITY, map3, new MyCallBack(7, getContext(), new CustomActivityModel(), handler));
    }

    private void aotoUpdate() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appType", "ANDROID");
        hashMap.put("appRole", "USER");
        hashMap.put("actReq", SignUtil.getRandom());
        hashMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(hashMap);
        hashMap.put("sign", MD5.getMD5(sign));
        OkHttpManager.postAsync(G.Host.VERSION, hashMap, new MyCallBack(9, getActivity(), new UpdateVersionModel(), handler));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        PubDocModel pubDocModel = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel.getRespCode()) && null != pubDocModel.getData()) {
                            if (picUrlList.size() == 0) {
                                wordsEntityList = pubDocModel.getData().getNodes();
                                for (int i = 0; i < wordsEntityList.size(); i++) {
                                    picUrlList.add(wordsEntityList.get(i).getImg());
                                }
                                initBanner();
                            }
                        }
                        break;
                    case 2:
                        MoneyTableModel moneyTableModel = (MoneyTableModel) msg.obj;
                        if (moneyTableModel.getRespCode() == 0) {
                            Order121Application.globalMoneyTableModel = moneyTableModel;
                        }

                        break;
                    case 3:
                        GlobalLoginModel globalLoginModel = (GlobalLoginModel) msg.obj;
                        if (globalLoginModel != null) {
                            if (globalLoginModel.getRespCode() == 0) {
                                Order121Application.globalLoginModel = globalLoginModel;
                                getActivity().sendBroadcast(new Intent("updateSlidingFragment"));
                                if (Order121Application.isLogin()) {
                                    jPush();
                                }
                                getTestToken();
                            } else {
                                Order121Application.globalLoginModel = null;
                            }
                        }
                        break;
                    case 4:
                        PubDocModel pubDocModel1 = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel1.getRespCode()) && null != pubDocModel1.getData()) {
                            //------------------------文字轮播广告
                            List<Model01> datas03 = new ArrayList<>();
                            for (int i = 0; i < pubDocModel1.getData().getNodes().size(); i++) {
                                datas03.add(new Model01(pubDocModel1.getData().getNodes().get(i).getName(), pubDocModel1.getData().getNodes().get(i).getSubName(), pubDocModel1.getData().getNodes().get(i).getLink(), pubDocModel1.getData().getNodes().get(i).getContent()));
                            }
                           SampleAdapter03 adapter03 = new SampleAdapter03(getContext(), datas03);
                            banner03 = (VerticalBannerView) view.findViewById(R.id.banner_03);
                            banner03.setAdapter(adapter03);
                            banner03.start();
                        }

                        break;
                    case 5:
                        RTokenModel model = (RTokenModel) msg.obj;
                        if (model.getCode() == 200) {
                            initRy(model.getToken());
                        } else {
                            Log.e("融云初始化结果：", "失败");
                        }
                        break;
                    case 6:
                        PubDocModel pubDocModel2 = (PubDocModel) msg.obj;
                        if ("SUCCESS".equals(pubDocModel2.getRespCode()) && null != pubDocModel2.getData()) {
                            final List<PubDocModel.DataBean.NodesBean> list = new ArrayList();
                            list.addAll(pubDocModel2.getData().getNodes());
                            PartnersGridviewAdapter adapter = new PartnersGridviewAdapter(getActivity(), list);
                            gv_group_partners.setAdapter(adapter);
                            gv_group_partners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                                            .putExtra("name", list.get(position).getName())
                                            .putExtra("id", list.get(position).getId()));
                                }
                            });
                        }
                        break;
                    case 7:
                        CustomActivityModel customActivityModel = (CustomActivityModel) msg.obj;
                        if ("SUCCESS".equals(customActivityModel.getRespCode())) {
                            if (customActivityModel.getData() != null) {
                                customAdapter.setData(customActivityModel.getData());
                            }
                        }
                        break;
                    case 8:
                        CityListModel cityListModel = (CityListModel) msg.obj;
                        if (cityListModel.getRespCode() == 0) {
                            cityString = cityListModel.getData().getCity();
                            cityList = cityString.split("\\|");
                            Log.e("cityList", cityList.length + "");
                        }
                        break;
                    case 9:
                        updateVersionModel = (UpdateVersionModel) msg.obj;
                        if ("SUCCESS".equals(updateVersionModel.getRespCode()) && Order121Application.isFirstLogin) {
                            Log.e("code", AppUtil.getVersionCode(getActivity()) + "  " + updateVersionModel.getData().getVersion() + "." + updateVersionModel.getData().getDeputy());
                            if (!AppUtil.getVersionName(getActivity()).equals(updateVersionModel.getData().getVersion() + "." + updateVersionModel.getData().getDeputy())) {
                                dialog2 = new Dialog(getActivity());
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.updateversion, null);
                                Button btn1 = (Button) linearLayout.findViewById(R.id.btn_cancel);
                                Button btn2 = (Button) linearLayout.findViewById(R.id.btn_go_update);
                                TextView tv_content = (TextView) linearLayout.findViewById(R.id.tv_content);
                                TextView tv_title = (TextView) linearLayout.findViewById(R.id.tv_title);
                                tv_title.setText("版本升级v" + updateVersionModel.getData().getVersion() + "." + updateVersionModel.getData().getDeputy());
                                CharSequence charSequence = Html.fromHtml(updateVersionModel.getData().getIntro());
                                tv_content.setText(charSequence);
                                tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                                dialog2.setContentView(linearLayout);
                                dialog2.setCanceledOnTouchOutside(false);
                                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable());
                                btn1.setOnClickListener(HomeFragment.this);
                                btn2.setOnClickListener(HomeFragment.this);
                                Order121Application.isFirstLogin = false;
                                dialog2.show();
                            }
                        }
                        break;


                }
            }
        }
    };

    private void initRy(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
            }

            @Override
            public void onSuccess(String s) {
                Log.e("------", "rongyun--init---SUCCESS");
                UserInfo info = new UserInfo(Order121Application.globalLoginModel.getData().getPhone(), Order121Application.globalLoginModel.getData().getNickname(), Uri.parse(Order121Application.globalLoginModel.getData().getImgKey()));
                RongIM.getInstance().setCurrentUserInfo(info);
            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {
            }
        });
    }

    private void getTestToken() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                List<NameValuePair> nameValuePair = new ArrayList<>();
                nameValuePair.add(new BasicNameValuePair("userId", Order121Application.globalLoginModel.getData().getPhone()));
                RongHttp.rPostHttp("user/getToken.json", nameValuePair, new RTokenModel(), handler, 5, getActivity());
            }
        }.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != banner03) {
            banner03.start();
        }
    }

    //初始化banner
    private void initBanner() {
        if (picUrlList.size() > 0) {
            convenient_banner.setPointViewVisible(true);
            convenient_banner.setPages(new CBViewHolderCreator<BannerHolderView>() {
                @Override
                public BannerHolderView createHolder() {
                    return new BannerHolderView();
                }
            }, picUrlList).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
            convenient_banner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_address:
                if (cityString != null) {
                    Intent intent2 = new Intent(getActivity(), ChooseCityActivity.class);
                    intent2.putExtra("mCurrentCity", mCity);
                    intent2.putExtra("city", cityString);
                    startActivityForResult(intent2, RESQUEST_CHOOSECITY);
                }
                break;
            case R.id.ed_go_sosuo:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.btn_cancel:
                dialog2.dismiss();
                break;
            case R.id.btn_go_update:
                getActivity().startService(new Intent(getActivity(), DownLoadService.class).putExtra("url", updateVersionModel.getData().getUpload()));
                dialog2.dismiss();
                ToastUtil.showShort(getContext(), "开始下载");
                break;
            case R.id.iv_weixiu:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "detail")
                        .putExtra("name", "维修基金")
                        .putExtra("code", "WelfareSqwxjj"));
                break;
            case R.id.iv_jiankang:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "健康资讯")
                        .putExtra("code", "MedicalJkzx"));
                break;
            case R.id.iv_yuer:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "育儿资讯")
                        .putExtra("code", "MedicalYezx"));
                break;
            case R.id.iv_yimiao:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "宝宝疫苗通知")
                        .putExtra("code", "MedicalBbymtz"));
                break;
            case R.id.ll_go_huoban:
                startActivity(new Intent(getActivity(), ShowZixunListActivity.class));
                break;
            case R.id.ll_go_yaodian:
                //社区医疗
                startActivity(new Intent(getActivity(), MedicalActivity.class));
                break;
            case R.id.ll_go_jiazheng:
                startActivity(new Intent(getActivity(), JiazhengActivity.class));
                break;
            case R.id.ll_go_llq:
                startActivity(new Intent(getActivity(), OldManActivity.class));
                break;
            case R.id.iv_go_chaxun:
                startActivity(new Intent(getActivity(), KuaisuZixunActivity.class));
                break;
            case R.id.iv_go_yaodian:
                startActivity(new Intent(getActivity(), YaodianListActivity.class));
                break;
            case R.id.iv_go_liaotianshi:
                if (isLogin()) {
                    startActivity(new Intent(getActivity(), GroupListActivity.class));
                }
                break;
            case R.id.iv_go_baojie:
                Intent intent = new Intent(getActivity(), YuyueActivity.class);
                intent.putExtra("typeName", "日常保洁");
                intent.putExtra("code", "PurifierEvery");
                intent.putExtra("typeCode", "PurifierEvery");
                startActivity(intent);
                break;
            case R.id.iv_go_yuershi:
                Intent intent1 = new Intent(getActivity(), YuyueActivity.class);
                intent1.putExtra("typeName", "育儿师");
                intent1.putExtra("code", "MatronNanny");
                intent1.putExtra("typeCode", "MatronNanny");
                startActivity(intent1);
                break;
            case R.id.iv_go_shendubao:
                Intent intent2 = new Intent(getActivity(), YuyueActivity.class);
                intent2.putExtra("typeName", "深度保洁");
                intent2.putExtra("code", "PurifierDepth");
                intent2.putExtra("typeCode", "PurifierDepth");
                startActivity(intent2);
                break;
            case R.id.iv_go_qiyebao:
                Intent intent3 = new Intent(getActivity(), YuyueActivity.class);
                intent3.putExtra("typeName", "企业保洁");
                intent3.putExtra("code", "EnterpriseCleaning");
                intent3.putExtra("typeCode", "EnterpriseCleaning");
                startActivity(intent3);
                break;
            case R.id.iv_go_baomu:
                Intent intent4 = new Intent(getActivity(), YuyueActivity.class);
                intent4.putExtra("typeName", "保姆");
                intent4.putExtra("code", "MatronSitter");
                intent4.putExtra("typeCode", "MatronSitter");
                startActivity(intent4);
                break;
            case R.id.iv_go_jiadian:
                Intent intent5 = new Intent(getActivity(), YuyueActivity.class);
                intent5.putExtra("typeName", "家电清洁");
                intent5.putExtra("code", "ElectricalClean");
                intent5.putExtra("typeCode", "ElectricalClean");
                startActivity(intent5);
                break;
            case R.id.iv_go_yizhen:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "detail")
                        .putExtra("name", "社区义诊")
                        .putExtra("code", "WelfareSqyz"));
                break;
            case R.id.iv_go_friend:
                startActivity(new Intent(getActivity(), FriendsQuanActivity.class));
                break;
            case R.id.iv_go_xiuxian:
                startActivity(new Intent(getActivity(), YuleActivity.class));
                break;
            case R.id.iv_go_chuangye:
                Intent intent8 = new Intent(getActivity(), WebViewShowActivity.class);
                intent8.putExtra("url", "http://api.86goodfood.com/cause/index");
                intent8.putExtra("name", "大学生创业");
                startActivity(intent8);
                break;
            case R.id.iv_go_gouwu:
                Intent intent7 = getActivity().getPackageManager().getLaunchIntentForPackage("com.brandsh.tiaoshi");
                if (intent7 != null) {
                    startActivity(intent7);
                } else {
                    Toast.makeText(getActivity(), "您还没有安装挑食网APP，快快去下载吧！", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.iv_go_huodong:
                startActivity(new Intent(getActivity(), ZixunDetailActivity.class)
                        .putExtra("type", "list")
                        .putExtra("name", "旅游景点")
                        .putExtra("code", "CommunityHdcs"));
                break;
            case R.id.iv_home_yanzi:
                call("4008804121");
                break;
            case R.id.iv_go_gongyi:
                Intent intent6 = new Intent(getActivity(), BaseWebViewShowActivity.class);
                intent6.putExtra("title", "公益视频");
                intent6.putExtra("url", "http://m.iqiyi.com/gongyi");
                startActivity(intent6);
                break;
        }
    }

    private void call(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("联系客服:").setMessage(phone);
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private boolean isLogin() {
        if (!Order121Application.isLogin()) {
            ToastUtil.show(getActivity(), "请先登陆", Toast.LENGTH_SHORT);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return false;
        } else {
            return true;
        }
    }

    //轮播图的ViewHolder
    public class BannerHolderView implements CBPageAdapter.Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            //加载图片
            Order121Application.getGlobalBitmapUtils().display(imageView, data);
            //绑定事件
            imageView.setOnClickListener(new MyOnClickListener(position));
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        int mPosition;

        public MyOnClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (wordsEntityList.get(mPosition).getHref() != null && !wordsEntityList.get(mPosition).getHref().equals("#")) {
                Intent intent = new Intent(getActivity(), WebViewShowActivity.class);
                intent.putExtra("url", wordsEntityList.get(mPosition).getLink());
                if (!TextUtils.isEmpty(wordsEntityList.get(mPosition).getLink()) && wordsEntityList.get(mPosition).getLink().length() > 1) {
                    startActivity(intent);
                }
            }
        }
    }

    private void jPush() {
        JPushInterface.setAlias(getActivity(), Order121Application.globalLoginModel.getData().getId() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e("11", i + "");
                if (i == 6002) {
                    jPush();
                }
            }
        });
    }

    /**
     * 自动登录
     */
    private void httpsend() {
        HttpUtils httpUtils = Order121Application.getGlobalHttpUtils();
        SharedPreferences sp = getActivity().getSharedPreferences(G.SP.APP_NAME, Context.MODE_PRIVATE);
        String user = sp.getString(G.SP.LOGIN_NAME, "0");
        String psw = sp.getString(G.SP.LOGIN_PWD, "0");
        if (user.length() == 11) {
            RequestParams logindata = new RequestParams();
            logindata.addBodyParameter("username", user);
            logindata.addBodyParameter("password", MD5.getMD5(psw));
            httpUtils.send(HttpRequest.HttpMethod.POST, G.Host.LOGIN, logindata, new MyRequestCallBack(getActivity(), handler, 3, new GlobalLoginModel()));
        }


    }

    MessageReceiver mMessageReceiver;

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        getActivity().registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        convenient_banner.stopTurning();
        getActivity().unregisterReceiver(mMessageReceiver);
    }

    private void initLocation() {
        //初始化定位
        aMapLocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        aMapLocationClientOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setMockEnable(true);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        //声明定位回调监听器
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        Log.e("===", amapLocation.getAddress() + "  " + amapLocation.getStreet() + "  " + amapLocation.getDistrict() + "  " + amapLocation.getLocationDetail() + "  " + amapLocation.getStreetNum());

                        if (amapLocation.getCity().equals("上海市")) {
                            Order121Application.Lat = amapLocation.getLatitude() + "";
                            Order121Application.Lng = amapLocation.getLongitude() + "";

                        } else {
                            Order121Application.Lat = "31.3125221305";
                            Order121Application.Lng = "121.4879768184";
                        }
                        Order121Application.globalCity = mCity = "上海市";
                        tv_home_address.setText("上海市");
//                        aMapLo = amapLocation;
//                        latlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    } else {
                        Order121Application.Lat = "31.3125221305";
                        Order121Application.Lng = "121.4879768184";
                        Order121Application.globalCity = mCity = "上海市";
                        tv_home_address.setText("上海市");
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        if (amapLocation.getErrorCode() == 2) {
                            locationBuilder.setMessage("wifi信息不足,请使用gprs定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 4) {
                            locationBuilder.setMessage("网络连接失败,无法启用定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 9) {
                            locationBuilder.setMessage("定位初始化失败，请重新启动定位").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 11) {
                            locationBuilder.setMessage("基站信息错误").setPositiveButton("确定", null).create().show();
                        } else if (amapLocation.getErrorCode() == 12) {
                            locationBuilder.setMessage("缺少定位权限,请在设备中开启GPS并允许定位").setPositiveButton("确定", null).create().show();
                        } else {
                            Toast.makeText(getActivity(), "定位失败, 请稍后再试", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }
        };
        //设置定位回调监听
        aMapLocationClient.setLocationListener(aMapLocationListener);
        aMapLocationClient.startLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initLocation();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSECITY) {
                mCity = data.getStringExtra("CurrentCity");
                Order121Application.globalCity = mCity;
                tv_home_address.setText(mCity);
            }
        }

    }
}
