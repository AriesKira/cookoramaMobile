package com.example.cookmaster;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookmaster.Utils.ImageHandler;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.interfaces.UserCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProfileFragment extends Fragment {

    TextView profileFirstname, profileLastname,profileBday,profileEmail,profileSignUpDate,profileFidelityPoints;
    ImageView profilePicture;

    static final String IMAGE_URL = "https://cookorama.fr/ressources/images/profilePicture/";

    ApiRequest apiRequest = new ApiRequest();

    SharedPreferences settings;

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

        apiRequest.getUserInfos(settings.getInt("id", -1), "Bearer " + settings.getString("token", "-1"), new UserCallback() {
            @Override
            public void onConnectionSuccess(JsonObject userInfos) {
                Log.d("API Response", "User Infos: " + userInfos.toString());
                Log.d("API CALL", "SUCCESS");

                JsonArray infos = userInfos.get("user").getAsJsonArray();
                JsonObject userObject = infos.get(0).getAsJsonObject();

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
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.d("API CALL", "FAILURE");
            }
        });

        return view;
    }
}