package com.mobitant.callbacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private Button btn;
    private DetailDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);

        dialog = new DetailDialog(MainActivity.this, myClickListener);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    MyClickListner myClickListener = new MyClickListner() {
        @Override
        public void onMyItemClick(int position) {
            Log.e("tag", "포지션 : " + position);
        }
    };
}
