package com.tis.merchant.app.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import com.tis.merchant.app.R;
import com.tis.merchant.app.login.LoginActivity;
import com.tis.merchant.app.openapp.SplashScreenActivity;
import com.tis.merchant.app.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseActivity extends AppCompatActivity {

    private boolean isInForeground = false;
    private boolean isTimeout = false;
    private Handler timeoutHandler = new Handler();
    private int TIME_LIMIT = 30 * 60 * 1000; // 5 minutes
    private final String TAG = BaseActivity.class.getSimpleName();

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, fragment, tag)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment, tag)
                .commit();
    }

    public void replaceFragmentAndClearBackStack(@Nullable String backStackName, Fragment fragment, String tag) {

        getSupportFragmentManager().popBackStackImmediate(backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment, tag)
                .commit();
    }

    public void replaceFragmentWithBackStack(Fragment fragment, String tag, @Nullable String backStackName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment, tag)
                .addToBackStack(backStackName)
                .commit();
    }

    public void clearBackStack(@Nullable String backStackName) {
        getSupportFragmentManager().popBackStackImmediate(backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.contentContainer)).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("NewApi")
    public void doTimeout(){
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInForeground = true;
        if (isTimeout){
            sessionTimeout();
            return;
        }
        timeoutHandler.removeCallbacks(timeoutRunnable);
        timeoutHandler.postDelayed(timeoutRunnable, TIME_LIMIT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                timeoutHandler.removeCallbacks(timeoutRunnable);
                timeoutHandler.postDelayed(timeoutRunnable, TIME_LIMIT);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private Runnable timeoutRunnable = new Runnable() {
        @Override
        public void run() {
            timeoutHandler.removeCallbacks(timeoutRunnable);
            if (isInForeground) {
                sessionTimeout();
            } else {
                isTimeout = true;
            }
        }
    };

    public void sessionTimeout(){
        Utils.showMessageDialogOneButton(this,
                getString(R.string.label_temed_out),
                getString(R.string.label_timed_out_message),
                null,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                        finishAffinity();
                    }
                });
    }
}
