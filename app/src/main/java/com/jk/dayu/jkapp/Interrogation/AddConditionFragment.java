package com.jk.dayu.jkapp.Interrogation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.jk.dayu.jkapp.BaseModule.DataManager;
import com.jk.dayu.jkapp.R;
import com.jk.dayu.jkapp.service.Service;
import com.jk.dayu.jkapp.untils.IDUtils;
import com.jk.dayu.jkapp.untils.MyThreadUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddConditionFragment extends Fragment {
    MyThreadUtil myThreadUtil = new MyThreadUtil();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_condition, container, false);
        final EditText symptoms = view.findViewById(R.id.add_c_symptoms);
        final EditText details = view.findViewById(R.id.add_c_detial);
        Spinner time = view.findViewById(R.id.time);
        Button confirm = view.findViewById(R.id.add_c_btn_confrim);

        String strDid  = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("did");
        final long did = Long.parseLong(strDid);
        final List<String> data_list = new ArrayList<>();
        data_list.add("");
        data_list.add("Within 3 days");
        data_list.add("Within a week");
        data_list.add("Within one month");
        data_list.add("Within one year");
        data_list.add("More than one year");
        final Condition condition = new Condition();
        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                condition.setTime(data_list.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object param = DataManager.getParam(getActivity(), "id", "", "userInfo");
                String uid = String.valueOf(param).trim();
                condition.setCid(IDUtils.getNewId());
                condition.setUid(Long.parseLong(uid));
                condition.setDid(did);
                condition.setSymptoms(symptoms.getText().toString());
                condition.setDetails(details.getText().toString());
                String result = myThreadUtil.myThread(Service.saveCondition, JSONObject.toJSONString(condition));
                if (result.equals("success")){
                    Toast.makeText(getActivity(), "submit successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "submit failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
