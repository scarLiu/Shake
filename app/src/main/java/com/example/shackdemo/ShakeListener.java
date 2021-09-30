package com.example.shackdemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeListener implements SensorEventListener {

    private static final int UPTATE_INTERVAL_TIME = 50;
    private static final int MEDUM_VALUE = 14;
    private SensorManager sensorManager;
    private Sensor sensor;
    private OnShakeListener onShakeListener;
    private Context mContext;
    private long lastUpdateTime;

    public ShakeListener(Context c) {
        mContext = c;
        start();
    }

    public void start() {
        sensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }

    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - lastUpdateTime;
        if (timeInterval < UPTATE_INTERVAL_TIME)
            return;
        lastUpdateTime = currentUpdateTime;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(Math.abs(x) > MEDUM_VALUE | Math.abs(y) > MEDUM_VALUE | Math.abs(z) > MEDUM_VALUE){ //检测到摇晃
            onShakeListener.onShake();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // 用于之后的回调
    public interface OnShakeListener {
        public void onShake();
    }
}
