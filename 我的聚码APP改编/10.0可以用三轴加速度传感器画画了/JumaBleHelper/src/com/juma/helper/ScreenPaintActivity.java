package com.juma.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.juma.helper.ScreenPaintView.DrawPath;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
/**
 * 
 * @category: ÆÁÄ»Îª±³¾°£¬Í¿Ñ»
 * @author wmm
 * @date: 2015-02-14
 * 
 */





public class ScreenPaintActivity extends Activity {
	
	
	SharedPreferences settings = getSharedPreferences("setting", MODE_PRIVATE);
    float x=settings.getInt("x",0);
    float y=settings.getInt("y",0);

	private ScreenPaintView tuyaView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		tuyaView = new ScreenPaintView(this, dm.widthPixels, dm.heightPixels);
		setContentView(tuyaView);
		
		 
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// ·µ»Ø¼ü
			tuyaView.undo();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_quit:
			Toast.makeText(this, "ÕýÔÚÍË³ö»­±Ê¡¤¡¤¡¤", Toast.LENGTH_LONG).show();
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}

