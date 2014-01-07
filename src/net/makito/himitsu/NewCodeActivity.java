package net.makito.himitsu;

import javax.crypto.EncryptedPrivateKeyInfo;

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

public class NewCodeActivity extends Activity implements View.OnClickListener {
	private TextView newcodeContent;
	private TextView newcodePassword;
	private Button newcodeBtnGenerate;
	private ImageView newcodeQRView;

	public static ProgressDialog mpDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcode);
		newcodeContent = (TextView) findViewById(R.id.tool_create_tv);
		newcodePassword = (TextView) findViewById(R.id.newcode_password);
		newcodeQRView = (ImageView) findViewById(R.id.newcodeQRView);
		newcodeBtnGenerate = (Button) findViewById(R.id.tool_create_btn);
		newcodeBtnGenerate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tool_create_btn:
			Valid();
			break;
		default:
			break;
		}
	}

	private void Valid() { // 验证输入内容及密码
		String mContent = newcodeContent.getText().toString();
		String mPassword = newcodePassword.getText().toString();
		if (TextUtils.isEmpty(mContent)) {
			Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_LONG).show();
		} else if (TextUtils.isEmpty(mPassword)) {
			Toast.makeText(this, "请输入二维码密码", Toast.LENGTH_LONG).show();
		} else {
			mProgress();
			mpDialog.show();
			String mEncrypted = net.makito.himitsu.AES.encrypt(mPassword,
					mContent);
			Intent dataIntent = new Intent();
			dataIntent.setClass(this, ImageViewer.class);
			dataIntent.putExtra("RawData", mEncrypted);
			dataIntent.putExtra("Refer", "NewCode");
			startActivity(dataIntent);
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

	private void mProgress() {
		mpDialog = new ProgressDialog(this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		// mpDialog.setTitle("提示");//设置标题
		mpDialog.setMessage("请稍候...");
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
	}
}