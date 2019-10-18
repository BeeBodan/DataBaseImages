package com.bogdan.model;

import javax.persistence.*;

@Entity
@Table(name = "statyva")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long id;
    @Column(name = "image_name")
    private String name;
    @Column(name = "image_base")
    private String base;

    public Image(String name, String base) {
        this.name = name;
        this.base = base;
    }

    public Image(String name) {
        this.name = name;
    }

    public Image() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBase() {
        return base;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", base='" + base + '\'' +
                '}';
    }
}
