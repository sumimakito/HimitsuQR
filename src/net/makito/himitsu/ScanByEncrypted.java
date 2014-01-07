package net.makito.himitsu;

import com.google.zxing.WriterException;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.widget.AutoCompleteTextView.Validator;
import android.view.View.OnClickListener;
import android.view.View;
import net.makito.himitsu.AES;

public class ScanByEncrypted extends Activity implements View.OnClickListener {

	private TextView SBEContentEncrypted;
	private TextView SBEPassword;
	private TextView SBEDecOutput;
	private Button SBEBtnDecrypted;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_by_encrypted);
		SBEContentEncrypted = (TextView) findViewById(R.id.SBEContentEncrypted);
		SBEPassword = (TextView) findViewById(R.id.SBEPassword);
		SBEDecOutput = (TextView) findViewById(R.id.SBEDecOutput);
		SBEBtnDecrypted = (Button) findViewById(R.id.SBEBtnDecrypt);
		SBEBtnDecrypted.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.SBEBtnDecrypt:
			Valid();
			break;
		default:
			break;
		}
	}
	
	private void Valid() {  //验证输入内容及密码
		String mContentEnc= SBEContentEncrypted.getText().toString();
		String mPassword = SBEPassword.getText().toString();
		if (TextUtils.isEmpty(mContentEnc)) {
			Toast.makeText(this, "请输入加密内容", Toast.LENGTH_LONG).show();
		} else if (TextUtils.isEmpty(mPassword)) {
			Toast.makeText(this, "请输入条码密码", Toast.LENGTH_LONG).show();
		} else {
			do_decrypt();
		}
	}
	private void do_decrypt() {
		String mContentEnc= SBEContentEncrypted.getText().toString();
		String mPassword = SBEPassword.getText().toString();
		String mDecrypted = net.makito.himitsu.AES.decrypt(mPassword, mContentEnc);
		SBEDecOutput.setText("字符串解密结果: "+mDecrypted);
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
