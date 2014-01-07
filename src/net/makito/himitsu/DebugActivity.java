package net.makito.himitsu;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class DebugActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug_output);
		
		EditText deviceEditText = (EditText) findViewById(R.id.debug_device);
		EditText macEditText = (EditText) findViewById(R.id.debug_mac);
		
		String json_data = UMengDebug.getDeviceInfo(this);
		try {
			JSONObject jObject = new JSONObject(json_data);
			deviceEditText.setText(jObject.getString("device_id"));
			macEditText.setText(jObject.getString("mac"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "获取调试信息失败!", Toast.LENGTH_SHORT).show();
			this.finish();
		}
	}
}
