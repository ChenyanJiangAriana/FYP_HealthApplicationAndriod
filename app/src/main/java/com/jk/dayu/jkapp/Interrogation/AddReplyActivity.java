package com.jk.dayu.jkapp.Interrogation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.Doctor;
import com.jk.dayu.jkapp.UserModule.UserBean;
import com.jk.dayu.jkapp.service.HttpUntil;
import com.jk.dayu.jkapp.service.Service;
import com.jk.dayu.jkapp.untils.IDUtils;
import com.jk.dayu.jkapp.untils.MyThreadUtil;
import static com.alibaba.fastjson.JSON.parseObject;

public class AddReplyActivity extends BaseActivity {

    private TextView username;
    private TextView sex;
    private TextView age;
    private TextView symptom;
    private TextView time;
    private TextView detial;
    private EditText reply;
    private Button doBtn;
    private Doctor doctor;
    private Condition condition;
    private String result;
    MyThreadUtil myThreadUtil = new MyThreadUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);
        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("doctor");
        condition = (Condition) intent.getSerializableExtra("condition");
        initView();
        initDate();
        doBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reply replyBean = new Reply();
                replyBean.setRid(IDUtils.getNewId());
                replyBean.setCid(condition.getCid());
                replyBean.setDid(condition.getDid());
                replyBean.setUid(condition.getUid());
                replyBean.setContent(reply.getText().toString());
                String  saveResult = myThreadUtil.myThread(Service.saveReply, JSONObject.toJSONString(replyBean));
                if (saveResult.equals("success")) {
                    Toast.makeText(AddReplyActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddReplyActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        username = findViewById(R.id.add_reply_user_name);
        sex = findViewById(R.id.add_reply_user_sex);
        age = findViewById(R.id.add_reply_user_age);
        symptom = findViewById(R.id.add_reply_cond_symptom);
        time = findViewById(R.id.add_reply_cond_time);
        detial = findViewById(R.id.add_reply_cond_detial);
        reply = findViewById(R.id.add_reply_et);
        doBtn = findViewById(R.id.add_reply_btn);
    }
    private void initDate() {
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserBean userBean = parseObject(result, UserBean.class);
        if (condition != null && userBean != null) {
            username.setText("用户姓名：" + userBean.getUsername());
            sex.setText("用户性别：" + userBean.getSex());
            age.setText("用户年龄：" + userBean.getAge());
            symptom.setText("主要症状：" + condition.getSymptoms());
            time.setText("持续时间：" + condition.getTime());
            detial.setText("详细描述:" + condition.getDetails());
        }
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try{
                result = HttpUntil.getUntil(Service.getUser+"?id="+condition.getUid());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
