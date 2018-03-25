package com.promptnow.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import android.content.Intent;
import android.net.Uri;


public class UtilFile extends BaseUtility {

	public enum UtilFileResult
	{
		UFR_SUCCESS,
		UFR_UNKNOWN_ERROR,
		UFR_NETWORK_ERROR,
		UFR_IO_ERROR,
		UFR_FILE_NOT_FOUND
	}
	
	public static UtilFileResult writeFile(FileOutputStream fos, byte[] data, boolean closeConnectionAfterWrite) throws IOException
	{
		UtilFileResult result = UtilFileResult.UFR_UNKNOWN_ERROR;
		
	    fos.write(data);
	    
	    if(closeConnectionAfterWrite)
	    {
	    	fos.close();
	    }
		    
	    result = UtilFileResult.UFR_SUCCESS;
		return result;
	}
	
	public static UtilFileResult writeFile(String filePath, byte[] data)
	{
		UtilFileResult result = UtilFileResult.UFR_UNKNOWN_ERROR;
		
		try {
		    FileOutputStream fos = new FileOutputStream(filePath, true);
		    writeFile(fos, data, false);
		    result = UtilFileResult.UFR_SUCCESS;
		} catch (IOException e) {
		    UtilLog.e(UtilLog.getStackTraceString(e));
		    result = UtilFileResult.UFR_IO_ERROR;
		}
		
		return result;
	}
	
	public static void updateGallery(String filePath)
	{
		File file = new File(filePath);
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		getContext().sendBroadcast(mediaScanIntent);
	}
	
	public static UtilFileResult writeMediaFile(String filePath, byte[] data)
	{
		UtilFileResult result = writeFile(filePath, data);
		updateGallery(filePath);
		return result;
	}
	
	public static UtilFileResult writeFileFromURL(String filePath, URL url)
	{
		UtilFileResult result = UtilFileResult.UFR_UNKNOWN_ERROR;
		
		boolean isOpenConnectionSuccess = false;
		URLConnection urlConnection = null;
		HttpURLConnection httpConn = null;
		HttpsURLConnection httpsConn = null;
		
		// this will be used in reading the data from the internet
		InputStream inputStream = null;
		
		try
		{
			urlConnection = url.openConnection();

			if(urlConnection instanceof HttpURLConnection)
			{
				httpConn = (HttpURLConnection)urlConnection;
				// set up some things on the connection
				httpConn.setRequestMethod("GET");
			}
			else if(urlConnection instanceof HttpsURLConnection)
			{
				httpsConn = (HttpsURLConnection)urlConnection;
				// set up some things on the connection
				httpsConn.setRequestMethod("GET");
			}

			// and connect!
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			inputStream = urlConnection.getInputStream();
			isOpenConnectionSuccess = true;
		}
		catch(IOException e)
		{
			result = UtilFileResult.UFR_NETWORK_ERROR;
		}
		
		if(isOpenConnectionSuccess)
		{
			// this will be used to write the downloaded data into the file we created
			FileOutputStream fileOutput = null;
			
			try
			{
				fileOutput = new FileOutputStream(filePath);
				// this is the total size of the file
				int totalSize = urlConnection.getContentLength();
				// variable to store total downloaded bytes
				int downloadedSize = 0;
							
				// create a buffer...
				byte[] buffer = new byte[2048];
				int bufferLength = 0; // used to store a temporary size of the buffer

				// now, read through the input buffer and write the contents to the file
				while ((bufferLength = inputStream.read(buffer)) > 0)//inputStream.read(buffer, 0, buffer.length)) > 0)//
				{
					// add the data in the buffer to the file in the file output stream (the file on the sd card
					fileOutput.write(buffer, 0, bufferLength);
					// add up the size so we know how much is downloaded
					downloadedSize += bufferLength;
					// this is where you would do something to report the prgress, like this maybe
					// updateProgress(downloadedSize, totalSize);
					UtilLog.v("Download progress " + downloadedSize + "/" + totalSize);
				}
				// close the output stream when done
				fileOutput.close();
				result = UtilFileResult.UFR_SUCCESS;
			}
			catch (IOException e)
			{
				UtilLog.e(UtilLog.getStackTraceString(e));
			    result = UtilFileResult.UFR_IO_ERROR;
			}
		}
		
		return result;
	}
	
	public static UtilFileResult writeMediaFileFromURL(String filePath, URL url)
	{
		UtilFileResult result = writeFileFromURL(filePath, url);
		updateGallery(filePath);
		return result;
	}
	
	public static UtilFileResult deleteFile(String filePath)
	{
		UtilFileResult result = UtilFileResult.UFR_FILE_NOT_FOUND;
		File file = new File(filePath);
		
		if(file.delete())
		{
			result = UtilFileResult.UFR_SUCCESS;
		}
		
		return result;
	}
}
