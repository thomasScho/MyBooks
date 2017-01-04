package fr.schollaert.mybooks;

import java.io.Serializable;

/**
 * Created by Thomas on 02/01/2017.
 */

public class Book implements Serializable {
    private String title;
    private String author;
    private String imageUrl;


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

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

