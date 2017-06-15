package com.example.shoppingmall.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.adapter.HomeAdapter;
import com.example.shoppingmall.home.bean.GoodsBean;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;
import com.example.shoppingmall.shoppingcart.utils.VirtualkeyboardHeight;
import com.example.shoppingmall.shoppingcart.view.AddSubView;
import com.example.shoppingmall.utils.Constants;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.shoppingmall.R.id.ll_root;

public class GoodsInfoActivity extends AppCompatActivity {

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.tv_more_share)
    TextView tvMoreShare;
    @BindView(R.id.tv_more_search)
    TextView tvMoreSearch;
    @BindView(R.id.tv_more_home)
    TextView tvMoreHome;
    @BindView(R.id.btn_more)
    Button btnMore;
    @BindView(ll_root)
    LinearLayout llRoot;
    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        getData();
        setData();
        
    }

    private void setData() {
        //设置图片和名字和价格的数据
        String coverPrice = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();//图片地址
        String productId = goodsBean.getProduct_id();//商品id
        String name = goodsBean.getName();//名称

        //设置图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        tvGoodInfoName.setText(name);
        tvGoodInfoPrice.setText("￥" + coverPrice);


        //设置webView的数据
        setWebViewData(productId);
    }

    private void setWebViewData(String productId) {
        WebSettings webSettings = wbGoodInfoMore.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        //设置支持双击变大变小
        webSettings.setUseWideViewPort(true);

        //设置检索缓存的
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);

        //设置不跳转到系统的浏览器
        wbGoodInfoMore.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        wbGoodInfoMore.loadUrl("http://mp.weixin.qq.com/s/Cf3DrW2lnlb-w4wYaxOEZg");
    }

    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
    }

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.btn_good_info_addcart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
//                Toast.makeText(GoodsInfoActivity.this, "更多", Toast.LENGTH_SHORT).show();
                llRoot.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_good_info_callcenter:
                Toast.makeText(GoodsInfoActivity.this, "联系客服", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(GoodsInfoActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                Toast.makeText(GoodsInfoActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_good_info_addcart:
//                CartStorage.getInstance(this).addData(goodsBean);
//                Toast.makeText(GoodsInfoActivity.this, "已添加到购物车", Toast.LENGTH_SHORT).show();
                showPopuwindow();
                break;
            case R.id.tv_more_share:
                Toast.makeText(GoodsInfoActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_search:
                Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                Toast.makeText(GoodsInfoActivity.this, "主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_more:
//                Toast.makeText(GoodsInfoActivity.this, "退出", Toast.LENGTH_SHORT).show();
                llRoot.setVisibility(View.GONE);

                break;
        }
    }

    //缓存
    private GoodsBean tempGoodsBean;
    //该商品信息是否在购物车中存在
    private  boolean isExist = false;

    /**
     * 显示popupWindow
     */

    private void showPopuwindow() {
        tempGoodsBean = CartStorage.getInstance(this).findDete(this.goodsBean.getProduct_id());
        if(tempGoodsBean != null) {
            isExist = true;
        }else {
            isExist = false;
            tempGoodsBean = goodsBean;
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popuwindow_add_product,null);
        final PopupWindow popuwindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        popuwindow.setFocusable(true);

        ColorDrawable cd = new ColorDrawable(0xfffffff);
        popuwindow.setBackgroundDrawable(cd);

        popuwindow.setAnimationStyle(R.style.mypopuwindow_anim_style);

        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        AddSubView nas_goodinfo_num = (AddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);

        Glide.with(GoodsInfoActivity.this).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_goodinfo_photo);

        // 名称
        tv_goodinfo_name.setText(tempGoodsBean.getName());
        // 显示价格
        tv_goodinfo_price.setText(tempGoodsBean.getCover_price());

        // 设置最大值和当前值
        nas_goodinfo_num.setMaxValue(100);//库存100

        nas_goodinfo_num.setValue(tempGoodsBean.getNumber());

        nas_goodinfo_num.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void onNumberChanger(int value) {
                tempGoodsBean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuwindow.dismiss();
                if(isExist&& tempGoodsBean.getNumber()==1){
                    tempGoodsBean.setNumber(tempGoodsBean.getNumber()+1);
                }
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuwindow.dismiss();
                //添加购物车
                CartStorage.getInstance(GoodsInfoActivity.this).addData(tempGoodsBean);
                Log.e("TAG", "66:" + tempGoodsBean.toString());
                Toast.makeText(GoodsInfoActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        });

        popuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popuwindow.dismiss();
            }
        });

        // 5 在底部显示
        popuwindow.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM, 0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));


    }


}
