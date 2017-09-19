package com.brandsh.tiaoshi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.brandsh.tiaoshi.R;
import com.brandsh.tiaoshi.activity.CategoryItemDetailListActivity;
import com.brandsh.tiaoshi.adapter.CategoryAdapter;
import com.brandsh.tiaoshi.constant.G;
import com.brandsh.tiaoshi.model.CategoryJsonData;
import com.brandsh.tiaoshi.model.CategoryModel;
import com.brandsh.tiaoshi.myRequestCallBack.MyCallBack;
import com.brandsh.tiaoshi.utils.Md5;
import com.brandsh.tiaoshi.utils.OkHttpManager;
import com.brandsh.tiaoshi.utils.SignUtil;
import com.brandsh.tiaoshi.utils.ToastUtil;
import com.brandsh.tiaoshi.widget.ProgressHUD;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mingle.widget.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by libokang on 15/9/2.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    @ViewInject(R.id.category_rg)
    private RadioGroup category_rg;
    @ViewInject(R.id.rg_category_title)
    private RadioGroup rg_category_title;
    @ViewInject(R.id.category_grid_view)
    private GridView category_grid_view;
    private List<CategoryJsonData.DataBean> listDataBean = new ArrayList<>();

    private HashMap requestMap;
    private CategoryAdapter categoryAdapter;
    private String firstCategory;
    private CategoryModel categoryModel;
    public String code="Fruit";
    private boolean istype=true;
    //加载动画
    private ShapeLoadingDialog loadingDialog;
    private MyFragmentBroadcastReciver myFragmentBroadcastReciver;
    private int currentPosition = 0;
    private void registerReceiver() {
        myFragmentBroadcastReciver = new MyFragmentBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateUI");
        getActivity().registerReceiver(myFragmentBroadcastReciver, intentFilter);
    }
    public class MyFragmentBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("updateUI".equals(intent.getAction())) {
                switch (intent.getStringExtra("what_category")){
                    case "shuiguo":
                        rg_category_title.check(R.id.rbt_shuiguo);
                        break;
                    case "guozhi":
                        rg_category_title.check(R.id.rbt_guozhi);
                        break;
                    case "lingshi":
                        rg_category_title.check(R.id.rbt_lingshi);
                        break;
                    case "liangyou":
                        rg_category_title.check(R.id.rbt_liangyou);
                        break;
                }
                G.SP.isfalse=true;
                getGoodsCategory();
                getActivity().removeStickyBroadcast(intent);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.category_fragment, null);
            ViewUtils.inject(this, rootView);
//            loadingDialog = ProgressHUD.show(getActivity(), "努力加载中...");
//            loadingDialog.show();
            initTitleButton();
            registerReceiver();
            firstCategory = "全部鲜果";
            requestMap=new HashMap();
            categoryAdapter = new CategoryAdapter(getActivity(), listDataBean);
            category_grid_view.setAdapter(categoryAdapter);
            category_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), CategoryItemDetailListActivity.class);
                    intent.putExtra("firstCategory", firstCategory);
                    intent.putExtra("cate_id", listDataBean.get(position).getId()+"");
                    intent.putExtra("TypeId", listDataBean.get(position).getTypeId()+"");
                    intent.putExtra("cate_name", listDataBean.get(position).getName());
                    startActivity(intent);
                }
            });
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }
    public void initTitleButton(){
        rg_category_title.check(R.id.rbt_shuiguo);
        rg_category_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rbt_shuiguo:
                        code="Fruit";
//                        loadingDialog.show();
                        break;
                    case R.id.rbt_guozhi:
                        code="Juice";
//                        loadingDialog.show();
                        break;
                    case R.id.rbt_lingshi:
                        code="Snacks";
//                        loadingDialog.show();
                        break;
                    case R.id.rbt_liangyou:
                        code="Grain";
                        break;
                }
                if (G.SP.isfalse){
                    getGoodsCategory();
                }
            }
        });
    }
    public void initviewLayout(){
        istype=false;
        category_rg.check(R.id.category_rb1);
        firstCategory=categoryModel.getData().get(0).getName();
        istype=true;
        category_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.category_rb1:
                        currentPosition=0;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb2:
                        currentPosition=1;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb3:
                        currentPosition=2;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb4:
                        currentPosition=3;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb5:
                        currentPosition=4;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb6:
                        currentPosition=5;
//                        loadingDialog.show();
                        break;
                    case R.id.category_rb7:
                        currentPosition=6;
//                        loadingDialog.show();
                        break;
                }
                if (istype){
                    updateGridView(currentPosition);
                }
            }
        });
    }
    private void updateGridView(int position) {
        if (position>categoryModel.getData().size()){
            position=0;
        }
        HashMap<String,String> categoryMap=new HashMap<>();
        Log.e("----",categoryModel.getData().size()+"");
        categoryMap.put("typeCode",categoryModel.getData().get(position).getCode());
        Log.e("----",categoryModel.getData().get(position).getCode());
        categoryMap.put("actReq",SignUtil.getRandom());
        categoryMap.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign1 = SignUtil.getSign(categoryMap);
        categoryMap.put("sign", Md5.toMd5(sign1));
        OkHttpManager.postAsync(G.Host.GOODS_CATEGORY_ITEM,categoryMap,new MyCallBack(2,getActivity(),new CategoryJsonData(), handler));
        firstCategory=categoryModel.getData().get(position).getName();
    }

    //获取分类类表
    private void getGoodsCategory() {
        HashMap map = new HashMap();
        map.put("categoryCode", code);
        Log.e("--code",code);
        map.put("roleTypeCode", "Shop");
        map.put("actReq", SignUtil.getRandom());
        map.put("actTime", System.currentTimeMillis() / 1000 + "");
        String sign = SignUtil.getSign(map);
        map.put("sign", Md5.toMd5(sign));
        OkHttpManager.postAsync(G.Host.GOODS_CATEGORY, map, new MyCallBack(8, getActivity(), new CategoryModel(), handler));

    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    CategoryJsonData categoryJsonData1 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData1 != null) {
                        if (categoryJsonData1.getRespCode().equals("SUCCESS")&&null!=categoryJsonData1.getData()) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData1.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData1.getRespMsg());
                            category_rg.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 2:
                    CategoryJsonData categoryJsonData2 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData2 != null) {
                        if (categoryJsonData2.getRespCode().equals("SUCCESS")&&null!=categoryJsonData2.getData()) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData2.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            listDataBean.clear();
                            categoryAdapter.notifyDataSetChanged();
                            showToast(categoryJsonData2.getRespMsg());
                        }
                    }
                    break;
                case 3:
                    CategoryJsonData categoryJsonData3 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData3 != null) {
                        if (categoryJsonData3.getRespCode().equals("SUCCESS")) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData3.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData3.getRespMsg());
                        }
                    }
                    break;
                case 4:
                    CategoryJsonData categoryJsonData4 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData4 != null) {
                        if (categoryJsonData4.getRespCode().equals("SUCCESS")) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData4.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData4.getRespMsg());
                        }
                    }
                    break;
                case 5:
                    CategoryJsonData categoryJsonData5 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData5 != null) {
                        if (categoryJsonData5.getRespCode().equals("SUCCESS")) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData5.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData5.getRespMsg());
                        }
                    }
                    break;
                case 6:
                    CategoryJsonData categoryJsonData6 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData6 != null) {
                        if (categoryJsonData6.getRespCode().equals("SUCCESS")) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData6.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData6.getRespMsg());
                        }
                    }
                    break;
                case 7:
                    CategoryJsonData categoryJsonData7 = (CategoryJsonData) msg.obj;
//                    loadingDialog.dismiss();
                    if (categoryJsonData7 != null) {
                        if (categoryJsonData7.getRespCode().equals("SUCCESS")) {
                            List<CategoryJsonData.DataBean> list = categoryJsonData7.getData();
                            listDataBean.clear();
                            listDataBean.addAll(list);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            showToast(categoryJsonData7.getRespMsg());
                        }
                    }
                    break;
                case 8:
                    categoryModel = (CategoryModel) msg.obj;
                    if (categoryModel.getRespCode().equals("SUCCESS")&&null!=categoryModel.getData()) {
                        category_rg.setVisibility(View.VISIBLE);
                        category_grid_view.setVisibility(View.VISIBLE);
//                        setGone(categoryModel.getData().size());
                        showNavs(categoryModel.getData().size());
                        requestMap = new HashMap();
                        requestMap.put("typeCode", categoryModel.getData().get(0).getCode());
                        requestMap.put("actReq", SignUtil.getRandom());
                        requestMap.put("actTime", System.currentTimeMillis() / 1000 + "");
                        String sign = SignUtil.getSign(requestMap);
                        requestMap.put("sign", Md5.toMd5(sign));
                        OkHttpManager.postAsync(G.Host.GOODS_CATEGORY_ITEM, requestMap, new MyCallBack(1, getActivity(), new CategoryJsonData(), handler));
                        initviewLayout();
                    } else {
                        if (!"操作成功".equals(categoryModel.getRespMsg())){
                            ToastUtil.showShort(getActivity(), categoryModel.getRespMsg());
                        }
                        category_rg.setVisibility(View.GONE);
                        category_grid_view.setVisibility(View.GONE);
//                        loadingDialog.dismiss();
                    }
                    break;
            }
        }
    };
    private void showNavs(int size) {
        for (int i = 0; i < 15; i++) {
            if (i < size) {
                category_rg.getChildAt(i).setVisibility(View.VISIBLE);
                ((RadioButton) (category_rg.getChildAt(i))).setText(categoryModel.getData().get(i).getName());
            } else {
                category_rg.getChildAt(i).setVisibility(View.GONE);
                ((RadioButton) (category_rg.getChildAt(i))).setText("");
            }

        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myFragmentBroadcastReciver);
    }

}
