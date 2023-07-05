package com.example.cookmaster;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookmaster.classes.NfcReceiver;

public class HomeFragment extends Fragment {

    NfcReceiver nfcReceiver = new NfcReceiver();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        nfcReceiver.onReceive(getContext(), getActivity().getIntent());

        return view;
    }
}