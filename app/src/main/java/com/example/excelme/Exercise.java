/*This file defines an exercise that is displayed during a training
 * File: Exercise.java
 * Authors: Serdiuk Andrii, Fedorenko Polina
 * */

package com.example.excelme;

import com.google.firebase.storage.StorageReference;

public class Exercise {

    private final String name;
    private final String description;
    private final int quantity;
    private final StorageReference reference;
    private String downloadUrl;

    public Exercise(String name, String description, int quantity, StorageReference reference) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public StorageReference getReference() {
        return reference;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getName() {
        return name;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
