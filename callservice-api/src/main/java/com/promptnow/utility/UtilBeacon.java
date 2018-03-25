package com.promptnow.utility;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.promptnow.constant.ActivityRequestCode;

public class UtilBeacon {

	public static BluetoothAdapter getBluetoothAdapter()
	{
		return BluetoothAdapter.getDefaultAdapter();
	}
	
	public static boolean isBluetoothSupport()
	{
		BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
		
		return bluetoothAdapter != null;
	}
	
	public static void startBluetoothEnabling(Activity activity)
	{
		BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
		
		if (!bluetoothAdapter.isEnabled()) {
		    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    activity.startActivityForResult(enableBluetoothIntent, ActivityRequestCode.REQUEST_CODE_ENABLE_BLUETOOTH);
		}
	}
}
