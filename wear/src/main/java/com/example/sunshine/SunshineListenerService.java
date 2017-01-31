package com.example.sunshine;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by michal.hornak on 1/30/2017.
 */

public class SunshineListenerService extends WearableListenerService {

    private static final String WEARABLE_DATA_PATH = "/sunshine_data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        DataMap dataMap;

        Log.d("Network"," ---- > Something went by...");

        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                if (event.getDataItem().getUri().getPath().equals(WEARABLE_DATA_PATH)) {

                    dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    // Broadcast message to wearable activity for display
                    Intent messageIntent = new Intent();
                    messageIntent.setAction("SUNSHINE_ACTION");
                    messageIntent.putExtras(dataMap.toBundle());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
                }
            }
        }
    }
}
