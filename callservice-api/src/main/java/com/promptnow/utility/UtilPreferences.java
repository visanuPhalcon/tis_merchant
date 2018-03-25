package com.promptnow.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UtilPreferences extends BaseUtility {

	public static String PREF_DEFAULT_STRING_VALUE = "";
	public static int PREF_DEFAULT_INTEGER_VALUE = -1;
	public static boolean PREF_DEFAULT_BOOLEAN_VALUE = false;
	public static float PREF_DEFAULT_FLOAT_VALUE = -1.0f;
	
	public static String PREFERENSE_NAME = "PN_Pref";
	
	public static final String GCM_REG_ID = "registration_id";
    public static final String GCM_APP_VERSION = "appVersion";
	
	private static SharedPreferences getPreferense()
	{
		SharedPreferences pref = null;
		pref = getContext().getSharedPreferences(PREFERENSE_NAME, Context.MODE_PRIVATE);
		
		return pref;
	}
	
	interface iSavePref
	{
		public void doSavePref(SharedPreferences.Editor editor);
	}
	
	private static boolean savePref(String prefName, iSavePref savePrefAction)
	{
		boolean result = false;
		
		SharedPreferences pref = getPreferense();
		
		if(pref != null)
		{
			SharedPreferences.Editor editor = pref.edit();
			savePrefAction.doSavePref(editor);
			editor.commit();
			result = true;
		}
		
		return result;
	}

	public static boolean savePref(final String prefName, final String prefValue)
	{
		iSavePref savePref = new iSavePref() {
			
			@Override
			public void doSavePref(Editor editor) {
				// TODO Auto-generated method stub
				editor.putString(prefName, prefValue);
			}
		};
		
		return savePref(prefName, savePref);
	}

	public static boolean savePref(final String prefName, final int prefValue)
	{
		iSavePref savePref = new iSavePref() {
			
			@Override
			public void doSavePref(Editor editor) {
				// TODO Auto-generated method stub
				editor.putInt(prefName, prefValue);
			}
		};
		
		return savePref(prefName, savePref);
	}

	public static boolean savePref(final String prefName, final float prefValue)
	{
		iSavePref savePref = new iSavePref() {
			
			@Override
			public void doSavePref(Editor editor) {
				// TODO Auto-generated method stub
				editor.putFloat(prefName, prefValue);
			}
		};
		
		return savePref(prefName, savePref);
	}

	public static boolean savePref(final String prefName, final boolean prefValue)
	{
		iSavePref savePref = new iSavePref() {
			
			@Override
			public void doSavePref(Editor editor) {
				// TODO Auto-generated method stub
				editor.putBoolean(prefName, prefValue);
			}
		};
		
		return savePref(prefName, savePref);
	}
	
	public static boolean clearPref(String prefName)
	{
		boolean result = false;

		SharedPreferences pref = getPreferense();
		
		if(pref != null)
		{
			SharedPreferences.Editor editor = pref.edit();
			editor.remove(prefName);
			editor.commit();
			result = true;
		}
		return result;
	}
	
	interface PrefGetValue
	{
		public void prefGetValue(SharedPreferences pref);
	}
	
	private static void getPref(String prefName, PrefGetValue prefGetValue)
	{
		SharedPreferences pref = getPreferense();
		
		if(pref.contains(prefName))
		{
			prefGetValue.prefGetValue(pref);
		}
	}
	
	public static String getStringPref(String prefName)
	{
		return getStringPref(prefName, PREF_DEFAULT_STRING_VALUE);
	}
	
	public static String getStringPref(final String prefName, final String defaultValue)
	{
		final String[] result = {defaultValue};

		getPref(prefName, new PrefGetValue(){

			@Override
			public void prefGetValue(SharedPreferences pref) {
				// TODO Auto-generated method stub
				result[0] = pref.getString(prefName, defaultValue);
			}
			
		});
		return result[0];
	}

	public static int getIntegerPref(String prefName)
	{
		return getIntegerPref(prefName, PREF_DEFAULT_INTEGER_VALUE);
	}

	public static int getIntegerPref(final String prefName, final int defaultValue)
	{
		final int[] result = {defaultValue};
		
		getPref(prefName, new PrefGetValue() {
			
			@Override
			public void prefGetValue(SharedPreferences pref) {
				// TODO Auto-generated method stub
				result[0] = pref.getInt(prefName, defaultValue);
			}
		});
		
		return result[0];
	}
	
	public static boolean getBooleanPref(String prefName)
	{
		return getBooleanPref(prefName, PREF_DEFAULT_BOOLEAN_VALUE);
	}
	
	public static boolean getBooleanPref(final String prefName, final boolean defaultValue)
	{
		final boolean[] result = {defaultValue};
		
		getPref(prefName, new PrefGetValue(){

			@Override
			public void prefGetValue(SharedPreferences pref) {
				// TODO Auto-generated method stub
				result[0] = pref.getBoolean(prefName, defaultValue);
			}
		});
		
		return result[0];
	}

	public static float getFloatPref(String prefName)
	{
		return getFloatPref(prefName, PREF_DEFAULT_FLOAT_VALUE);
	}

	public static float getFloatPref(final String prefName, final float defaultValue)
	{
		final float[] result = {defaultValue};
		
		getPref(prefName, new PrefGetValue() {
			
			@Override
			public void prefGetValue(SharedPreferences pref) {
				// TODO Auto-generated method stub
				result[0] = pref.getFloat(prefName, defaultValue);
			}
		});

		return result[0];
	}
	
	public static boolean isContainPref(String prefName)
	{
		return getPreferense().contains(prefName);
	}
}
