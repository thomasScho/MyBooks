package fr.schollaert.mybooks;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Thomas on 04/01/2017.
 */

public class User implements Serializable {
    private String pseudo;
    private String idUtilisateur;
    private String mail;
    private ArrayList<Book> userLibrary;
    private int age;
    private String sexe;

    public User() {

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public ArrayList<Book> getUserLibrary() {
        return userLibrary;
    }

    public void setUserLibrary(ArrayList<Book> userLibrary) {
        this.userLibrary = userLibrary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public User(String pseudo, String idUtilisateur, ArrayList<Book> userLibrary, int age, String sexe) {
        this.pseudo = pseudo;
        this.idUtilisateur = idUtilisateur;
        this.userLibrary = userLibrary;
        this.age = age;
        this.sexe = sexe;
    }

    public User(String idUtilisateur, String mail) {
        this.mail = mail;
        this.idUtilisateur = idUtilisateur;
    }


    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", idUtilisateur='" + idUtilisateur + '\'' +
                ", mail='" + mail + '\'' +
                ", userLibrary=" + userLibrary +
                ", age=" + age +
                ", sexe='" + sexe + '\'' +
                '}';
    }
}

