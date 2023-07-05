package com.example.cookmaster.classes;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.widget.Toast;

import retrofit2.http.Tag;

public class NfcReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            new AlertDialog.Builder(context)
                    .setTitle("NFC")
                    .setMessage("NFC Tag Detected")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
