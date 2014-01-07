package net.makito.himitsu;

import java.security.PublicKey;

import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.widget.AutoCompleteTextView.Validator;
import android.view.View.OnClickListener;
import android.view.View;

public class ScanCodeActivity extends Activity implements View.OnClickListener {
	public Button scancodeBtnDecrypt;
	public EditText scancodePassword;
	public TextView scancodeHintPassword;
	public TextView scancodeOutput;
	public String scanResult;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scancode);
		
		if (ZXingExist.isAvilible(this, "com.srowen.bs.android")) {
			
		} else {
			Toast.makeText(this, "没有安装需要的二维码扫描器 现在将进行安装", Toast.LENGTH_SHORT).show();
			ZXingExist.Install(this);
		}
		

		scancodeBtnDecrypt = (Button) findViewById(R.id.scancode_btn_decrypt);
		scancodePassword = (EditText) findViewById(R.id.scancode_password);
		scancodeHintPassword = (TextView) findViewById(R.id.scancode_hint_password);
		scancodeOutput = (TextView) findViewById(R.id.scancode_output);
		
		scancodeBtnDecrypt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scancode_btn_decrypt:
			String mPassword = scancodePassword.getText().toString();
			if (TextUtils.isEmpty(mPassword)) {
				Toast.makeText(this, "请输入二维码密码", Toast.LENGTH_LONG).show();
			} else {
				if (ZXingExist.isAvilible(this, "com.srowen.bs.android")) {
				    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				    startActivityForResult(intent, 0);
				} else {
					Toast.makeText(this, "没有安装需要的二维码扫描器 现在将进行安装", Toast.LENGTH_SHORT).show();
					ZXingExist.Install(this);
				}
			}
	    break;
	    default:
	    	break;
		}
	}  
	
	//二维码扫描回调接口
	
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
         if (resultCode == RESULT_OK) {
        	 String mPassword = scancodePassword.getText().toString();
        	 String scanResult = intent.getStringExtra("SCAN_RESULT");
        	 String mDecrypted = net.makito.himitsu.AES.decrypt(mPassword, scanResult);
        	 scancodeOutput.setText("二维码解密结果:\n"+mDecrypted);
        	 scancodeBtnDecrypt.setClickable(false);
          }
         else if (resultCode == RESULT_CANCELED) {
        	 scancodeOutput.setText("扫描失败，请按返回键并重新扫描！");
        	 scancodeBtnDecrypt.setClickable(false);
         }
        }
       }

    
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}

}
