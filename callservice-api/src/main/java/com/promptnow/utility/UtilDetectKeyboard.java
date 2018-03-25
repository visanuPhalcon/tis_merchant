package com.promptnow.utility;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class UtilDetectKeyboard {
    private static Boolean isShow = false;
	public static Boolean keyboardShow(final View activityRootView){
		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
				if (heightDiff > 100) {
					//keyboard show 
		            isShow = true;
		        }else {
		        	//Keyboard hidden 
		        	isShow = false;
				}
			}
		});
		if (isShow) {
			return isShow;
		}else {
			return isShow;
		}
	}
}
