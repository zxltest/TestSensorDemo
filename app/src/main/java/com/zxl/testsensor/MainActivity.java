package com.zxl.testsensor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zxl.testsensor.compass.ThirdActivity;
import com.zxl.testsensor.shake.SecondActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvTest1;
    private TextView mTvTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mTvTest1 = (TextView) findViewById(R.id.tv_test1);
        mTvTest2 = (TextView) findViewById(R.id.tv_test2);
        mTvTest1.setOnClickListener(this);
        mTvTest2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test1:
                clickTv1();
                break;
            case R.id.tv_test2:
                clickTv2();
                break;
        }
    }

    public void clickTv1() {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void clickTv2() {
        startActivity(new Intent(this, ThirdActivity.class));
    }
}
