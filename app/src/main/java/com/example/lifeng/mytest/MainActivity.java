package com.example.lifeng.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lifeng.mytest.activity.CanvasActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickView1(View view) {
        Intent intent = new Intent(this, CanvasActivity.class);
        startActivity(intent);
    }


}
