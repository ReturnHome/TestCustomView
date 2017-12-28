package com.example.lifeng.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.lifeng.mytest.R;
import com.example.lifeng.mytest.view.Bezier2;
import com.example.lifeng.mytest.view.CheckView;

/**
 * Created by lifeng on 2017/12/18.
 */

public class CanvasActivity extends AppCompatActivity{

    private CheckView mCheckView;
    private RadioButton mRadioBatton1;
    private RadioButton mRadioBatton2;
    private Bezier2 mViewBezier;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_canvas);
//        mViewBezier = (Bezier2)findViewById(R.id.viewBezier);
//        mRadioBatton1 = (RadioButton) findViewById(R.id.radioButton1);
//        mRadioBatton2 = (RadioButton) findViewById(R.id.radioButton2);
//        mRadioBatton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRadioBatton1.setChecked(true);
//                mRadioBatton2.setChecked(false);
//                mViewBezier.setMode(true);
//            }
//        });
//        mRadioBatton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRadioBatton1.setChecked(false);
//                mRadioBatton2.setChecked(true);
//                mViewBezier.setMode(false);
//            }
//        });
    }
}
