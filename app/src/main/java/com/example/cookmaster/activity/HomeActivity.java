package com.example.cookmaster;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cookmaster.activity.MemoryGameActivity;
import com.example.cookmaster.classes.NfcReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    private Fragment currentFragment;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Intent intent = new Intent(this, getClass());
        intent.setAction("NFC");

        mPendingIntent = PendingIntent.getActivity(this, 0, intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);


        bottomNav = findViewById(R.id.bottom_navbar);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.home_activity_container, new com.example.cookmaster.HomeFragment()).commit();

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment display = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    display = new HomeFragment();
                    break;
                case R.id.nav_shop:
                    display = new ShopFragment();
                    break;
                case R.id.nav_profile:
                    display = new ProfileFragment();
                    break;
            }

            if (display != null) {
                currentFragment = display;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_activity_container, display);

                if (currentFragment instanceof ProfileFragment) {
                    transaction.addToBackStack(null);
                }

                transaction.commit();
            }

            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NfcAdapter.getDefaultAdapter(this) == null || !NfcAdapter.getDefaultAdapter(this).isEnabled()) {
            Toast.makeText(this, getResources().getString(R.string.nfc_not_available), Toast.LENGTH_SHORT).show();
            return;
        }
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (NfcAdapter.getDefaultAdapter(this) == null || !NfcAdapter.getDefaultAdapter(this).isEnabled()) {
            Toast.makeText(this, getResources().getString(R.string.nfc_not_available), Toast.LENGTH_SHORT).show();
            return;
        }
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (action.equals("NFC")) {
            startActivity(new Intent(this, MemoryGameActivity.class));
        }
    }
}

