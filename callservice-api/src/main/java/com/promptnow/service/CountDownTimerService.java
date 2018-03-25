package com.promptnow.service;

import java.util.Locale;

import com.promptnow.utility.UtilLog;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public abstract class CountDownTimerService extends Service {

	private static final long DEFAULT_COUNTDOWN_INTERVAL = 1000; // 1 second
	private static final long DEFAULT_COUNTDOWN_TIME = 100000;
	
	CountDownTimer countdown = null;
	long countdown_time = DEFAULT_COUNTDOWN_TIME;
	long countdown_interval = DEFAULT_COUNTDOWN_INTERVAL;
	boolean bInfinityCountDown = false;
	
	public CountDownTimerService()
	{
		
	}
	
	public CountDownTimerService(long millisInFuture, long countDownInterval)
	{
		countdown_time = millisInFuture;
		countdown_interval = countDownInterval;
	}
	
	public CountDownTimerService(long countDownInterval)
	{
		countdown_time = countDownInterval*10;
		countdown_interval = countDownInterval;
		bInfinityCountDown = true;
	}
	
    @Override
    public void onCreate() {
        super.onCreate();
        startCountDown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countdown.cancel();
        countdown = null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

	protected void startCountDown()
	{
		cancelCountDown();
		
		UtilLog.v(String.format("'%s' start count down.", this.toString()));
		countdown = new CountDownTimer(countdown_time, countdown_interval) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				UtilLog.v(String.format(Locale.getDefault(), "'%s' deliver event onTick with time until finished is %d", CountDownTimerService.this.toString(), millisUntilFinished));
				CountDownTimerService.this.onTick();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				UtilLog.v(String.format("'%s' deliver event onFinish", CountDownTimerService.this.toString()));
				
				if(bInfinityCountDown)
				{
					startCountDown();
				}
				else
				{
					CountDownTimerService.this.onFinish();
				}
			}
		};
		countdown.start();
	}
	
	protected void cancelCountDown()
	{
		if(countdown != null)
		{
			countdown.cancel();
			countdown = null;
		}
	}
	
	protected abstract void onTick();
	protected abstract void onFinish();
}
