package com.example.cookmaster;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private EditText emailInput, pwdInput;
    private BottomNavigationView bottomNav;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        bottomNav = findViewById(R.id.bottom_navbar);

        getSupportActionBar().hide();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.home_activity_container, new HomeFragment()).commit();

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment display = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    display = new HomeFragment();
                    break;
                case R.id.nav_search:
                    display = new SearchFragment();
                    break;
                case R.id.nav_shop:
                    display = new ShopFragment();
                    break;
                case R.id.nav_profile:
                    display = new ProfileFragment();
                    break;
                case R.id.nav_recipe:
                    display = new RecipeFragment();
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

}

