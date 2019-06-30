package com.eyek.ebook.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document("picture")
public class DBPicture {

    @Id
    private String id;

    // id in RDBMS
    private int rid;

    @Field("filename")
    private String filename;

    @Field("contentType")
    private String contentType;

    @Field("size")
    private long size;

    @Field("content")
    private byte[] content;

    public DBPicture() {
    }

    public DBPicture(String filename, int rid, String contentType, long size, byte[] content) {
        this.filename = filename;
        this.rid = rid;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, filename, contentType, size);
    }

    @Override
    public String toString() {
        return String.format("File{%s, type=%s, size=%d}", filename, contentType, size);
    }

}
