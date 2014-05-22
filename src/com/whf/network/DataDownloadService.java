package com.whf.network;

import java.util.Timer;
import java.util.TimerTask;

import com.whf.sqlite.DatabaseHandler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DataDownloadService extends Service {
	
    private static long UPDATE_INTERVAL = 6*1000;  //default
    private static Timer timer = new Timer(); 
    public static long latestTime=1398927600;
  //  private DatabaseHandler db=new DatabaseHandler(getApplicationContext());

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		_startService();
	}

	private void _shutdownService()
    {
        if (timer != null) timer.cancel();
    }

    @Override 
    public void onDestroy() 
    {
        super.onDestroy();
        _shutdownService();
    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(!netWorkChecken()) Toast.makeText(getBaseContext(), "No network connection...", Toast.LENGTH_LONG).show();
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void _startService()
    {      
        timer.scheduleAtFixedRate(    

                new TimerTask() {

                    public void run() {
                    	if(netWorkChecken()){
                    		doServiceWork();
                    	}
                    }
                }, 100,UPDATE_INTERVAL);
    }

	private boolean netWorkChecken(){
		    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo != null && networkInfo.isConnected()) {
	            return true;
	        } else {
	        	return false;
	        }
	}

    private void doServiceWork()
    { 
        //do something wotever you want 
        //like reading file or getting data from network 
        try {
        	 System.out.println("do Service Work....");
        	//new DataDownload().download(db);
        	//Transaction record=db.getTransaction(1);
        	//Toast.makeText(getApplicationContext(), record.getDataCenter(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {

        }

    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
