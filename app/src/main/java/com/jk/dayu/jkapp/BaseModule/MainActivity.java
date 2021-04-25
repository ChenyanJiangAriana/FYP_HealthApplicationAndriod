package com.jk.dayu.jkapp.BaseModule;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import com.jk.dayu.jkapp.MineModule.MineFragment;
import com.jk.dayu.jkapp.TipsModule.TipsFragment;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.LoginActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    //Fragment Object
    private HomeFragment homeFragment;
    private TipsFragment tipsFragment;
    private MineFragment mineFragment;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        RadioButton rb_channel = findViewById(R.id.rb_home);
        rb_channel.setChecked(true);
        DbUtils.createDb(this);
        instance = this;

    }

    @Override
    protected void onResume() {
        if (DataManager.isLogin(this)){
            homeFragment.initData();
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_home:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    fTransaction.add(R.id.ly_content,homeFragment);
                }else{
                    fTransaction.show(homeFragment);
                }
                break;
            case R.id.rb_tips:
                if(tipsFragment == null){
                    tipsFragment = new TipsFragment();
                    fTransaction.add(R.id.ly_content,tipsFragment);
                }else{
                    fTransaction.show(tipsFragment);
                }
                break;
            case R.id.rb_mine:
                if(mineFragment == null){
                    mineFragment = new MineFragment();
                    fTransaction.add(R.id.ly_content,mineFragment);
                }else{
                    fTransaction.show(mineFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment != null)fragmentTransaction.hide(homeFragment);
        if(tipsFragment != null)fragmentTransaction.hide(tipsFragment);
        if(mineFragment != null)fragmentTransaction.hide(mineFragment);
    }
    public static MainActivity getInstance(){
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        return instance;
    }

}
