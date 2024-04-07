package com.shashank.platform.classroomappui.ui.Books.Fragments.Books_Details;

public class Book_Details_Model {
    String name;
    String publication;
    String pages, price;
    String author;
    String id;




    public Book_Details_Model( String id,String name, String publication, String author, String pages, String price) {
        this.name = name;
        this.publication = publication;
        this.pages = pages;
        this.price = price;
        this.author = author;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
