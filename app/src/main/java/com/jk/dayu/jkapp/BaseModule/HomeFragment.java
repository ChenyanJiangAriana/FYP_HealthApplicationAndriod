package com.jk.dayu.jkapp.BaseModule;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jk.dayu.jkapp.HealthModule.AddTimeActivity;
import com.jk.dayu.jkapp.HealthModule.HealthSelectActivity;
import com.jk.dayu.jkapp.HealthModule.TimeBean;
import com.jk.dayu.jkapp.HeartBeatModule.HeartBeatActivity;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.stepModule.StepActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private Button hbBtn;
    private Button stepBtn;
    private Button jkBtn;
    private ArrayList<HomeBean> dataList;

    private ListView listView;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        hbBtn = view.findViewById(R.id.heartBeat);
        hbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHeartBeatAct();
            }
        });

        stepBtn = view.findViewById(R.id.step);
        stepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStepAct();
            }
        });

        jkBtn = view.findViewById(R.id.jktest);
        jkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoJKTest();
            }
        });
        listView = view.findViewById(R.id.listView);
        return view;
    }

    private String  getWeather(){
        return "";
    }

    public void initData(){
        dataList = new ArrayList<>();
        HomeBean home2 = new HomeBean(HomeAdapter.TYPE_HEALTH);
        home2.bmi = DataManager.healthBean(getContext()).bmi;
        home2.date = DataManager.healthBean(getContext()).date;
        dataList.add(home2);

        String weather = getWeather();
        HomeBean home3 = new HomeBean(HomeAdapter.TYPE_WEATHER);
        if (weather.equals("")){
            home3.time = "No internet";
            home3.tip = "";
            home3.date = "";
            dataList.add(home3);
        }else {
            try {
                JSONObject jObject = new JSONObject(weather);
                JSONObject data = jObject.getJSONObject("data");
                JSONArray wendua =  data.getJSONArray("forecast");
                JSONObject wenduo = (JSONObject) wendua.get(0);
                home3.time = wenduo.getString("high")+"  "+wenduo.getString("low");
                home3.tip = wenduo.getString("fx")+wenduo.getString("fl")+","+wenduo.getString("type");
                home3.date = data.getString("ganmao");
                dataList.add(home3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ???????????? health reminder
        List<TimeBean> timeList = DataManager.timeBean(getActivity());
        if (timeList==null||timeList.size()==0){
            HomeBean home = new HomeBean(HomeAdapter.TYPE_TIME,"Click here to add your reminder","");
            dataList.add(home);
        }else {
            for(TimeBean time:timeList){
                HomeBean home = new HomeBean(HomeAdapter.TYPE_TIME,time.tip,time.time);
                NotiManager.addAlert(getActivity(),time.time,time.tip,time.id);
                dataList.add(home);
            }
        }

        HomeAdapter adapter = new HomeAdapter(getActivity(), dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id){
                if(dataList.get(position).type==HomeAdapter.TYPE_HEALTH){
                    gotoJKTest();
                }else if(dataList.get(position).type==HomeAdapter.TYPE_STEP){
                    gotoStepAct();
                }else if(dataList.get(position).type==HomeAdapter.TYPE_TIME){
                    gotoTime();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                if (position<=2) return true;
                //??????AlertDialog.Builder???????????????????????????????????????????????????????????????
                //Define AlertDialog.Builder object to pop up a confirmation delete dialog when long pressing a list item
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("delete confirm?");
                builder.setTitle("reminder");
                //Add the setPositiveButton() method of the AlertDialog.
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataList.remove(position);
                        List<TimeBean> timeList = DataManager.timeBean(getActivity());
                        DataManager.remove(getActivity(), timeList.get(position-3));
                        if(dataList.size()==3){
                            HomeBean home = new HomeBean(HomeAdapter.TYPE_TIME,"Click to add reminder","");
                            dataList.add(home);
                        }
                        HomeAdapter adapter = new HomeAdapter(getActivity(), dataList);
                        listView.setAdapter(adapter);
                    }
                });

                //??????AlertDialog.Builder?????????setNegativeButton()??????
                //Add the setNegativeButton() method of the AlertDialog.
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.create().show();
                return true;
            }
        });
    }

    // ????????????activity
    //Jump to heartbeatactivity
    private void gotoHeartBeatAct(){

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        2);
            }
            return;
        }

        Intent intent = new Intent(getActivity(), HeartBeatActivity.class);
        startActivity(intent);
    }

    // ????????????activity
    //Jump to step activity
    private  void gotoStepAct(){
        if(!DataManager.isLogin(getActivity())) return;
        Intent intent = new Intent(getActivity(), StepActivity.class);
        startActivity(intent);
    }
    // ??????????????????activity
    //Jump to health test activity
    private  void gotoJKTest(){
        if(!DataManager.isLogin(getActivity())) return;
        Intent intent = new Intent(getActivity(), HealthSelectActivity.class);
        startActivity(intent);
    }

    // ????????????????????????activity
    //Jump to add health time activity
    private  void gotoTime(){
        if(!DataManager.isLogin(getActivity())) return;
        Intent intent = new Intent(getActivity(), AddTimeActivity.class);
        startActivity(intent);
    }

    public void login(){
        DataManager.isLogin(getActivity());
    }
}
