package com.example.cookmaster;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cookmaster.Utils.ImageHandler;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.interfaces.UserCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProfileFragment extends Fragment {

    TextView profileFirstname, profileLastname,profileBday,profileEmail,profileSignUpDate,profileFidelityPoints;
    ImageView profilePicture;
    ImageButton editProfileButton,disconnectButton;
    LottieAnimationView loadingAnimation;

    static final String IMAGE_URL = "https://cookorama.fr/ressources/images/profilePicture/";

    ApiRequest apiRequest = new ApiRequest();

    SharedPreferences settings;

    private String firstname, lastname, bday, email, signUpDate, fidelityPoints, profilePictureUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        settings = getActivity().getSharedPreferences("PREFS", MODE_PRIVATE);

        profileFirstname = view.findViewById(R.id.profileFirstname);
        profileLastname = view.findViewById(R.id.profileLastname);
        profileBday = view.findViewById(R.id.profileBirthday);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileSignUpDate = view.findViewById(R.id.profileSignUpDate);
        profileFidelityPoints = view.findViewById(R.id.profileFidelityPoints);
        profilePicture = view.findViewById(R.id.UserPPHolder);
        loadingAnimation = view.findViewById(R.id.animationView);

        editProfileButton = view.findViewById(R.id.editProfileButton);
        disconnectButton = view.findViewById(R.id.disconnectButton);

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.disconnectTitle))
                        .setMessage(getResources().getString(R.string.disconnectMessage))
                        .setPositiveButton(getResources().getString(R.string.disconnect), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor edit = settings.edit();
                                edit.putInt("id", -1);
                                edit.putString("token", "-1");
                                edit.apply();

                                Log.d("diconnect", "clicked");

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancelButton), null)
                        .show();
            }
        });

        loadingAnimation.setVisibility(View.VISIBLE);

        apiRequest.getUserInfos(settings.getInt("id", -1), "Bearer " + settings.getString("token", "-1"), new UserCallback() {
            @Override
            public void onConnectionSuccess(JsonObject userInfos) {
                loadingAnimation.setVisibility(View.GONE);
                Log.d("API Response", "User Infos: " + userInfos.toString());
                Log.d("API CALL", "SUCCESS");

                SharedPreferences.Editor edit = settings.edit();

                JsonObject userObject = userInfos.get("user").getAsJsonObject();

                edit.putString("token", userObject.get("token").getAsString());
                edit.apply();

                profileFirstname.setText(userObject.get("firstname").getAsString());
                profileLastname.setText(userObject.get("lastname").getAsString());
                profileBday.setText(userObject.get("birthdate").getAsString());
                profileEmail.setText(userObject.get("email").getAsString());
                profileSignUpDate.setText(userObject.get("creation").getAsString());
                profileFidelityPoints.setText(userObject.get("fidelityCounter").getAsString());
                ImageHandler.setPrifilePicture(profilePicture,IMAGE_URL + userObject.get("profilePicture").getAsString());

            }

            @Override
            public void onConnectionFailure(String errorMessage) {
                Log.d("API CALL", "FAILURE");
                Log.d("onConnectionFailure: ", errorMessage);

                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.connexionErrorTitle))
                        .setMessage(getResources().getString(R.string.connexionErrorMessage))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });



        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause: ", "onPause");
        this.firstname = profileFirstname.getText().toString();
        this.lastname = profileLastname.getText().toString();
        this.bday = profileBday.getText().toString();
        this.email = profileEmail.getText().toString();
        this.signUpDate = profileSignUpDate.getText().toString();
        this.fidelityPoints = profileFidelityPoints.getText().toString();
        this.profilePictureUrl = profilePicture.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume: ", "onResume");
        profileFirstname.setText(this.firstname);
        profileLastname.setText(this.lastname);
        profileBday.setText(this.bday);
        profileEmail.setText(this.email);
        profileSignUpDate.setText(this.signUpDate);
        profileFidelityPoints.setText(this.fidelityPoints);
        ImageHandler.setPrifilePicture(profilePicture, IMAGE_URL + this.profilePictureUrl);
    }
}