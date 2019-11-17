package com.mobitant.callbacktest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailDialog extends Dialog {
    private DetailDialog m_This;
    private String[] data = {"1", "2", "3", "4", "5", "6"};
    private ArrayList<String> list;
    private CheckBox checkBox;
    private TextView textView;
    private CustomAdapter customAdapter = null;
    private Button btn;
    private ListView listView;
    private MyClickListner listener;

    public DetailDialog(Context context, MyClickListner myClickListener) {

        super(context);
        listener = myClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        m_This = this;

        btn = findViewById(R.id.btn_accept);
        listView = findViewById(R.id.listView);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCancelable(false);

        list = new ArrayList<>();
        for(int i=0; i<data.length; i++) {
            list.add(data[i]);
        }
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(mItemClickListener);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_This.dismiss();
            }
        });

        customAdapter = new CustomAdapter(getContext(), list, listener);
        listView.setAdapter(customAdapter);
    }

    public void MyClick(final MyClickListner lisetner) {
        this.listener = listener;

        listener = new MyClickListner() {
            @Override
            public void onMyItemClick(int position) {
                lisetner.onMyItemClick(customAdapter.getChecked());
            }
        };
    }
    private final AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            customAdapter.setChecked(position);
            customAdapter.notifyDataSetChanged();
            listener.onMyItemClick(customAdapter.getChecked());

        }
    };
    class CustomAdapter extends BaseAdapter {
        private ArrayList<String> sArrayList = new ArrayList<>();
        private boolean[] isCheckedConfrim;
        private MyClickListner listener;
        private int pos;
        public CustomAdapter(Context context, ArrayList<String> mList, MyClickListner listener) {

            this.sArrayList = mList;
            this.isCheckedConfrim = new boolean[sArrayList.size()];
            this.listener = listener;
        }


        public void setChecked(int position) {
            isCheckedConfrim[position] = !isCheckedConfrim[position];
            pos = position;
        }
        public int getChecked() {
            return pos;
        }

        @Override
        public int getCount() {
            return sArrayList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return sArrayList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = LayoutInflater.from(getContext()).inflate(R.layout.select_opt, parent,false);
            TextView textView = v.findViewById(R.id.textOpt);
            CheckBox checkBox = v.findViewById(R.id.main_check_box);


            checkBox.setChecked(isCheckedConfrim[position]);
            textView.setText(sArrayList.get(position));
            return v;
        }
    }
}
