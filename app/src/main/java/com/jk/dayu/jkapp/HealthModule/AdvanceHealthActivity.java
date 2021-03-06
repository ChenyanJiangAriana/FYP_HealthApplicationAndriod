package com.jk.dayu.jkapp.HealthModule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.DbUtils;
import com.jk.dayu.jkapp.BaseModule.HomeFragment;
import com.jk.dayu.jkapp.BaseModule.MainActivity;
import com.jk.dayu.jkapp.MineModule.MineFragment;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.TipsModule.TipsFragment;
import com.jk.dayu.jkapp.UserModule.UserBean;

import java.util.ArrayList;
import java.util.List;


public class AdvanceHealthActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_health);
        resultView = findViewById(R.id.result);

        radioGroup = findViewById(R.id.rg_tab_bar);
        radioGroup.setOnCheckedChangeListener(this);// Of course, you can also use anonymous internal classes to implement

    }

    private void gotoResult() {
        Intent intent = new Intent(this, HealthResultActivity.class);
        startActivity(intent);
    }

    public void test(View view) {

        EditText txt_low_press = findViewById(R.id.low_press);
        EditText txt_high_press = findViewById(R.id.high_press);
        EditText txt_bless_suger = findViewById(R.id.bless_suger);
        if (txt_low_press.getText().toString() == ""
                || txt_high_press.getText().toString() == ""
                || txt_bless_suger.getText().toString() == "") {
            Toast.makeText(this, "Please enter a message and try again", Toast.LENGTH_LONG).show();
            return;
        }

        HealthBean health = DataManager.healthBean(this);
        health.presslow = txt_low_press.getText().toString();
        health.presshigh = txt_high_press.getText().toString();
        health.bloodsugar = txt_bless_suger.getText().toString();
        Integer presslow = Integer.parseInt(health.presslow);
        Integer presshigh = Integer.parseInt(health.presshigh);
        Integer bloodsugar = Integer.parseInt(health.bloodsugar);

        String tips = "";
        if (bloodsugar < 3.9) {
            tips += "Low blood sugar  ";
        } else if (bloodsugar < 6.1) {
            tips += "High blood sugar  ";
        } else {
            tips += "Normal blood sugar  ";
        }

        if (presslow < 60 || presshigh < 90) {
            tips += "Low blood pressure  ";
        } else if (presslow > 90 || presshigh > 140) {
            tips += "High blood pressure  ";
        } else {
            tips += "Normal blood pressure  ";
        }
        resultView.setText(tips);
        Object param = DataManager.getParam(this, "id", "", "userInfo");
        String uid = String.valueOf(param);
        List<String> queryParamList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        queryParamList.add("id");
        valueList.add(uid);
        List<UserBean> userList = DbUtils.getQueryByWhere(UserBean.class, queryParamList,valueList);
        int point = Integer.parseInt(userList.get(0).getPoint())+10;
        userList.get(0).setPoint(point+"");
        DbUtils.update(userList.get(0));
        Toast.makeText(this, "Test completed! 10 bonus points!", Toast.LENGTH_LONG).show();
        health.id = uid;
        health.name = userList.get(0).getUsername();
        DataManager.saveHealthBean(this, health);
        DataManager.closeKeyboard(getWindow(), (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
    }

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
