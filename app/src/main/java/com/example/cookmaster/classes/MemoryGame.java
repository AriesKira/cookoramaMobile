package com.example.cookmaster.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame {
    private List<ImageObject> imageObjects;

    public MemoryGame() {
        // Créez les objets d'image et formez des paires
        imageObjects = new ArrayList<>();

        // Ajoutez vos images ici
        imageObjects.add(new ImageObject("burger", "path_to_image1"));
        imageObjects.add(new ImageObject("tomato", "path_to_image2"));
        imageObjects.add(new ImageObject("eggs", "path_to_image2"));
        imageObjects.add(new ImageObject("iceCream", "path_to_image2"));
        imageObjects.add(new ImageObject("drink", "path_to_image2"));
        // ...

        // Dupliquez les objets d'image pour former des paires
        List<ImageObject> pairedImageObjects = new ArrayList<>(imageObjects);
        imageObjects.addAll(pairedImageObjects);

        // Mélangez l'ordre des objets d'image
        Collections.shuffle(imageObjects);
    }

    public void startGame() {
        // Logique du jeu de mémoire
        // Par exemple, afficher les images et gérer les sélections des joueurs
    }
}

class ImageObject {
    private String id;
    private String imagePath;

    public ImageObject(String id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    // Ajoutez des getters et des setters si nécessaire
}
