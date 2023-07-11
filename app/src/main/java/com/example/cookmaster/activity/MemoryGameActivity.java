package com.example.cookmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookmaster.R;
import com.example.cookmaster.classes.ApiRequest;
import com.example.cookmaster.classes.ButtonObject;
import com.example.cookmaster.classes.MemoryGame;
import com.example.cookmaster.interfaces.UpdateFidelityCallback;
import com.example.cookmaster.interfaces.UpdateUserCallback;
import com.example.cookmaster.interfaces.UserCallback;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameActivity extends AppCompatActivity {

    private static final int HIDDEN_FACE = R.drawable.memory_hidden;
    private TextView timer, score, startTimer;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,btn11,btn12, backBtn, submitBtn;


    private CountDownTimer countDownTimer;
    private final long INITIAL_COUNTDOWN_DURATION = 3000;
    private final long COUNTDOWN_INTERVAL = 1000;


    MemoryGame game;

    List<ImageButton> buttons;
    List<Integer> imagePaths;

    List<ButtonObject> buttonObjects;

    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        startTimer = findViewById(R.id.startTimer);
        timer = findViewById(R.id.timer);
        score = findViewById(R.id.score);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        backBtn = findViewById(R.id.backBtn);
        submitBtn = findViewById(R.id.submitBtn);

        buttons = new ArrayList<>();
        imagePaths = new ArrayList<>();

        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);
        buttons.add(btn10);
        buttons.add(btn11);
        buttons.add(btn12);

        imagePaths.add(R.drawable.memory_burger);
        imagePaths.add(R.drawable.memory_drink);
        imagePaths.add(R.drawable.memory_eggs);
        imagePaths.add(R.drawable.memory_fries);
        imagePaths.add(R.drawable.memory_ice_cream);
        imagePaths.add(R.drawable.memory_tomato);

        game = new MemoryGame(buttons, imagePaths);
        buttonObjects = game.getButtonObjectList();

        flipAllCards(buttonObjects, false);

        new AlertDialog.Builder(MemoryGameActivity.this)
                .setTitle(getResources().getString(R.string.welcome_memory))
                .setMessage(getResources().getString(R.string.rules_memory))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer = new CountDownTimer(INITIAL_COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long secondsRemaining = millisUntilFinished / 1000;
                                startTimer.setText(Long.toString(secondsRemaining));
                            }

                            @Override
                            public void onFinish() {

                                flipAllCards(buttonObjects, true);
                                startTimer.setText("Go!");
                                startGame();
                            }
                        };
                        countDownTimer.start();

                    }
                })
                .show();


        backBtn.setOnClickListener(v -> {
            finish();
        });

        submitBtn.setOnClickListener(v -> {
            if (game.checkSelectedButtons()) {
                flipCard(game.getSelectedButtons().get(0), false);
                flipCard(game.getSelectedButtons().get(1), false);
                game.addScore();
                score.setText(Integer.toString(game.getScore()));
                buttonObjects.remove(game.getSelectedButtons().get(0));
                buttonObjects.remove(game.getSelectedButtons().get(1));
                game.clearSelectedButtons();
            }else {
                Toast.makeText(MemoryGameActivity.this, getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
                flipCard(game.getSelectedButtons().get(0), true);
                flipCard(game.getSelectedButtons().get(1), true);
                game.clearSelectedButtons();
            }

            if (buttonObjects.isEmpty()) {
                endGame();
            }

        });


    }

    private void endGame() {
        timer.setText(getResources().getString(R.string.done));
        new AlertDialog.Builder(MemoryGameActivity.this)
                .setTitle(getResources().getString(R.string.finished_game_title))
                .setMessage(getResources().getString(R.string.finished_game, game.getScore()))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);

                        apiRequest = new ApiRequest();
                        apiRequest.postScore(sharedPreferences.getString("token", "-1"), game.getScore(), new UpdateFidelityCallback() {

                            @Override
                            public void onUpdateFidelitySuccess(JsonObject userInfos) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("token", userInfos.get("token").getAsInt());
                                editor.apply();
                            }

                            @Override
                            public void onUpdateFidelityFailure(JsonObject errorMessage) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("token", errorMessage.get("token").getAsInt());
                                editor.apply();
                                Log.d("ERROR", "Fidelity update failed");
                            }
                        });
                        finish();
                    }
                })
                .show();
    }


    public void flipAllCards(List<ButtonObject> buttonObjects,boolean isHidden) {
        if (isHidden) {
            for (ButtonObject buttonObject : buttonObjects) {
                buttonObject.getImageButton().setImageResource(HIDDEN_FACE);
            }
        } else {
            for (ButtonObject buttonObject : buttonObjects) {
                buttonObject.getImageButton().setImageResource(buttonObject.getImagePath());
            }
        }
    }

    public void flipCard(ButtonObject buttonObject, boolean isHidden) {
        if (isHidden) {
            buttonObject.getImageButton().setImageResource(HIDDEN_FACE);
        } else {
            buttonObject.getImageButton().setImageResource(buttonObject.getImagePath());
        }
    }

    public void startGame() {
        startGameTimer();
        for (ButtonObject buttonObject : buttonObjects) {
            buttonObject.getImageButton().setOnClickListener(v -> {
                game.addSelectedButton(buttonObject);
            });
        }


    }

    public void startGameTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timer.setText(Long.toString(secondsRemaining));
            }

            @Override
            public void onFinish() {
                timer.setText(getResources().getString(R.string.done));
                new AlertDialog.Builder(MemoryGameActivity.this)
                        .setTitle(getResources().getString(R.string.finished_game_title))
                        .setMessage(getResources().getString(R.string.times_up, game.getScore()))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();

            }
        };
        countDownTimer.start();
    }
}