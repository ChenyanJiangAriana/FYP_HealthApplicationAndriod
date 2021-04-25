package com.jk.dayu.jkapp.Interrogation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.LoginActivity;

public class DoctorActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ShowConditionsFragment showConditionsFragment;
    private ShowReplyFragment showReplyFragment;
    private static DoctorActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        RadioGroup rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        RadioButton rb_channel = findViewById(R.id.show_conditisons_btn);
        rb_channel.setChecked(true);
        instance = this;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
        DataManager.removeParam(DoctorActivity.this,"did","userInfo");
        startActivity(intent);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(showReplyFragment != null)fragmentTransaction.hide(showReplyFragment);
        if(showConditionsFragment != null)fragmentTransaction.hide(showConditionsFragment);
    }
    public static DoctorActivity getInstance(){
        return instance;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.show_conditisons_btn:
                if(showConditionsFragment == null){
                    showConditionsFragment = new ShowConditionsFragment();
                    fTransaction.add(R.id.ly_content,showConditionsFragment);
                }else{
                    fTransaction.show(showConditionsFragment);
                }
                break;
            case R.id.show_replys_btn:
                if(showReplyFragment == null){
                    showReplyFragment = new ShowReplyFragment();
                    fTransaction.add(R.id.ly_content,showReplyFragment);
                }else{
                    fTransaction.show(showReplyFragment);
                }
                break;
        }
        fTransaction.commit();
    }
}
