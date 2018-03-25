package com.promptnow.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import android.content.res.AssetManager;


public class UtilAsset extends BaseUtility {
	public static String loadTextFile(String name) {
		String result = "";
		BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getContext().getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));
            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            result = buf.toString();
        } catch (IOException e) {
            UtilLog.e(UtilLog.getStackTraceString(e));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    UtilLog.e(UtilLog.getStackTraceString(e));
                }
            }
        }
        
        return result;
    }
	
	public static Properties loadPropertiesFile(String propertiesFilename)
	{
		Properties properties = new Properties();
		AssetManager assetManager = getResources().getAssets();

		// Read from the /assets directory
		try {
		    InputStream inputStream = assetManager.open(propertiesFilename);
		    properties.load(inputStream);
		    UtilLog.d("properties: " + properties + " are now loaded");
		} catch (IOException e) {
		    UtilLog.e(UtilLog.getStackTraceString(e));
		}
		
		return properties;
	}
}
