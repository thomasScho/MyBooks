package fr.schollaert.mybooks;

import java.io.Serializable;

/**
 * Created by Thomas on 04/01/2017.
 */

public class Param implements Serializable {
    private String name;
    private String description;

    public Param(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Param{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
