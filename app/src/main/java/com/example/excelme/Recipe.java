/**This class defines a recipe that the user can find in the food section
 * File: Recipe.java
 * Authors: Serdiuk Andrii, Fedorenko Polina
 * */


package com.example.excelme;


import com.google.firebase.storage.StorageReference;

public class Recipe {
    private final String rName;
    private final StorageReference reference;
    private String downloadUrl;
    private final String rInstructions;
    private final String rIngredients;
    private final String rNutrition;
    private String rType;

    public Recipe(String rName, StorageReference reference , String ingredients, String instructions, String nutrition){
        this.rName = rName;
        this.reference = reference;
        this.rIngredients = ingredients;
        this.rInstructions = instructions;
        this.rNutrition = nutrition;
    }

    public void setDownloadUrl(String url) {this.downloadUrl = url;}
    public String getDownloadUrl() {return downloadUrl;}
    public String getName(){ return rName;}
    public StorageReference getImageUrl(){return reference;}
    public String getInstructions(){return rInstructions;}
    public String getIngredients(){return rIngredients;}
    public String getNutrition(){return rNutrition;}
    public String getType(){return rType;}


    @Override
    public String toString() {
        return "Recipe{" +
                "rName='" + rName + '\'' /*+
                ", rInstructions='" + rInstructions + '\'' +
                ", rIngredients='" + rIngredients + '\'' +
                ", rNutrition='" + rNutrition + '\'' +
                ", rResultImage=" + rResultImage +
                ", rType='" + rType + '\'' +
                '}'*/;
    }
}
