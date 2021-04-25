package com.jk.dayu.jkapp.HealthModule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.DbUtils;
import com.jk.dayu.jkapp.BaseModule.MainActivity;
import com.jk.dayu.jkapp.BaseModule.NotiManager;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.UserBean;
import java.util.ArrayList;
import java.util.List;

public class AddTimeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    EditText txt_name;
    EditText txt_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        txt_name = findViewById(R.id.name);
        txt_time = findViewById(R.id.time);
        RadioGroup radioGroup = findViewById(R.id.rg_tab_bar);
        radioGroup.setOnCheckedChangeListener(this);
    }

    public void save(View view){
        if (txt_name.getText().toString().equals("") || txt_time.getText().toString().equals("")){
            Toast.makeText(this,"请输入信息后重试",Toast.LENGTH_LONG).show();
            return;
        }
        TimeBean time = new TimeBean();
        time.tip  = txt_name.getText().toString();
        time.time = txt_time.getText().toString();
        Object param = DataManager.getParam(this, "id", "", "userInfo");
        String uid = String.valueOf(param);
        time.setUid(uid);
        DataManager.saveTimeBean(this,time);
        List<TimeBean> list = DbUtils.getQueryByWhere(TimeBean.class,"name",new String[]{time.name});
        UserBean user = new UserBean();
        user.setId(uid);
        List<String> queryParamList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        queryParamList.add("id");
        valueList.add(uid);
        List<UserBean> userList = DbUtils.getQueryByWhere(UserBean.class, queryParamList,valueList);
        int point = Integer.parseInt(userList.get(0).getPoint())+10;
        userList.get(0).setPoint(point+"");
        DbUtils.update(userList.get(0));
        NotiManager.addAlert(this,time.time,time.tip,list.get(0).getId());
        Toast.makeText(this,"保存成功！奖励10积分！",Toast.LENGTH_LONG).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb_home = findViewById(R.id.rb_home);
        switch (checkedId) {
            case R.id.rb_home:
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
                finish();
                break;
            default:
                Toast.makeText(this, "secret", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
