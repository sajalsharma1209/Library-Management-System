package com.shashank.platform.classroomappui;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ConnectivityServices extends Service {

    private static final String TAG = "ConnectivityService";
    private BroadcastReceiver connectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create BroadcastReceiver
        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkConnectivity(context);
            }
        };

        // Register BroadcastReceiver to listen for network changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Check connectivity once when the service starts
        checkConnectivity(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister BroadcastReceiver when the service is destroyed
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkConnectivity(Context context) {
        // Check for network connectivity
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

        if (isConnected) {
            Log.d(TAG, "Internet connected");
           // Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show();
            // You can perform actions when internet is connected
        } else {
            Log.d(TAG, "No internet connection");
            Toast.makeText(context, "Internet disconnected! Please connect your internet connection", Toast.LENGTH_SHORT).show();
            // You can perform actions when there is no internet connection
            // Broadcast to notify the activity of internet disconnection
            Intent intent = new Intent("INTERNET_CONNECTION_STATUS");
            intent.putExtra("is_connected", false);
            sendBroadcast(intent);
        }
    }
}
