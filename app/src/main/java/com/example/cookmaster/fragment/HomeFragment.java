package com.example.cookmaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookmaster.activity.MemoryGameActivity;
import com.example.cookmaster.classes.NfcReceiver;

public class HomeFragment extends Fragment {

    NfcReceiver nfcReceiver = new NfcReceiver();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}