package net.makito.himitsu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ToolCreateQR extends Activity {
	private TextView mRawDataTV;
	private Button btnCreate;
	
	public static ProgressDialog mpDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_create_code);
		
		mRawDataTV = (TextView) findViewById(R.id.tool_create_tv);
		btnCreate = (Button) findViewById(R.id.tool_create_btn);
		
		btnCreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String mRawData = mRawDataTV.getText().toString();
				if (TextUtils.isEmpty(mRawData)) {
					Toast.makeText(ToolCreateQR.this, "请输入二维码内容", Toast.LENGTH_LONG).show();
				} else {
					mProgress();
					mpDialog.show();
					String mData = mRawDataTV.getText().toString();
					Intent dataIntent = new Intent();
					dataIntent.setClass(ToolCreateQR.this, ImageViewer.class);
					dataIntent.putExtra("RawData", mData);
					dataIntent.putExtra("Refer", "ToolCreate");
					startActivity(dataIntent);
				}
			}
		});
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
