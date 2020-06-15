package br.com.devdojo.model;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity{
    // TODO: Se tiver erro de compilação, pode ser aqui
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
