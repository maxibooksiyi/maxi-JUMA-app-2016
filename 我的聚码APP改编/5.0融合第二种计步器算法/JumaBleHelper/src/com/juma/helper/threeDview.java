package com.juma.helper;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.juma.helper.MySurfaceView;

//import com.juma.helper.MySurfaceView;
//import android.opengl.GLSurfaceView;
//import MySurfaceView;



//import android.view.MotionEvent;
//目前语法上的错误没了但是运行逻辑上海存在些问题需要改
public class threeDview extends Activity{
	 /** Called when the activity is first created. */
		private MySurfaceView mSurfaceView;//声明MySurfaceView对象
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.threed);     
	        mSurfaceView=new MySurfaceView(this);//创建MySurfaceView对象
	        mSurfaceView.requestFocus();//获取焦点
	        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控
	        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);//获得线性布局的引用
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