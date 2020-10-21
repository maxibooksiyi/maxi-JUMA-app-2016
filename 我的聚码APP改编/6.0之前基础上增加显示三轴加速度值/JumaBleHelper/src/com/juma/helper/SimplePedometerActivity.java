package com.juma.helper;

import com.juma.helper.R.layout;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SimplePedometerActivity extends Activity implements SensorEventListener, StepListener {
  private TextView textView;
  private SimpleStepDetector simpleStepDetector;
  private SensorManager sensorManager;
  private Sensor accel;
  private static final String TEXT_NUM_STEPS = "Number of Steps: ";
  private int numSteps;
  
  TextView tvstep;
  TextView tvX;
  TextView tvY;
  TextView tvZ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   // textView = new TextView(this);
   // textView.setTextSize(30);
    setContentView(R.layout.xyz);
    tvstep=(TextView)findViewById(R.id.tvstep);
    tvX=(TextView)findViewById(R.id.tvX);
    tvY=(TextView)findViewById(R.id.tvY);
    tvZ=(TextView)findViewById(R.id.tvZ);

    // Get an instance of the SensorManager
    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    simpleStepDetector = new SimpleStepDetector();
    simpleStepDetector.registerListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    numSteps = 0;
    tvstep.setText(TEXT_NUM_STEPS + numSteps);
   // tvX.setText("X:"+ Sensor.value[0]);
    //textView.setText(TEXT_NUM_STEPS + numSteps);
    sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
  }

  @Override
  public void onPause() {
    super.onPause();
    sensorManager.unregisterListener(this);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      simpleStepDetector.updateAccel(
          event.timestamp, event.values[0], event.values[1], event.values[2]);
      tvX.setText("X轴加速度:"+ event.values[0]);
      tvY.setText("Y轴加速度:"+ event.values[1]);
      tvZ.setText("Z轴加速度:"+ event.values[2]);
      //tvX.setText((int) event.values[0]);这句一加上就会停止运行。。
      
    }
    
  }
  /*
  public void xyzview(SensorEvent event)
  {
	  tvX.setText("X:"+ event.values[0]);
  }
  */
  @Override
  public void step(long timeNs) {
    numSteps++;
    tvstep.setText(TEXT_NUM_STEPS + numSteps);
    //textView.setText(TEXT_NUM_STEPS + numSteps);
  }

}
