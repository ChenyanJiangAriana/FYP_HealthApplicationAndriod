package com.jk.dayu.jkapp.UserModule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.jk.dayu.jkapp.BaseModule.BaseActivity;
import com.jk.dayu.jkapp.Interrogation.InterrogationActivity;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.service.HttpUntil;
import com.jk.dayu.jkapp.service.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

public class DoctorListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    String result=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Doctor > doctorList = parseObject(result, new TypeReference<List<Doctor>>(){});
        Message message = new Message();
        message.obj = doctorList;
        mHandler.sendMessage(message);
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try{
                result = HttpUntil.getUntil(Service.dockerList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<Doctor> doctorList = (List<Doctor>) msg.obj;
            List<Map<String, Object>> lists = new ArrayList<>();
            for (Doctor d:doctorList){
                Map<String, Object> map = new HashMap<>();
                //用到的图片是mipmap中的ic_launcher
                int imageViews = R.mipmap.ic_launcher;
                map.put("image", imageViews);
                map.put("did",d.getDid());
                map.put("name", d.getUsername());
                map.put("content", d.getIntroduce());
                lists.add(map);
            }
            //适配器指定应用自己定义的xml格式
            SimpleAdapter adapter = new SimpleAdapter(DoctorListActivity.this, lists, R.layout.item_doctor_list, new String[]{"image", "did", "name", "content"}, new int[]{R.id.image1, R.id.did, R.id.name, R.id.introduce});
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(DoctorListActivity.this);

        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView didTv = view.findViewById(R.id.did);
        String did = (String) didTv.getText();
        if(!did.isEmpty()){
            Intent intent = new Intent(DoctorListActivity.this, InterrogationActivity.class);
            intent.putExtra("did",did);
            startActivity(intent);
            finish();
        }

    }
}

