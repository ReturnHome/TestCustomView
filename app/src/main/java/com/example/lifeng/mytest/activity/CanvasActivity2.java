package com.example.lifeng.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lifeng.mytest.R;
import com.example.lifeng.mytest.view.SetPolyToPoly;

/**
 * Created by lifeng on 2017/12/29.
 */

public class CanvasActivity2 extends AppCompatActivity {

    private SetPolyToPoly viewMatrix;
    private android.widget.RadioButton point0;
    private android.widget.RadioButton point1;
    private android.widget.RadioButton point2;
    private android.widget.RadioButton point3;
    private android.widget.RadioButton point4;
    private android.widget.RadioGroup group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_2);
        this.group = (RadioGroup) findViewById(R.id.group);
        this.point4 = (RadioButton) findViewById(R.id.point4);
        this.point3 = (RadioButton) findViewById(R.id.point3);
        this.point2 = (RadioButton) findViewById(R.id.point2);
        this.point1 = (RadioButton) findViewById(R.id.point1);
        this.point0 = (RadioButton) findViewById(R.id.point0);

        viewMatrix = (SetPolyToPoly) findViewById(R.id.viewMatrix);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.point0: viewMatrix.setTestPoint(0); break;
                    case R.id.point1: viewMatrix.setTestPoint(1); break;
                    case R.id.point2: viewMatrix.setTestPoint(2); break;
                    case R.id.point3: viewMatrix.setTestPoint(3); break;
                    case R.id.point4: viewMatrix.setTestPoint(4); break;
                }
            }
        });
    }
}
