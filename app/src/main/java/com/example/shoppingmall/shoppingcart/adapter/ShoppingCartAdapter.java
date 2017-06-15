package com.example.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.GoodsBean;
import com.example.shoppingmall.shoppingcart.utils.CartStorage;
import com.example.shoppingmall.shoppingcart.view.AddSubView;
import com.example.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/6/13.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox checkboxDeleteAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> list, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox checkboxDeleteAll) {
        this.mContext = mContext;
        this.datas = list;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.checkboxDeleteAll = checkboxDeleteAll;
        showTotalPrice();


    }

    public void showTotalPrice() {
        double totalPrice = getTotalPrice();
        tvShopcartTotal.setText("总计￥："+ totalPrice);
        Log.e("TAG","总价：" +getTotalPrice() );

    }

    public double getTotalPrice() {
        double totalPrice = 0;
        if(datas != null && datas.size() >0){
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()){
                    totalPrice += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();
                    Log.e("TAG",goodsBean.getName()+"单价：" + Double.parseDouble(goodsBean.getCover_price()) +"，数量："+goodsBean.getNumber());
                }
            }
        }
        return totalPrice;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_shoppingcart, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //1.先得到数据
        final GoodsBean goodsBean = datas.get(position);
        //2.绑定数据
        holder.cbGov.setChecked(goodsBean.isChecked());
        //图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.ivGov);
        //设置名称
        holder.tvDescGov.setText(goodsBean.getName());
        //设置价格
        holder.tvPriceGov.setText("￥"+goodsBean.getCover_price());

        //设置数量
        holder.addSubView.setValue(goodsBean.getNumber());

        holder.addSubView.setMinValue(1);
        //设置库存-来自服务器-
        holder.addSubView.setMaxValue(100);

        holder.addSubView.setOnNumberChangerListener(new AddSubView.OnNumberChangerListener() {
            @Override
            public void onNumberChanger(int value) {
                goodsBean.setNumber(value);
                CartStorage.getInstance(mContext).uadataData(goodsBean);
                showTotalPrice();
            }
        });


    }


    /**
     * 校验是否全选
     */
    public void checkAll() {
        if(datas != null && datas.size() >0){

            int number = 0;
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if(!goodsBean.isChecked()){
                    //只要有一个不勾选
                    checkboxAll.setChecked(false);
                    checkboxDeleteAll.setChecked(false);
                }else{
                    //勾选
                    number ++;
                }
            }

            if(datas.size()==number){
                checkboxAll.setChecked(true);
                checkboxDeleteAll.setChecked(true);
            }

        }else{
            //没有数据
            checkboxAll.setChecked(false);
            checkboxDeleteAll.setChecked(false);
        }
    }

    public void checkAll_none(boolean isChecked) {
        if(datas != null && datas.size() >0){
            for (int i=0;i<datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                //设置是否勾选状态
                goodsBean.setChecked(isChecked);
                checkboxAll.setChecked(isChecked);
                checkboxDeleteAll.setChecked(isChecked);

                //更新视图
                notifyItemChanged(i);
            }

        }
    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_gov)
        CheckBox cbGov;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @BindView(R.id.addSubView)
        AddSubView addSubView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemClickListener != null){
                        itemClickListener.onItemClickListener(v,getLayoutPosition());
                    }
                }
            });


        }
    }

    //回调点击事件的监听
    private OnItemClickListener itemClickListener;

    /**
     * 点击item的监听
     */
    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position);
    }

    /**
     * 设置item的监听
     * @param l
     */
    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }


    public void deleteData() {
        if(datas != null && datas.size() >0) {
            for(int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()) {
                    datas.remove(goodsBean);
                    CartStorage.getInstance(mContext).deletaData(goodsBean);
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }

    }
}
