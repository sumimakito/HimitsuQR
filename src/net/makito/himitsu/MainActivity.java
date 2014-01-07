package net.makito.himitsu;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;

import android.net.Uri;
import android.os.Bundle;
import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
	public Button mainBtnNewCode;
	public Button mainBtnScanCode;
	public Button mainBtnSBE;
	public String vsUri = "http://pan.baidu.com/s/15okeY#dir/path=%2FAndroid-APP%E5%BC%80%E5%8F%91%2FHimitsu%E4%BA%8C%E7%BB%B4%E7%A0%81-%E7%89%88%E6%9C%AC%E4%BB%93%E5%BA%93";
	private FeedbackAgent agent;

	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 1, 0, "小工具");
		menu.add(0, 2, 0, "关于");// .setIcon(R.drawable.ic_launcher);
		menu.add(0, 3, 0, "版本仓库");
		menu.add(0, 4, 0, "介绍页面");
		//menu.add(0, 5, 0, "用户反馈");
		menu.add(0, 6, 0, "退出");

		// return true;
		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			showToolsDialog();
			break;
		case 2:
			showAbout();
			break;
		case 3:
			Uri uri = Uri.parse(vsUri);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			break;
		case 4:
			Intent introIntent = new Intent(this, Intro.class);
			startActivity(introIntent);
			break;
		case 5:
		    agent.startFeedbackActivity();
			break;
		case 6:
			android.os.Process.killProcess(android.os.Process.myPid());
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void showAbout() {
		Intent aboutint = new Intent(MainActivity.this, About.class);
		startActivity(aboutint);
		// this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    FeedbackAgent agent = new FeedbackAgent(this);
		
		UmengUpdateAgent.update(this);
		agent.sync();
		
		
		//Log.d("DeviceInfo", getDeviceInfo(MainActivity.this));

		// 判断初次启动
		SharedPreferences setting = getSharedPreferences("Config.ini", 0);
		Boolean user_first = setting.getBoolean("FIRST_RUN", true);
		if (user_first) {
			setting.edit().putBoolean("FIRST_RUN", false).commit();
			Intent intent_intro = new Intent(this, Intro.class);
			startActivity(intent_intro);
		} else {

		}

		mainBtnNewCode = (Button) findViewById(R.id.main_btn_newcode);
		mainBtnScanCode = (Button) findViewById(R.id.main_btn_scancode);
		mainBtnSBE = (Button) findViewById(R.id.main_btn_sbe);
		mainBtnNewCode.setOnClickListener(this);
		mainBtnScanCode.setOnClickListener(this);
		mainBtnSBE.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_newcode:
			Intent intent1 = new Intent();
			intent1.setClass(this, NewCodeActivity.class);
			startActivity(intent1);
			break;
		case R.id.main_btn_scancode:
			Intent intent2 = new Intent();
			intent2.setClass(this, ScanCodeActivity.class);
			startActivity(intent2);
			break;
		case R.id.main_btn_sbe:
			Intent intent3 = new Intent();
			intent3.setClass(this, ScanByEncrypted.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}

	public void showToolsDialog() {
		final Builder builder = new AlertDialog.Builder(this);
		builder.setItems(new String[] { "二维码阅读器", "生成普通二维码" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							startActivity(new Intent(MainActivity.this, ToolQRScanner.class));
							break;
						case 1:
							startActivity(new Intent(MainActivity.this, ToolCreateQR.class));
							break;
						}
					}
				});
		builder.create().show();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	public static String getDeviceInfo(Context context) {
	    try{
	      org.json.JSONObject json = new org.json.JSONObject();
	      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
	          .getSystemService(Context.TELEPHONY_SERVICE);
	  
	      String device_id = tm.getDeviceId();
	      
	      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	          
	      String mac = wifi.getConnectionInfo().getMacAddress();
	      json.put("mac", mac);
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = mac;
	      }
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
	      }
	      
	      json.put("device_id", device_id);
	      
	      return json.toString();
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  return null;
	}

}
