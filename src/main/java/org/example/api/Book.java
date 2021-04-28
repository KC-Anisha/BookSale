package org.example.api;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

public class Book {

    private int id;
    private String book_description;
    private int seller_id;
    @NotEmpty private String title;
    @NotNull private int edition;
    @NotNull private double price;
    @NotEmpty private String book_subject;
    @NotEmpty private String author;

    @JsonCreator
    @ConstructorProperties({"id", "seller_id", "title", "edition", "price", "book_subject", "book_description", "author"})
    public Book (int i, int si, String t , int e, double p, String bs, String bd, String a) {
        id = i;
        seller_id = si;
        title = t;
        edition = e;
        price = p;
        book_subject = bs;
        book_description = bd;
        author = a;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public int getSeller_id() {
        return seller_id;
    }

    public String getTitle() {
        return title;
    }

    public int getEdition() {
        return edition;
    }

    public double getPrice() {
        return price;
    }

    public String getBook_subject() {
        return book_subject;
    }

    public String getBook_description() {
        return book_description;
    }

    public String getAuthor() {
        return author;
    }
}
