package net.makito.himitsu;

import com.google.zxing.WriterException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewer extends Activity {

	private ImageView newImageQR;
	private TextView textHint;

	private AlphaAnimation animAlpha; // 提示文字淡出
	private Handler mHandler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageviewer);

		newImageQR = (ImageView) findViewById(R.id.imageNewQR);
		textHint = (TextView) findViewById(R.id.ImageViewerHint);

		Intent dataIntent = getIntent();

		String mRawData = dataIntent.getStringExtra("RawData");
		String mRefer = dataIntent.getStringExtra("Refer");
		// 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）

		Bitmap qrCodeBitmap;
		try {
			qrCodeBitmap = EncodingHandler.createQRCode(mRawData, 600);
			newImageQR.setImageBitmap(qrCodeBitmap);
			if (mRefer.equals("NewCode")) {
				NewCodeActivity.mpDialog.dismiss();
			} else if (mRefer.equals("ToolCreate")) {
				ToolCreateQR.mpDialog.dismiss();
			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 淡出动画
		animAlpha = new AlphaAnimation(1.0f, 0.0f);
		animAlpha.setDuration(2000);
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				textHint.startAnimation(animAlpha);
			}
		}, 3000);

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				textHint.setVisibility(View.INVISIBLE);
			}
		}, 5000);

	}

}
