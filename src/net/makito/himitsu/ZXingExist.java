package net.makito.himitsu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

public class ZXingExist {

    public static void Install(Context ctx) {  
        Intent intentInstall = new Intent();  
        String apkPath = "/data/data/" + ctx.getPackageName() + "/files";  
        String apkName = "com.srowen.bs.android_25.apk";  
        File file = new File(apkPath, apkName);  
        try {  
  InputStream is = ctx.getAssets().open("com.srowen.bs.android_25.apk");  
  
            if (!file.exists()) {  
                file.createNewFile();  
                FileOutputStream os = ctx.openFileOutput(file.getName(),  
                        Context.MODE_WORLD_WRITEABLE);  
                byte[] bytes = new byte[512];  
                int i = -1;  
                while ((i = is.read(bytes)) > 0) {  
                    os.write(bytes);  
                }  
  
                os.close();  
                is.close();  
                Log.e("", "----------- has been copy to ");  
            } else {  
                Log.e("", "-----------cunzai ");  
            }  
            String permission = "666";  
  
            try {  
                String command = "chmod " + permission + " " + apkPath + "/"  
                        + apkName;  
                Runtime runtime = Runtime.getRuntime();  
                runtime.exec(command);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
  
        } catch (Exception e) {  
            Log.e("", e.toString());  
        }  
        Log.e("", "fl--" + file.getName() + "-dd---" + file.getAbsolutePath()  
                + "-pa-" + file.getPath());  
        intentInstall.setAction(android.content.Intent.ACTION_VIEW);  
        intentInstall.setDataAndType(Uri.fromFile(file),  
                "application/vnd.android.package-archive");  
        intentInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
  
        ctx.startActivity(intentInstall);  
    }  
    
    public static boolean isAvilible(Context context, String packageName){
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
       //从pinfo中将包名字逐一取出，压入pName list中
            if(pinfo != null){
            for(int i = 0; i < pinfo.size(); i++){
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
  } 
}
