package com.example.cookmaster.classes;

import com.example.cookmaster.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame {
    private List<ImageObject> imageObjects;

    public MemoryGame() {
        // Créez les objets d'image et formez des paires
        this.imageObjects = new ArrayList<>();

        // Ajoutez vos images ici
        this.imageObjects.add(new ImageObject("burger", R.drawable.memory_burger));
        this.imageObjects.add(new ImageObject("tomato", R.drawable.memory_tomato));
        this.imageObjects.add(new ImageObject("eggs", R.drawable.memory_eggs));
        this.imageObjects.add(new ImageObject("iceCream", R.drawable.memory_ice_cream));
        this.imageObjects.add(new ImageObject("drink", R.drawable.memory_drink));
        this.imageObjects.add(new ImageObject("fries", R.drawable.memory_fries));


        // Dupliquez les objets d'image pour former des paires
        List<ImageObject> pairedImageObjects = new ArrayList<>(imageObjects);
        this.imageObjects.addAll(pairedImageObjects);

        // Mélangez l'ordre des objets d'image
        Collections.shuffle(this.imageObjects);
    }

    public void startGame() {
        // Logique du jeu de mémoire
        // Par exemple, afficher les images et gérer les sélections des joueurs
    }

    public void endGame() {
        // Logique de fin de jeu
        // Par exemple, afficher un message de fin de jeu
    }

    public List<ImageObject> getImageObjects() {
        return imageObjects;
    }
}

