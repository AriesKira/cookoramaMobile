package com.example.cookmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.classes.ImageObject;
import com.example.cookmaster.classes.MemoryGame;

import java.util.List;

public class MemoryGameActivity extends AppCompatActivity {

    private static final int HIDDEN_FACE = R.drawable.memory_hidden;
    private TextView timer, score, startTimer;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,btn11,btn12, backBtn, submitBtn;

    private int btn1Img, btn2Img, btn3Img, btn4Img, btn5Img, btn6Img, btn7Img, btn8Img, btn9Img, btn10Img, btn11Img, btn12Img;

    private CountDownTimer countDownTimer;
    private final long INITIAL_COUNTDOWN_DURATION = 3000;
    private final long COUNTDOWN_INTERVAL = 1000;

    MemoryGame game = new MemoryGame();

    List<ImageObject> imageObjects = game.getImageObjects();

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
        btn4= findViewById(R.id.btn4);
        btn5= findViewById(R.id.btn5);
        btn6= findViewById(R.id.btn6);
        btn7= findViewById(R.id.btn7);
        btn8= findViewById(R.id.btn8);
        btn9= findViewById(R.id.btn9);
        btn10= findViewById(R.id.btn10);
        btn11= findViewById(R.id.btn11);
        btn12= findViewById(R.id.btn12);
        backBtn = findViewById(R.id.backBtn);
        submitBtn = findViewById(R.id.submitBtn);


        btn1Img = imageObjects.get(0).getImagePath();
        btn2Img = imageObjects.get(1).getImagePath();
        btn3Img = imageObjects.get(2).getImagePath();
        btn4Img = imageObjects.get(3).getImagePath();
        btn5Img = imageObjects.get(4).getImagePath();
        btn6Img = imageObjects.get(5).getImagePath();
        btn7Img = imageObjects.get(6).getImagePath();
        btn8Img = imageObjects.get(7).getImagePath();
        btn9Img = imageObjects.get(8).getImagePath();
        btn10Img = imageObjects.get(9).getImagePath();
        btn11Img = imageObjects.get(10).getImagePath();
        btn12Img = imageObjects.get(11).getImagePath();


        new AlertDialog.Builder(MemoryGameActivity.this)
                .setTitle("Memory Game")
                .setMessage("Welcome to the memory game! You have 3 seconds to memorize the images. After that, you will have to find the pairs. Good luck!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        flipCard(btn1, btn1Img, false);
                        flipCard(btn2, btn2Img, false);
                        flipCard(btn3, btn3Img, false);
                        flipCard(btn4, btn4Img, false);
                        flipCard(btn5, btn5Img, false);
                        flipCard(btn6, btn6Img, false);
                        flipCard(btn7, btn7Img, false);
                        flipCard(btn8, btn8Img, false);
                        flipCard(btn9, btn9Img, false);
                        flipCard(btn10, btn10Img, false);
                        flipCard(btn11, btn11Img, false);
                        flipCard(btn12, btn12Img, false);


                        countDownTimer = new CountDownTimer(INITIAL_COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long secondsRemaining = millisUntilFinished / 1000;
                                startTimer.setText(Long.toString(secondsRemaining));
                            }

                            @Override
                            public void onFinish() {
                                flipCard(btn1, btn1Img, true);
                                flipCard(btn2, btn2Img, true);
                                flipCard(btn3, btn3Img, true);
                                flipCard(btn4, btn4Img, true);
                                flipCard(btn5, btn5Img, true);
                                flipCard(btn6, btn6Img, true);
                                flipCard(btn7, btn7Img, true);
                                flipCard(btn8, btn8Img, true);
                                flipCard(btn9, btn9Img, true);
                                flipCard(btn10, btn10Img, true);
                                flipCard(btn11, btn11Img, true);
                                flipCard(btn12, btn12Img, true);

                                startTimer.setText("Go!");

                                //game.startGame();
                            }
                        };
                        countDownTimer.start();

                    }
                })
                .show();
    }


    private void flipCard(ImageButton btn, int img ,boolean isFlipped) {
        if (isFlipped) {
            btn.setImageResource(HIDDEN_FACE);
        } else {
            btn.setImageResource(img);
        }
    }


}