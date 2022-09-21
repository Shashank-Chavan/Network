package com.example.networkandperistence;

public class Movie {
    private String title;
    private String description;
    private String ImageLink;
    Movie(String title,String description,String ImageLink){
        this.ImageLink = ImageLink;
        this.title = title;
        this.description = description;
    }
    Movie(){}
    String getTitle(){return  title;}
    String getDescription(){return description;}
    String getImageLink(){return  ImageLink;}
}
