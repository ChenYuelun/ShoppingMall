package com.example.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.shoppingmall.home.bean.GoodsBean;
import com.example.shoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private final Context mContext;
    private static CartStorage instance;
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context context) {
        this.mContext = context;
        sparseArray = new SparseArray<>();
        listToSparseArray();
    }

    private void listToSparseArray() {
        List<GoodsBean> beanList = getAllData();
        for (int i = 0; i < beanList.size(); i++) {
            GoodsBean goodsBean = beanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        }
    }

    public List<GoodsBean> getAllData() {
        return getLocalData();
    }

    private List<GoodsBean> getLocalData() {
        List<GoodsBean> beanList = new ArrayList<>();
        String json = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(json)) {
            //解析数据成集合
            beanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return beanList;
    }

    /**
     * 单例——懒汉式
     *
     * @param context
     * @return
     */
    public static CartStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (CartStorage.class) {
                if (instance == null) {
                    instance = new CartStorage(context);
                }
            }

        }

        return instance;
    }


    public void addData(GoodsBean goodsBean) {
        GoodsBean tempGoodsbean = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempGoodsbean != null) {
            //已经添加过
            tempGoodsbean.setNumber(goodsBean.getNumber());
        } else {
            //没有添加过
            tempGoodsbean = goodsBean;
        }
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), tempGoodsbean);
        //保存到本地
        savaToLocal();
    }

    //删除数据
    public void deletaData(GoodsBean goodsBean){
        sparseArray.remove(Integer.parseInt(goodsBean.getProduct_id()));
        savaToLocal();
    }

    //更新数据
    public void uadataData(GoodsBean goodsBean){
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        //保存到本地
        savaToLocal();
    }



    private void savaToLocal() {
        List<GoodsBean> goodsBeanList = sparseArrayToList();
        String json = new Gson().toJson(goodsBeanList);
        CacheUtils.putString(mContext, JSON_CART, json);

    }

    private List<GoodsBean> sparseArrayToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }

    /**
     * 是否在购物车中存在
     * @param product_id
     * @return
     */
    public GoodsBean findDete(String product_id) {
        return  sparseArray.get(Integer.parseInt(product_id));

    }


}
