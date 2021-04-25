package com.jk.dayu.jkapp.Interrogation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.BaseModule.DbUtils;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.UserModule.Doctor;
import com.jk.dayu.jkapp.UserModule.UserBean;
import com.jk.dayu.jkapp.service.HttpUntil;
import com.jk.dayu.jkapp.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.alibaba.fastjson.JSON.parseObject;

public class ShowConditionsFragment extends Fragment{

    private List<Condition> dataList;
    private Doctor doctor;
    String result=null;
    View view;
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if (hidden){
//            initData();
//        }
//        super.onHiddenChanged(hidden);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_show_conditions, container, false);
        initData();
        dataList = parseObject(result, new TypeReference<List<Condition>>() {
        });
        ListView listView = view.findViewById(R.id.show_lv_conditisons);
        MyAdapter adapter = new MyAdapter(getActivity(), R.layout.adapter_condition, dataList);
        listView.setAdapter(adapter);
        if (doctor!=null&&doctor.getRole().equals("1")) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), AddReplyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("condition", dataList.get(position));
                    bundle.putSerializable("doctor",doctor);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try{
                if (doctor != null && doctor.getRole().equals("1")){
                    result = HttpUntil.getUntil(Service.conditionForDoctor+"?did="+doctor.getDid());
                }else {
                    Object param = DataManager.getParam(getActivity(), "id", "", "userInfo");
                    String uid = String.valueOf(param);
                    result = HttpUntil.getUntil(Service.conditionForUser+"?uid="+uid);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    static class MyAdapter extends ArrayAdapter<Condition> {
        private final List<Condition> dataList;
        private final int resourceId;
        public MyAdapter(Context context, int resource, List<Condition> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
            this.dataList = objects;
        }
        @NonNull
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            TextView cid = view.findViewById(R.id.cid);
            TextView time = view.findViewById(R.id.time);
            TextView symptoms = view.findViewById(R.id.symptoms);
            TextView detail = view.findViewById(R.id.detail);

            cid.setText(dataList.get(position).getCid() + "");
            time.setText(dataList.get(position).getTime());
            symptoms.setText(dataList.get(position).getSymptoms());
            detail.setText(dataList.get(position).getDetails());
            return view;
        }
    }
    public void initData(){
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
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




