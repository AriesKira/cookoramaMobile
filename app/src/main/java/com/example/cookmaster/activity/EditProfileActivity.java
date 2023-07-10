package com.example.cookmaster.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookmaster.R;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.interfaces.OnValidatePasswordCallback;
import com.example.cookmaster.interfaces.UpdateUserCallback;
import com.example.cookmaster.interfaces.UserCallback;
import com.google.gson.JsonObject;

public class EditProfileActivity extends AppCompatActivity {

    EditText editFirstname, editLastname, editBday, editEmail, editPassword, editPasswordConfirm ,currentPassword;
    Button saveButton,cancelButton;

    SharedPreferences settings;
    ApiRequest apiRequest = new ApiRequest();

    String changedFirstname, changedLastname, changedBday, changedEmail, changedPassword;
    Boolean pwdValidated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        settings = getSharedPreferences("PREFS", MODE_PRIVATE);

        this.cancelButton = findViewById(R.id.cancelButton);
        this.saveButton = findViewById(R.id.saveButton);
        this.editFirstname = findViewById(R.id.editFirstname);
        this.editLastname = findViewById(R.id.editLastname);
        this.editBday = findViewById(R.id.editBday);
        this.editEmail = findViewById(R.id.editEmail);
        this.editPassword = findViewById(R.id.newPasswordConfirm);
        this.editPasswordConfirm = findViewById(R.id.editPasswordConfirm);
        this.currentPassword = findViewById(R.id.currentPassword);

        this.cancelButton.setOnClickListener(view -> {
            finish();
        });

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!currentPassword.getText().toString().isEmpty()) {
                    apiRequest.checkPassword("Bearer " + settings.getString("token", ""), currentPassword.getText().toString(), new OnValidatePasswordCallback() {
                        @Override
                        public void onValidatePasswordSuccess(JsonObject token) {
                            pwdValidated = true;
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", token.get("token").getAsString());
                            editor.apply();

                            if (!editFirstname.getText().toString().isEmpty() && !editFirstname.getText().toString().equals(settings.getString("firstname", ""))) {
                                    changedFirstname = editFirstname.getText().toString();
                            } else {
                                    changedFirstname = settings.getString("firstname", "-1");
                            }
                            if (!editLastname.getText().toString().isEmpty() && !editLastname.getText().toString().equals(settings.getString("lastname", ""))) {
                                    changedLastname = editLastname.getText().toString();
                            } else {
                                    changedLastname = settings.getString("lastname", "-1");
                            }
                            if (!editBday.getText().toString().isEmpty() && !editBday.getText().toString().equals(settings.getString("bday", ""))) {
                                    changedBday = editBday.getText().toString();
                            } else {
                                    changedBday = settings.getString("birthdate", "-1");
                            }
                            if (!editEmail.getText().toString().isEmpty() && !editEmail.getText().toString().equals(settings.getString("email", ""))) {
                                    changedEmail = editEmail.getText().toString();
                            } else {
                                    changedEmail = settings.getString("email", "-1");
                            }

                            apiRequest.updateUser("Bearer " + settings.getString("token", ""), changedFirstname, changedLastname, changedBday, changedEmail, new UpdateUserCallback() {
                                @Override
                                public void onUpdateUserSuccess(JsonObject user) {
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("firstname", user.get("firstname").getAsString());
                                    editor.putString("lastname", user.get("lastname").getAsString());
                                    editor.putString("birthdate", user.get("birthdate").getAsString());
                                    editor.putString("email", user.get("email").getAsString());
                                    editor.apply();

                                    Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.infos_updated_successfully), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onUpdateUserFailure(String errorMessage) {
                                    Toast.makeText(EditProfileActivity.this, "Error while updating your information's, please try again later", Toast.LENGTH_LONG).show();
                                    Log.d("API ERROR", errorMessage);
                                }
                            });

                            if (pwdValidated) {
                                Log.d("in pwdValidate", "true ");
                                if (!editPassword.getText().toString().equals(editPasswordConfirm.getText().toString())) {
                                    editPassword.setError(getResources().getString(R.string.passwords_not_matching));
                                    editPasswordConfirm.setError(getResources().getString(R.string.passwords_not_matching));
                                }
                                if (!editPassword.getText().toString().isEmpty() && !editPassword.getText().toString().equals(currentPassword.getText().toString()) && editPassword.getText().toString().equals(editPasswordConfirm.getText().toString())) {
                                    if (editPassword.getText().toString().length() < 8 ||
                                            !editPassword.getText().toString().matches("(.*[A-Z].*){2,}") ||
                                            !editPassword.getText().toString().matches("(.*[a-z].*){2,}") ||
                                            !editPassword.getText().toString().matches("(.*\\d.*){2,}") ||
                                            !editPassword.getText().toString().matches("(.*[^a-zA-Z0-9].*){2,}")) {
                                        editPassword.setError(getResources().getString(R.string.password_not_secure));
                                        return;
                                    }


                                    apiRequest.updateUserPassword("Bearer " + settings.getString("token",""),editPassword.getText().toString(), new UpdateUserCallback() {
                                        @Override
                                        public void onUpdateUserSuccess(JsonObject rsp) {
                                            Log.d("API Call", "Success");
                                            SharedPreferences.Editor editor = settings.edit();
                                            editor.putString("token", rsp.get("token").getAsString());
                                            editor.apply();
                                            finish();
                                        }

                                        @Override
                                        public void onUpdateUserFailure(String errorMessage) {
                                            editPassword.setError(getResources().getString(R.string.password_not_secure));
                                            Log.d("API ERROR", errorMessage);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onValidatePasswordFailure(JsonObject errorMessage) {
                            Toast.makeText(EditProfileActivity.this, getResources().getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = settings.edit();
                            if (errorMessage.get("token") != null) {
                                editor.putString("token", errorMessage.get("token").getAsString());
                                editor.apply();
                            }else {
                                editor.putString("token", "-1");
                                editor.apply();
                                AlertDialog alertDialog = new AlertDialog.Builder(EditProfileActivity.this).create();
                                alertDialog.setTitle(getResources().getString(R.string.fatal_error));
                                alertDialog.setMessage(getResources().getString(R.string.fatal_error_occured));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();
                            }

                        }

                    });

                }else {
                    currentPassword.setError(getResources().getString(R.string.password_required));
                }



            }});
    }
}