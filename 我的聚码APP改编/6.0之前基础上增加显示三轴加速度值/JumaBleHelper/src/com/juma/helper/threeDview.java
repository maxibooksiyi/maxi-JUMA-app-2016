package com.juma.helper;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.juma.helper.MySurfaceView;

//import com.juma.helper.MySurfaceView;
//import android.opengl.GLSurfaceView;
//import MySurfaceView;



//import android.view.MotionEvent;
//Ŀǰ�﷨�ϵĴ���û�˵��������߼��Ϻ�����Щ������Ҫ��
public class threeDview extends Activity{
	 /** Called when the activity is first created. */
		private MySurfaceView mSurfaceView;//����MySurfaceView����
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.threed);     
	        mSurfaceView=new MySurfaceView(this);//����MySurfaceView����
	        mSurfaceView.requestFocus();//��ȡ����
	        mSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���
	        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);//������Բ��ֵ�����
	        ll.addView(mSurfaceView);
	    }
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			mSurfaceView.onPause();
		}
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			mSurfaceView.onResume();
		}  
	}