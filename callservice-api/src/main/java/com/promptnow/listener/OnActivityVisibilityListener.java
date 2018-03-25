package com.promptnow.listener;

import android.app.Activity;

public interface OnActivityVisibilityListener {
	public void onBecameVisible(Activity activity);
    public void onBecameInvisible(Activity activity);
}
