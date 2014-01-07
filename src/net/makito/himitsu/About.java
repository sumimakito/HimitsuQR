package net.makito.himitsu;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;

import android.net.Uri;
import android.os.Bundle;
import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
import android.view.View;

public class About extends Activity {


	public Button BtnCheckUpdate;
	public String vsUri = "http://pan.baidu.com/s/15okeY#dir/path=%2FAndroid-APP%E5%BC%80%E5%8F%91%2FHimitsu%E4%BA%8C%E7%BB%B4%E7%A0%81-%E7%89%88%E6%9C%AC%E4%BB%93%E5%BA%93";

	LayoutInflater 		inflater;
	View			 	staff;
	View 				update;
	ViewPager 			viewPager;
	PagerTabStrip 		tabStrip; 
	List<View> 			viewList=new ArrayList<View>();
	List<String> 		viewTitle=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		BtnCheckUpdate = (Button) findViewById(R.id.btn_check_update);
		/*BtnCheckUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_check_update:
					VersionsStore();
					break;
				default:
					break;
			}
		}});*/
		init();
	}
	
	public void init(){
		viewPager	=(ViewPager) findViewById(R.id.viewpager);
		tabStrip	=(PagerTabStrip) findViewById(R.id.pagertab);
		inflater	=this.getLayoutInflater().from(this);
		staff		=inflater.inflate(R.layout.about_staff, null);
		update	=inflater.inflate(R.layout.about_update,null);
		tabStrip.setTabIndicatorColor(Color.rgb(255, 255, 255));  //FF9C95 RGB 255, 156, 149
		tabStrip.setDrawFullUnderline(true);  
		tabStrip.setTextColor(Color.WHITE);
		tabStrip.setBackgroundColor(Color.rgb(154, 205, 50));
		viewList	.add(staff);  
		viewTitle	.add("STAFF");
		viewList	.add(update);
		viewTitle	.add("检查更新");
		viewPager	.setAdapter(new MyPagerAdapter(viewList, viewTitle));
	}

	private void VersionsStore() {
		Uri uri = Uri.parse(vsUri);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		startActivity(intent);
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