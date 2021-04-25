package com.jk.dayu.jkapp.Interrogation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.DbUtils;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.Doctor;
import com.jk.dayu.jkapp.service.HttpUntil;
import com.jk.dayu.jkapp.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.alibaba.fastjson.JSON.parseObject;

public class ShowReplyFragment extends Fragment {
    private Doctor doctor;
    String result=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_reply, container, false);
        doctor  = (Doctor) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra("doctor");
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Reply> dataList = parseObject(result, new TypeReference<List<Reply>>() {
        });
        ListView listView = view.findViewById(R.id.lv_show_reply);
        MyAdapter myAdapter = new MyAdapter(getActivity(), R.layout.adapter_reply, dataList);
        listView.setAdapter(myAdapter);
        return view;
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            Object param = DataManager.getParam(getActivity(), "id", "", "userInfo");
            String id = String.valueOf(param);
            List<String> queryParamList = new ArrayList<>();
            List<String> valueList = new ArrayList<>();
            queryParamList.add("did");
            valueList.add(id);
            List<Doctor> list = DbUtils.getQueryByWhere(Doctor.class, queryParamList, valueList);
            if (list.size()>0){
                doctor = list.get(0);
            }
            try{
                if (doctor != null){
                    result = HttpUntil.getUntil(Service.replyForDoctor+"?did="+id);
                }else {
                    result = HttpUntil.getUntil(Service.replyForUser+"?uid="+id);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    static class MyAdapter extends ArrayAdapter<Reply> {
        private final List<Reply> dataList;
        private final int resourceId;
        public MyAdapter(Context context, int resource, List<Reply> objects) {
            super(context, resource, objects);
            this.dataList = objects;
            this.resourceId = resource;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            TextView tvId = view.findViewById(R.id.tv_id);
            TextView tvContent = view.findViewById(R.id.tv_content);
            TextView tvCid = view.findViewById(R.id.tv_cid);

            tvId.setText(dataList.get(position).getDid() + "");
            tvCid.setText(dataList.get(position).getCid() + "");
            tvContent.setText(dataList.get(position).getContent());
            return view;
        }
    }
}
