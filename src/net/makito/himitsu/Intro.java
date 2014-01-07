package net.makito.himitsu;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class Intro extends Activity implements OnPageChangeListener {

	LayoutInflater inflater;
	ViewPager viewPager;
	PagerTabStrip tabStrip;
	List<View> viewList = new ArrayList<View>();
	List<String> viewTitle = new ArrayList<String>();

	private AlphaAnimation animAlpha; // 提示文字淡出
	private Handler mHandler;
	private TextView splashHint;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		init();
		mHandler = new Handler();
		animAlpha = new AlphaAnimation(1.0f, 0.0f);
		animAlpha.setDuration(1000);
		
		splashHint = (TextView) findViewById(R.id.splashHint);
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				splashHint.startAnimation(animAlpha);
			}
		}, 800);
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				splashHint.setVisibility(View.INVISIBLE);
			}
		}, 1800);

	}

	public void init() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		tabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		inflater = this.getLayoutInflater().from(this);
		View intro0 = inflater.inflate(R.layout.intro_0, null);
		View intro1 = inflater.inflate(R.layout.intro_1, null);
		View intro2 = inflater.inflate(R.layout.intro_2, null);
		View intro3 = inflater.inflate(R.layout.intro_3, null);
		View intro4 = inflater.inflate(R.layout.intro_4, null);
		tabStrip.setTabIndicatorColor(Color.rgb(255, 255, 255)); // FF9C95 RGB
																	// 255, 156,
																	// 149
		tabStrip.setDrawFullUnderline(true);
		tabStrip.setTextColor(Color.WHITE);
		tabStrip.setBackgroundColor(Color.rgb(154, 205, 50));
		viewList.add(intro0);
		viewTitle.add("0");
		viewList.add(intro1);
		viewTitle.add("1");
		viewList.add(intro2);
		viewTitle.add("2");
		viewList.add(intro3);
		viewTitle.add("3");
		viewList.add(intro4);
		viewTitle.add("4");
		viewPager.setAdapter(new MyPagerAdapter(viewList, viewTitle));
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 4:
			Handler delayHandler = new Handler();
			delayHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Intro.this.finish();
				}
			}, 2000);
			break;
		}
	}
}
