package com.juma.helper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
//走步检测器，用于检测走步并计数
/**
 * 具体算法不太清楚，本算法是从谷歌计步器：Pedometer上截取的部分计步算法
 * sensor传感器的意思
 */
public class StepDetector implements SensorEventListener {

	public static int CURRENT_SETP = 0;

	public static float SENSITIVITY = 0;   //SENSITIVITY灵敏度

	private float mLastValues[] = new float[3 * 2]; //二维数组。
	private float mScale[] = new float[2];
	private float mYOffset;
	private static long end = 0;
	private static long start = 0;

	/**
	 * 最后加速度方向
	 * 
	 * 来判断方向变化
	 */
	private float mLastDirections[] = new float[3 * 2];
	private float mLastExtremes[][] = { new float[3 * 2], new float[3 * 2] };
	private float mLastDiff[] = new float[3 * 2];
	private int mLastMatch = -1;

	/**
	 * 传入上下文的构造函数
	 * 
	 * @param context
	 */
	public StepDetector(Context context) {
		// TODO Auto-generated constructor stub
		super();
		int h = 480;
		mYOffset = h * 0.5f;
		mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2))); //我想知道这得到的上什么值。传感器的值只能从这传到了
		mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));//SensorManager就是专门管理传感器的。这为什么涉及到磁场，我想不通了。。
		if (SettingsActivity.sharedPreferences == null) {
			SettingsActivity.sharedPreferences = context.getSharedPreferences(
					SettingsActivity.SETP_SHARED_PREFERENCES,
					Context.MODE_PRIVATE);
		}
		SENSITIVITY = SettingsActivity.sharedPreferences.getInt(
				SettingsActivity.SENSITIVITY_VALUE, 3);
	}

	// public void setSensitivity(float sensitivity) {
	// SENSITIVITY = sensitivity; // 1.97 2.96 4.44 6.66 10.00 15.00 22.50
	// // 33.75
	// // 50.62
	// }

	// public void onSensorChanged(int sensor, float[] values) {
	@Override
	public void onSensorChanged(SensorEvent event) {
		// Log.i(Constant.STEP_SERVER, "StepDetector");
		Sensor sensor = event.sensor;
		// Log.i(Constant.STEP_DETECTOR, "onSensorChanged");
		synchronized (this) {
			if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
			} else {
				int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
				if (j == 1) {
					float vSum = 0;
					for (int i = 0; i < 3; i++) {
						final float v = mYOffset + event.values[i] * mScale[j];
						vSum += v;
					}
					int k = 0;
					float v = vSum / 3;

					float direction = (v > mLastValues[k] ? 1: (v < mLastValues[k] ? -1 : 0));
					if (direction == -mLastDirections[k]) {
						// Direction changed
						int extType = (direction > 0 ? 0 : 1); // minumum or
						// maximum?
						mLastExtremes[extType][k] = mLastValues[k];
						float diff = Math.abs(mLastExtremes[extType][k]- mLastExtremes[1 - extType][k]);

						if (diff > SENSITIVITY) {
							boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
							boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
							boolean isNotContra = (mLastMatch != 1 - extType);

							if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
								end = System.currentTimeMillis();
								if (end - start > 500) {// 此时判断为走了一步
									Log.i("StepDetector", "CURRENT_SETP:"
											+ CURRENT_SETP);
									CURRENT_SETP++;
									mLastMatch = extType;
									start = end;    //这个应该好理解
								}
							} else {
								mLastMatch = -1;
							}
						}
						mLastDiff[k] = diff;
					}
					mLastDirections[k] = direction;
					mLastValues[k] = v;
				}
			}
		}
	}
/**********************************
 * 这个函数是一个传感器的回调函数，在其中可以根据从系统地加速度传感器获取的数值进行胳膊甩动动作的判断。主要从以下几个方面判断：
（1）人如果走起来了，一般会连续多走几步。因此，如果没有连续4-5个波动，那么就极大可能是干扰。 
（2）人走动的波动，比坐车产生的波动要大，因此可以看波峰波谷的高度，只检测高于某个高度的波峰波谷。
（3）人的反射神经决定了人快速动的极限，怎么都不可能两步之间小于0.2秒，因此间隔小于0.2秒的波峰波谷直接跳过
通过重力加速计感应，重力变化的方向，大小。与正常走路或跑步时的重力变化比对，达到一定相似度时认为是在走路或跑步。实现起来很简单，只要手机有重力感应器就能实现。
 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

}
