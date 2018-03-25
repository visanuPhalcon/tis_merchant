package com.promptnow.bean;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.promptnow.utility.UtilLog;

/**
 * Create this Class from tutorial : 
 * http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial
 * 
 * For Geocoder read this : http://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation
 * 
 */

public class GPSTracker implements LocationListener
{
	private final Activity mActivity;

    //flag for GPS Status
    boolean isGPSEnabled = false;

    //flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;

    //The minimum distance to change updates in metters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 metters

    //The minimum time beetwen updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    //Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Activity activity) 
    {
        this.mActivity = activity;
        updateLocation();
    }

    public void updateLocation()
    {
    	this.mActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
		        {
		            locationManager = (LocationManager) mActivity.getSystemService(Service.LOCATION_SERVICE);

		            //getting GPS status
		            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		            //getting network status
		            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		            if (!isGPSEnabled && !isNetworkEnabled)
		            {
		                // no network provider is enabled
		            }
		            else
		            {
		                canGetLocation = true;

		                //First get location from Network Provider
		                if (isNetworkEnabled)
		                {
		                    locationManager.requestLocationUpdates(
		                            LocationManager.NETWORK_PROVIDER,
		                            MIN_TIME_BW_UPDATES,
		                            MIN_DISTANCE_CHANGE_FOR_UPDATES, GPSTracker.this);

		                    UtilLog.d("Call locationManager to retrieve latitude,longitude from NET");

		                    updateGPSCoordinates(LocationManager.NETWORK_PROVIDER);
		                }

		                //if GPS Enabled get lat/long using GPS Services
		                if (isGPSEnabled && location == null)
		                {
		                	locationManager.requestLocationUpdates(
		                            LocationManager.GPS_PROVIDER,
		                            MIN_TIME_BW_UPDATES,
		                            MIN_DISTANCE_CHANGE_FOR_UPDATES, GPSTracker.this);

		                    UtilLog.d("Call locationManager to retrieve latitude,longgiture from GPS");

		                    updateGPSCoordinates(LocationManager.GPS_PROVIDER);
		                }
		            }
		        }
		        catch (Exception e)
		        {
		            UtilLog.e(UtilLog.getStackTraceString(e));
		        }
			}
		});
        
    }

    private void updateGPSCoordinates(String provider)
    {
    	if (locationManager != null)
    	{
    		location = locationManager.getLastKnownLocation(provider);
    	}
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    public void stopUsingGPS()
    {
        if (locationManager != null)
        {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public Location getLocation()
    {
    	Location result = null;
    	
    	if(location != null)
    	{
    		result = location;
    	}
    	
    	return result;
    }
    
    /**
     * Function to get latitude
     */
    public double getLatitude()
    {
    	double latitude = 0.0f;

        if (location != null)
        {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude()
    {
    	double longitude = 0.0f;

        if (location != null)
        {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     */
    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }

    /**
     * Get list of address by latitude and longitude
     * @return null or List<Address>
     */
    public List<Address> getGeocoderAddress(Context context)
    {
        if (location != null)
        {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            try 
            {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                return addresses;
            } 
            catch (IOException e) 
            {
                //e.printStackTrace();
                UtilLog.e("Impossible to connect to Geocoder");
            }
        }

        return null;
    }

    /**
     * Try to get AddressLine
     * @return null or addressLine
     */
    public String getAddressLine(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get Locality
     * @return null or locality
     */
    public String getLocality(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     * @return null or postalCode
     */
    public String getPostalCode(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        }
        else
        {
            return null;
        }
    }

    /**
     * Try to get CountryName
     * @return null or postalCode
     */
    public String getCountryName(Context context)
    {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0)
        {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) 
    {   
    }

    @Override
    public void onProviderDisabled(String provider) 
    {   
    }

    @Override
    public void onProviderEnabled(String provider) 
    {   
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) 
    {   
    }

}
