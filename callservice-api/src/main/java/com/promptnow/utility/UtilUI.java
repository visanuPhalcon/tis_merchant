package com.promptnow.utility;

import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class UtilUI {

	public static ArrayList<View> getViewChildren(View v)
	{
	    if (!(v instanceof ViewGroup)) {
	        ArrayList<View> viewArrayList = new ArrayList<View>();
	        viewArrayList.add(v);
	        return viewArrayList;
	    }

	    ArrayList<View> result = new ArrayList<View>();

	    ViewGroup viewGroup = (ViewGroup) v;
	    for (int i = 0; i < viewGroup.getChildCount(); i++) {

	        View child = viewGroup.getChildAt(i);

	        ArrayList<View> viewArrayList = new ArrayList<View>();
	        viewArrayList.add(v);
	        viewArrayList.addAll(getViewChildren(child));

	        result.addAll(viewArrayList);
	    }
		return result;
	}
	
	public static void setViewLocale(View v, String lang)
	{
		Resources res = v.getResources();
		Locale myLocale = new Locale(lang);
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
	}
}
