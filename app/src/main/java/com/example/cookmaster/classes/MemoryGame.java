package com.example.cookmaster.classes;

import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame {
    private List<ButtonObject> buttonObjects;
    private int score;

    List<ButtonObject> selectedButtons = new ArrayList<>();

    public MemoryGame(List<ImageButton> buttons,List<Integer> imagePaths) {
        List<Integer> imagePathsCopy = new ArrayList<>(imagePaths);
        imagePathsCopy.addAll(imagePaths);
        Collections.shuffle(imagePathsCopy);
        this.buttonObjects = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            buttonObjects.add(new ButtonObject(buttons.get(i), imagePathsCopy.get(i)));
        }

    }



    public List<ButtonObject> getButtonObjectList() {
        return buttonObjects;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addSelectedButton(ButtonObject buttonObject) {
        if (selectedButtons.size() < 2) {
            if (!selectedButtons.contains(buttonObject)) {
                selectedButtons.add(buttonObject);
            }
        }else {
            if (!selectedButtons.contains(buttonObject)) {
                selectedButtons.remove(0);
                selectedButtons.add(buttonObject);
            }
        }
    }

    public List<ButtonObject> getSelectedButtons() {
        return selectedButtons;
    }

    public void clearSelectedButtons() {
        selectedButtons.clear();
    }

    public boolean checkSelectedButtons() {
        if (selectedButtons.size() == 2) {
            if (selectedButtons.get(0).getImagePath() == selectedButtons.get(1).getImagePath()) {
                return true;
            }
        }
        return false;
    }

    public void addScore() {
        this.score += 1;
    }
}

