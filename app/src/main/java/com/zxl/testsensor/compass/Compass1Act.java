package com.zxl.testsensor.compass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zxl.testsensor.R;

/**
 * 指南针
 * 此程序使用手机传感器来显示指南针，当用户移动手机方向时，让指南针始终指向南方。
 *
 * @author tangliang
 */
public class Compass1Act extends Activity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SampleView mView;
    private Button mBtn;
    private TextView mTvValues1, mTvValues2, mTvValues3;
    float[] mValues;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.act_compass1);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mView = (SampleView) findViewById(R.id.sampleview01);
        mTvValues1 = (TextView) findViewById(R.id.tv_values_1);
        mTvValues2 = (TextView) findViewById(R.id.tv_values_2);
        mTvValues3 = (TextView) findViewById(R.id.tv_values_3);
        mBtn = (Button) findViewById(R.id.button01);
    }

    /**
     * 第一个角度：表示手机顶部朝向正北方的夹角。 当手机绕着Z轴旋转时，该角度值发送改变。
     * 例如当该角度为0时，表明手机顶部朝向正北；该角度为90时，代表手机顶部朝向正东；该角度为180时，代表手机顶部朝向正南；该角度为270时，代表手机顶部朝向正西。
     * 第二个角度：表示手机顶部或尾部翘起的角度。当手机绕着X轴倾斜时，该角度值发送改变。该角度的取值范围是-180~180。
     * 假设将手机屏幕朝上水平放在桌子上，如果桌子是完全水平的，该角度值应该是0。加入从手机顶部开始抬起，直到将手机沿X轴旋转180度（屏幕向下水平放在桌面上），在这个旋转过程中，该角度值会从0变化为-180。也就是说，从手机顶部抬起时，该角度值会逐渐减小，直到等于-180；如果从手机底部开始抬起，知道将手机沿X轴旋转180度（屏幕向下水平放在桌面上），该角度值会从0变化为180。也就是说，从手机顶部抬起时，该角度值会逐渐增大，直到等于180。
     * 第三个角度：表示手机左侧或右侧翘起的角度。当手机绕着Y轴倾斜时，该角度值发送改变。该角度的取值范围是-90~90。
     * 假设将手机屏幕朝上水平放在桌子上，如果桌子是完全水平的，该角度值应该是0。假设将手机左侧逐渐抬起，知道将手机沿Y轴旋转90度(手机与桌面垂直),在这个旋转过程中，该角度值会从0变化为-90。也就是说，从手机左侧抬起时，该角度值会逐渐减小，直到等于-90；如果将手机右侧逐渐抬起，知道将手机沿Y轴旋转90度(手机与桌面垂直),在这个旋转过程中，该角度值会从0变化为90。也就是说，从手机右侧抬起时，该角度值会逐渐增大，直到等于90
     */
    private final SensorEventListener mListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            mValues = event.values;
            if (mView != null) {
                mView.setValues(mValues);
                mView.invalidate();
                mTvValues1.setText("" + (int) mValues[0]);
                mTvValues2.setText("" + (int) mValues[1]);
                mTvValues3.setText("" + (int) mValues[2]);
            }
            if (mBtn != null) {
                int direction = (int) mValues[0];
                String msg = "";
                if (direction > 45 && direction <= 135) {
                    msg = "东";
                } else if (direction > 135 && direction < 225) {
                    msg = "南";
                } else if (direction > 225 && direction < 315) {
                    msg = "西";
                } else {
                    msg = "北";
                }
                mBtn.setText(msg + direction + "°");
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mListener);
    }

}
