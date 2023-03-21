package com.example.tp4;

public class Note {
    String titre, description,date;
    int imgId;
    public Note(String titre, String description, int imgId,String date) {
        this.titre = titre;
        this.description = description;
        this.imgId=imgId;
        this.date=date;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getDescription() {
            int maxLength = Math.min(description.length(), 30);
            return description.substring(0, maxLength) + "...";
    }
    public void setDescription(String description) {
            this.description = description;
    }
    public int getImgId() {
            return imgId;
    }
    public void setImgId(int imgId) {
            this.imgId = imgId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String titre) {
        this.date = date;
    }

}
