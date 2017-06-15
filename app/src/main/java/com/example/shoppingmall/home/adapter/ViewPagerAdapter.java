package com.example.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.home.bean.HomeBean;
import com.example.shoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by chenyuelun on 2017/6/12.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private final List<HomeBean.ResultBean.ActInfoBean> datas;
    private final Context mContext;

    public ViewPagerAdapter(Context mContext, List<HomeBean.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + datas.get(position).getIcon_url()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lisatener != null) {
                    lisatener.onItemClick(position);
                }
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    public interface onItemClickLisatener{
        public void onItemClick(int position);
    }

    private onItemClickLisatener lisatener;

    public void setOnItemClickLisatener(onItemClickLisatener l){
        this.lisatener = l;
    }

}
