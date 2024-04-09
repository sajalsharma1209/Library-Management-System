package com.shashank.platform.classroomappui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectivityReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check network connectivity when receiving the broadcast
        if (isConnectedToInternet(context)) {
            // Device is connected to the internet
            Toast.makeText(context, "Connected to the internet", Toast.LENGTH_SHORT).show();
        } else {
            // Device is not connected to the internet
            Toast.makeText(context, "Not connected to the internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
