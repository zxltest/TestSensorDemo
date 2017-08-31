package com.zxl.testsensor.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.zxl.testsensor.R;


/**
 * Created by zxl on 2017/8/31.
 */

public class Compass2Act extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccel, mMagnetic;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private float[] values = new float[3];
    private float[] mR9 = new float[9];
    private Button mBtn;

    private String mText = "";
    private float mDegree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_compass2);
        mBtn = (Button) findViewById(R.id.button01);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化地磁场传感器
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    // 计算方向
    private void calculateOrientation() {
        SensorManager.getRotationMatrix(mR9, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(mR9, values);
        mDegree = (float) Math.toDegrees(values[0]);
        mText = "";
        if (mDegree >= -5 && mDegree < 5) {
            mText = "正北";
        } else if (mDegree >= 5 && mDegree < 85) {
            mText = "东北";
        } else if (mDegree >= 85 && mDegree <= 95) {
            mText = "正东";
        } else if (mDegree >= 95 && mDegree < 175) {
            mText = "东南";
        } else if ((mDegree >= 175 && mDegree <= 180) || (mDegree) >= -180 && mDegree < -175) {
            mText = "正南";
        } else if (mDegree >= -175 && mDegree < -95) {
            mText = "西南";
        } else if (mDegree >= -95 && mDegree < -85) {
            mText = "正西";
        } else if (mDegree >= -85 && mDegree < -5) {
            mText = "西北";
        }
        Log.e("", "[" + mDegree + "" + mText + "]");
        mBtn.setText(mText + mDegree + "°");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccel, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(sensorEventListener, mMagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values;
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticFieldValues = event.values;
            }
            calculateOrientation();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
