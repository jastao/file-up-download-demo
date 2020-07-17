package com.jt.file.ud.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Jason Tao on 7/15/2020
 */
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String filename;

    @NotNull
    @Column(name = "content_type")
    @JsonProperty("content_type")
    private String contentType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("upload_date")
    @Column(name = "upload_date")
    private Date uploadDate;

    @JsonProperty("file_size")
    @Column(name = "file_size")
    private Long fileSize;

    @Lob
    @Column(name = "data")
    @JsonProperty("data")
    private byte[] dataInBytes;

    public File () {}

    public File(String filename, String contentType, Long fileSize, byte[] dataInBytes) {
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.uploadDate = new Date();
        this.dataInBytes = dataInBytes;
    }

    public Long getId() {
        return id;
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getDataInBytes() {
        return dataInBytes;
    }

    public void setDataInBytes(byte[] dataInBytes) {
        this.dataInBytes = dataInBytes;
    }
}
