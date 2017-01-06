package fr.schollaert.mybooks.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 02/01/2017.
 */

public class Book implements Serializable {
    private String title;
    private String author;
    private String imageUrl;
    private String googleID;
    private String subTitle;
    private String isbn10;
    private String isbn13;
    private SimpleDateFormat publishedDate;
    private List<Float> rates;
    private List<Comment> comments;
    private Float yourRate;


    public Book(Book b) {
        this.title = b.getTitle();
        this.author = b.getAuthor();
        this.imageUrl = b.getImageUrl();
        this.googleID = b.getGoogleID();
        this.subTitle = b.getSubTitle();
        this.isbn10 = b.getIsbn10();
        this.isbn13 = b.getIsbn13();
        this.publishedDate = b.getPublishedDate();
        this.rates = b.getRates();
        this.comments = b.getComments();
        this.yourRate = b.getYourRate();
    }

    public Book() {
    }

    public Book(String title, String author, String imageUrl, String googleID) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.googleID = googleID;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public SimpleDateFormat getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(SimpleDateFormat publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageLink) {
        this.imageUrl = imageLink;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Float> getRates() {
        return rates;
    }

    public void setRates(List<Float> rates) {
        this.rates = rates;
    }

    public Float getYourRate() {
        return yourRate;
    }

    public void setYourRate(Float yourRate) {
        this.yourRate = yourRate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", googleID='" + googleID + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", publishedDate=" + publishedDate +
                ", rates=" + rates +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return googleID.equals(book.googleID);

    }

    @Override
    public int hashCode() {
        return googleID.hashCode();
    }
}

