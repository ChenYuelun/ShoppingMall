package com.example.shoppingmall;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.fragments.CartFragment;
import com.example.shoppingmall.fragments.CommunityFragment;
import com.example.shoppingmall.fragments.HomeFragment;
import com.example.shoppingmall.fragments.TypeFragment;
import com.example.shoppingmall.fragments.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.shoppingmall.R.id.rb_main_user;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private  int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragments();
        rgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rgMain.check(R.id.rb_main_home);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new CartFragment());
        fragments.add(new UserFragment());
    }


    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case  R.id.rb_main_home:
                    position = 0;
                    break;
                case  R.id.rb_main_type:
                    position = 1;
                    break;
                case  R.id.rb_main_community:
                    position = 2;
                    break;
                case  R.id.rb_main_cart:
                    position = 3;
                    break;
                case  rb_main_user:
                    position = 4;
                    break;
            }

            swithFragment(position);
        }


    }
    private BaseFragment preFragment;
    private void swithFragment(int position) {
        BaseFragment currentFragment = fragments.get(position);
        if(currentFragment != preFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(preFragment != null) {
                ft.hide(preFragment);
            }
            if(!currentFragment.isAdded()) {
                ft.add(R.id.fl_main,currentFragment);
            }else {
                ft.show(currentFragment);
            }
            ft.commit();
            preFragment = currentFragment;
        }
    }
}
