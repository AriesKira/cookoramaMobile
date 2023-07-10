package com.example.cookmaster.classes;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.widget.Toast;

import com.example.cookmaster.activity.MemoryGameActivity;

import retrofit2.http.Tag;

public class NfcReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
                    context.startActivity(intent);
    }
}
