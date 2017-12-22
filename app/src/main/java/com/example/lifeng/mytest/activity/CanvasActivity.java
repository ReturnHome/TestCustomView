package com.example.lifeng.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lifeng.mytest.R;
import com.example.lifeng.mytest.view.CheckView;

/**
 * Created by lifeng on 2017/12/18.
 */

public class CanvasActivity extends AppCompatActivity {

    private CheckView mCheckView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_canvas);
//        mCheckView = (CheckView) findViewById(R.id.checkView);
//        mCheckView.setAnimDuration(3000);
    }

    public void checkButton(View view) {
        mCheckView.check();
    }

    public void uncheckButton(View view) {
        mCheckView.unCheck();
    }
}
