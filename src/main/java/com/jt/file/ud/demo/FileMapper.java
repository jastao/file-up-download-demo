package com.jt.file.ud.demo;

import org.springframework.stereotype.Component;

/**
 * Created by Jason Tao on 7/16/2020
 */
@Component
public class FileMapper {

    FileDTO convertFileToFileDTO(File file) {
        final FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(file.getFilename());
        fileDTO.setContentType(file.getContentType());
        fileDTO.setUploadDate(file.getUploadDate());
        fileDTO.setFileSize(file.getFileSize());
        fileDTO.setDataInBytes(file.getDataInBytes());
        return fileDTO;
    }
}
