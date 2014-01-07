package net.makito.himitsu;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ToolQRScanner extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_qrscanner);

		if (ZXingExist.isAvilible(this, "com.srowen.bs.android")) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} else {
			Toast.makeText(this, "没有安装需要的二维码扫描器 现在将进行安装", Toast.LENGTH_SHORT)
					.show();
			ZXingExist.Install(this);
			this.finish();
		}

	}

	// 二维码扫描回调接口

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String scanResult = intent.getStringExtra("SCAN_RESULT");

				TextView tv = (TextView) findViewById(R.id.tool_qr_result);
				
				tv.setText(scanResult);
				CharSequence text = tv.getText();
				if (text instanceof Spannable) {
					int end = text.length();
					Spannable sp = (Spannable) tv.getText();
					URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
					SpannableStringBuilder style = new SpannableStringBuilder(
							text);
					style.clearSpans();// should clear old spans
					for (URLSpan url : urls) {
						MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
						style.setSpan(myURLSpan, sp.getSpanStart(url),
								sp.getSpanEnd(url),
								Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
					}
					tv.setText(style);
				}

			} else if (resultCode == RESULT_CANCELED) {
				this.finish();
			}
		}
	}

	private class MyURLSpan extends ClickableSpan {

		private String mUrl;

		MyURLSpan(String url) {
			mUrl = url;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(mUrl);
			intent.setData(content_url);
			startActivity(intent);
		}
	}
}
