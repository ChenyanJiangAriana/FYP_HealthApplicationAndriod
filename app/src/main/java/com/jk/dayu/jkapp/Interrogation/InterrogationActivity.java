package com.jk.dayu.jkapp.Interrogation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.R;

public class InterrogationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private AddConditionFragment addConditionFragment;
    private ShowReplyFragment showReplyFragment;
    private ShowConditionsFragment showConditionsFragment;
    private static InterrogationActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_inquiry);
        RadioGroup rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        RadioButton rb_channel = findViewById(R.id.main_addBtn);
        rb_channel.setChecked(true);
        instance = this;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.main_addBtn:
                if(addConditionFragment == null){
                    addConditionFragment = new AddConditionFragment();
                    fTransaction.add(R.id.ly_content,addConditionFragment);
                }else{
                    fTransaction.show(addConditionFragment);
                }
                break;
            case R.id.main_shwoConsBtn:
                if(showConditionsFragment == null){
                    showConditionsFragment = new ShowConditionsFragment();
                    fTransaction.add(R.id.ly_content,showConditionsFragment);
                }else{
                    fTransaction.show(showConditionsFragment);
                }
                break;
            case R.id.main_showComBtn:
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

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(addConditionFragment != null)fragmentTransaction.hide(addConditionFragment);
        if(showReplyFragment != null)fragmentTransaction.hide(showReplyFragment);
        if(showConditionsFragment != null)fragmentTransaction.hide(showConditionsFragment);
    }
    public static InterrogationActivity getInstance(){
        return instance;
    }
}
