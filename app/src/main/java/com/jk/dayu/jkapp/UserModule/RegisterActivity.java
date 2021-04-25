package com.jk.dayu.jkapp.UserModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.MainActivity;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.service.Service;
import com.jk.dayu.jkapp.untils.IDUtils;
import com.jk.dayu.jkapp.untils.MyThreadUtil;
import com.jk.dayu.jkapp.untils.StringUtil;

public class RegisterActivity extends BaseActivity {

    private EditText accountTxt;
    private EditText pwdTxt;
    private EditText ageTxt;
    private RadioButton sex1;
    private RadioButton sex2;
    private RadioButton sex3;
    private Button registerButton;
    private final UserBean user = new UserBean();
    MyThreadUtil myThreadUtil = new MyThreadUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountTxt = findViewById(R.id.edit_account);
        pwdTxt = findViewById(R.id.edit_pwd);
        sex1 = findViewById(R.id.sex1);
        sex2 = findViewById(R.id.sex2);
        sex3 = findViewById(R.id.sex3);
        ageTxt = findViewById(R.id.edit_age);
        registerButton = findViewById(R.id.register);
        verifyAccount();

    }
    private boolean verifyAccount(){
        accountTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String account = accountTxt.getText().toString().trim();
                    JSONObject json = new JSONObject();
                    json.put("username",account);
                    System.out.println(json);
                    if (account.length()>=3){
                        String result = myThreadUtil.myThread(Service.verifyAccountUrl,json.toJSONString());
                        int i = Integer.parseInt(result);
                        if (i>0){
                            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
                            registerButton.setEnabled(false);
                            registerButton.setBackgroundResource(R.drawable.bg_login_submit_lock);
                        }else {
                            registerButton.setEnabled(true);
                            registerButton.setBackgroundResource(R.drawable.bg_login_submit);
                        }
                    }
                }
            }
        });
        return true;
    }
    public void register(View view) {
        if (accountTxt.getText().length()<3){
            Toast.makeText(this, "用户名不小于3位", Toast.LENGTH_LONG).show();
            return;
        }
        if (pwdTxt.getText().length()<6){
            Toast.makeText(this, "密码不小于6位", Toast.LENGTH_LONG).show();
            return;
        }
        boolean b1 = StringUtil.isEmpty(sex1.toString());
        boolean b2 = StringUtil.isEmpty(sex2.toString());
        boolean b3 = StringUtil.isEmpty(sex3.toString());
        if (b1 && b2 && b3){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_LONG).show();
            return;
        }
        if (!StringUtil.isEmpty(ageTxt.toString())){
            int age = Integer.parseInt(ageTxt.getEditableText().toString().trim());
            if (age<0 || age>200){
                Toast.makeText(this, "请输入正确的年龄", Toast.LENGTH_LONG).show();
                return;
            }
        }else {
            Toast.makeText(this, "请输入年龄", Toast.LENGTH_LONG).show();
            return;
        }
        String name = accountTxt.getText().toString();
        String pwd = pwdTxt.getText().toString();
        String age = ageTxt.getText().toString();
        String id = IDUtils.getNewStrId();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(pwd);
        user.setAge(age);
        if(sex1.isChecked()){
            user.setSex("0");
        }else if(sex2.isChecked()){
            user.setSex("1");
        }else if(sex3.isChecked()){
            user.setSex("2");
        }
        user.setRole("0");
        user.setPoint("0");
        String result = myThreadUtil.myThread(Service.registerUrl, JSONObject.toJSONString(user));
        if ("true".equals(result)) {
            DataManager.saveUser(user);
            DataManager.setParam(RegisterActivity.this,"id", id,"userInfo");
            DataManager.setParam(RegisterActivity.this,"role", id,"userInfo");
            Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (result.equals("false")) {
            Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}
