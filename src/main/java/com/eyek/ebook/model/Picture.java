package com.eyek.ebook.model;

import com.eyek.ebook.util.PictureSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 63)
    private String name;

    @Column(length = 15, nullable = false)
    private String size;

    @NotBlank
    private String path;

    private String thumbPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getSize() {
        return size;
    }

    public PictureSize getSizeObject() throws Exception{
        return PictureSize.fromString(size);
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSizeObject(PictureSize size) {
        this.size = size.toString();
    }


}
