package com.jk.dayu.jkapp.UserModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.DbUtils;
import com.jk.dayu.jkapp.BaseModule.MainActivity;
import com.jk.dayu.jkapp.Interrogation.DoctorActivity;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.service.Service;
import com.jk.dayu.jkapp.untils.MyThreadUtil;
import com.jk.dayu.jkapp.untils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;


public class LoginActivity extends BaseActivity {
    private EditText account;
    private EditText pwd;
    private UserBean user = new UserBean();
    private Doctor doctor = new Doctor();
    RadioButton rb1;
    RadioButton rb2;
    MyThreadUtil myThreadUtil = new MyThreadUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.edit_account);
        pwd = findViewById(R.id.edit_pwd);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb1.setChecked(true);
        Object param = DataManager.getParam(this, "id", "", "userInfo");
        String id = String.valueOf(param);
        if (!StringUtil.isEmpty(id)) {
            List<String> queryParamList = new ArrayList<>();
            List<String> valueList = new ArrayList<>();
            queryParamList.add("id");
            valueList.add(id);
            List<UserBean> list = DbUtils.getQueryByWhere(UserBean.class, queryParamList, valueList);
            if (list.size() > 0) {
                account.setText(list.get(0).username);
                pwd.setText(list.get(0).getPassword());
            }
        }
    }

    public void login(View view) {
        String accountT = account.getText().toString().trim();
        String pwdT = pwd.getText().toString().trim();
        if (accountT.length() < 3) {
            Toast.makeText(this, "username should at least 3 characters", Toast.LENGTH_LONG).show();
            return;
        }
        if (pwdT.length() < 6) {
            Toast.makeText(this, "password at least 6 ", Toast.LENGTH_LONG).show();
            return;
        }
        if (!rb1.isChecked() && !rb2.isChecked()) {
            Toast.makeText(this, "please choose your role", Toast.LENGTH_LONG).show();
            return;
        }
        if (rb1.isChecked()) {
            user.setRole("0");
            user.setUsername(accountT);
            user.setPassword(pwdT);
            String result = myThreadUtil.myThread(Service.loginUrl, JSONObject.toJSONString(user));
            user = parseObject(result, UserBean.class);
            if (user != null) {
                DataManager.saveUser(user);
                DataManager.setParam(LoginActivity.this, "id", user.getId(), "userInfo");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "username and password worry", Toast.LENGTH_SHORT).show();
            }
        } else {
            doctor.setRole("1");
            doctor.setUsername(accountT);
            doctor.setPassword(pwdT);
            String result = myThreadUtil.myThread(Service.doctorLoginUrl, JSONObject.toJSONString(doctor));
            doctor = parseObject(result, Doctor.class);
            if (doctor != null) {
                DataManager.saveDoctor(doctor);
                DataManager.setParam(LoginActivity.this, "did", doctor.getDid(), "userInfo");
                Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "username or password worry", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
