package com.juma.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
public class SurfaceViewActivity3 extends Activity {
public void onCreate(Bundle s){
super.onCreate(s);
//全屏显示
requestWindowFeature(Window.FEATURE_NO_TITLE);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
WindowManager.LayoutParams.FLAG_FULLSCREEN);
setContentView(new MyView(this));
}
public class MyView extends SurfaceView implements Callback,Runnable{
public static final int TIME_IN_FRAME =50;
Paint mPaint = null;
Paint mTextPaint = null;
SurfaceHolder mSurfaceHolder = null;
boolean mRunning = true;//这个之前是flase，所以要触摸屏幕时才会画，现在我该为true了，就不需要触摸了。
Canvas mCanvas = null;
private Path mPath;
private float mPosX,mPosY;
public MyView(Context context){
super(context);
this.setFocusable(true);
this.setFocusableInTouchMode(true);
mSurfaceHolder = this.getHolder();
mSurfaceHolder.addCallback(this);
mCanvas = new Canvas();
mPaint = new Paint();
mPaint.setColor(Color.BLACK);
mPaint.setAntiAlias(true);
mPaint.setStyle(Paint.Style.STROKE);
mPaint.setStrokeCap(Paint.Cap.ROUND);
mPaint.setStrokeWidth(6);
mPath = new Path();
mTextPaint = new Paint();
mTextPaint.setColor(Color.BLACK);
mTextPaint.setTextSize(15);
}
/*
public void onTouchEvent(){
//int action = event.getAction();
SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
float x=settings.getInt("x",0);
float y=settings.getInt("y",0);
//float x = event.getX();//这块又是如此，跟之前一样呵呵，我再次尝试改改。
//float y = event.getY();
mPath.quadTo(mPosX, mPosY, x, y);
/*
switch(action){
case MotionEvent.ACTION_DOWN:
mPath.moveTo(x, y);
break;
case MotionEvent.ACTION_MOVE:
mPath.quadTo(mPosX, mPosY, x, y);
break;
case MotionEvent.ACTION_UP:
//mPath.reset();
break;
}
*/
//记录当前触摸点得当前得坐标
//mPosX = x;
//mPosY = y;
//return true;
//}

private void onDraw(){
	
	SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
	float x=settings.getInt("x",0);
	float y=settings.getInt("y",0);
	//float x = event.getX();//这块又是如此，跟之前一样呵呵，我再次尝试改改。
	//float y = event.getY();
	mPath.quadTo(mPosX, mPosY, x, y);
	/*
	switch(action){
	case MotionEvent.ACTION_DOWN:
	mPath.moveTo(x, y);
	break;
	case MotionEvent.ACTION_MOVE:
	mPath.quadTo(mPosX, mPosY, x, y);
	break;
	case MotionEvent.ACTION_UP:
	//mPath.reset();
	break;
	}
	*/
	//记录当前触摸点得当前得坐标
	mPosX = x;
	mPosY = y;
mCanvas.drawColor(Color.WHITE);
//绘制曲线
mCanvas.drawPath(mPath, mPaint);
mCanvas.drawText("当前X："+mPosX,0,20,mTextPaint);
mCanvas.drawText("当前Y:"+mPosY,0,40,mTextPaint);
}
@SuppressLint("WrongCall")
public void run() {
// TODO Auto-generated method stub
while(mRunning){
long startTime = System.currentTimeMillis();
synchronized(mSurfaceHolder){
mCanvas = mSurfaceHolder.lockCanvas();
onDraw();
mSurfaceHolder.unlockCanvasAndPost(mCanvas);
}
long endTime = System.currentTimeMillis();
int diffTime = (int) (endTime - startTime);
while(diffTime<=TIME_IN_FRAME){
diffTime =(int)(System.currentTimeMillis()-startTime);
Thread.yield();
}
}
}
@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
int height) {
// TODO Auto-generated method stub
}
@Override
public void surfaceCreated(SurfaceHolder holder) {
mRunning = true;
new Thread(this).start();
}
@Override
public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
mRunning = false;
}
}
}