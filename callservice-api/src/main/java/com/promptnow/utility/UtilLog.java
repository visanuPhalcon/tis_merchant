package com.promptnow.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

enum LogTagType
{
	LogTag_LineOfCode,
	LogTag_PackageVersion,
	LogTag_EncryptLineOfCode
}

@SuppressLint("DefaultLocale")
public final class UtilLog {

	public static final boolean SHOW_LINE_OF_CODE = true;
	public static final LogTagType tag = LogTagType.LogTag_LineOfCode; 
	
	private static String getLogTag()
	{
		String format = "%s %s";
		String result = String.format(format, UtilApplication.getPackageName(), UtilApplication.getAppVersionName());
		
		return result;
	}
	
	private static String getCallerInfo()
	{
		String result = "";
		
		if(SHOW_LINE_OF_CODE)
		{
			StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
	
			for (int i=1; i<stElements.length; i++)
			{
				StackTraceElement ste = stElements[i];
				if (!ste.getClassName().equals(UtilLog.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0)
				{
					result = new StringBuilder(ste.getClassName()).append(":line").append(ste.getLineNumber()).toString();
					break;
				}
			}
		}
		else
		{
			result = getLogTag();
		}

		return result;
	}
	
	public static void v(String tag, String msg)
	{
		Log.v(tag, msg);
	}
	
	public static void v(String msg)
	{
		v(getCallerInfo(), msg);
	}
	
	public static void d(String tag, String msg)
	{
		Log.d(tag, msg);
	}
	
	public static void d(String msg)
	{
		d(getCallerInfo(), msg);
	}
	
	public static void i(String tag, String msg)
	{
		Log.i(tag, msg);
	}
	
	public static void i(String msg)
	{
		i(getCallerInfo(), msg);
	}
	
	public static void w(String tag, String msg)
	{
		Log.w(tag, msg);
	}
	
	public static void w(String msg)
	{
		w(getCallerInfo(), msg);
	}
	
	public static void e(String tag, String msg)
	{
		Log.e(tag, msg);
	}
	
	public static void e(String msg)
	{
		e(getCallerInfo(), msg);
	}
	
	public static void showDebugDlg(String msg)
	{
		Toast toast = Toast.makeText ( null, msg, Toast.LENGTH_LONG );
		toast.show();
	}

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return "PromptNow Stack Trace:" + sw.toString();
    }

    public static void logModel(Object model) {
    	String tag = getCallerInfo();
		try {

			UtilLog.i(tag, "|||||||"+model.getClass().getName()+"|||||||");

			//String superclassForName = model.getClass().getCanonicalName();
			Class<?> superclass = model.getClass().getSuperclass();
			if(superclass.getCanonicalName().indexOf("CommonR") >= 0) {
				Field[] fieldsSuper = superclass.getDeclaredFields();
				for(int index=0; index<fieldsSuper.length; index++){  
					Field field = superclass.getDeclaredField(fieldsSuper[index].getName()); 
					field.setAccessible(true);
					Object value = field.get(model);
					if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) == false) {
						if(value==null){
							Log.i(tag, fieldsSuper[index].getName()+" : "+"null"); 
						}else {
							Log.i(tag, fieldsSuper[index].getName()+" : "+(value==null?"":value.toString())); 
						}
					}
				}
			}

			String classForName = model.getClass().getCanonicalName();
			Class<?> clfn = Class.forName(classForName);
			Field[] fields = model.getClass().getDeclaredFields();
			for(int index=0; index<fields.length; index++){  
				Field field = clfn.getDeclaredField(fields[index].getName()); 
				field.setAccessible(true);
				Object value = field.get(model);
				if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) == false) {
					if(value==null){
						Log.i(tag, fields[index].getName()+" : "+"null"); 
					}else {
						Log.i(tag, fields[index].getName()+" : "+(value==null?"":value.toString())); 
					}
				}
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
