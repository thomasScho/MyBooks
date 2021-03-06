package fr.schollaert.mybooks.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Thomas on 06/01/2017.
 */

public class Comment implements Serializable {
    private String idUtil;
    private String comment;
    private DateFormat date;

    public Comment(String idUtilisateur, String msg) {
        this.idUtil = idUtilisateur;
        this.comment = msg;
    }

    public String getIdUtil() {
        return idUtil;
    }

    public void setIdUtil(String idUtil) {
        this.idUtil = idUtil;
    }

    public DateFormat getDate() {
        return date;
    }

    public void setDate(DateFormat date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "idUtil='" + idUtil + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (!idUtil.equals(comment1.idUtil)) return false;
        if (!comment.equals(comment1.comment)) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = idUtil.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }

    public Comment(String idUtil, String comment, DateFormat date) {
        this.idUtil = idUtil;
        this.comment = comment;
        this.date = date;
    }

    public Comment() {
    }


}
