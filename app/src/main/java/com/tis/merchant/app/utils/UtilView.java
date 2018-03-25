package com.tis.merchant.app.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Created by Nanthakorn on 1/18/2017.
 */

public class UtilView {
    public static final int ANIMATION_TRANSITION_TIME = 1000;
    public static void showElements(View view){
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.animate()
            .setDuration(ANIMATION_TRANSITION_TIME)
            .alpha(1.0f)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            })
        ;
    }
    public static void hideElements(final View view){
        view.animate()
            .setDuration(ANIMATION_TRANSITION_TIME)
            .alpha(0.0f)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                    view.animate().setListener(null);
                }
            })
        ;
    }
}
