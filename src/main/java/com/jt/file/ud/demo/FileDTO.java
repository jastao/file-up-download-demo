package com.jt.file.ud.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Jason Tao on 7/16/2020
 */
public class FileDTO {

    private String filename;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("upload_date")
    private Date uploadDate;

    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("data")
    private byte[] dataInBytes;

    public FileDTO() {}

    public FileDTO(String filename, String contentType, Date uploadDate, Long fileSize, byte[] dataInBytes) {
        this.filename = filename;
        this.contentType = contentType;
        this.uploadDate = uploadDate;
        this.fileSize = fileSize;
        this.dataInBytes = dataInBytes;
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
