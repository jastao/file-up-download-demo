package com.jt.file.ud.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Jason Tao on 7/15/2020
 */
@RestController
@RequestMapping("/api/v1/files")
@CrossOrigin(value = {"*"}, exposedHeaders = HttpHeaders.CONTENT_DISPOSITION)
public class FileResource {

    private static final Logger logger = LoggerFactory.getLogger(FileResource.class);

    private FileRepository fileRepository;

    private FileMapper fileMapper;

    public FileResource(final FileRepository fileRepository, final FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /*
       Get - /api/v1/files/download
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) throws FileNotFoundException {

        logger.info("GET request - download file: " + filename);
        if(filename == null) {
            throw new IllegalArgumentException("Missing filename");
        }

        // Get the file from repository
        File targetFile = fileRepository.findByFilename(filename)
                                        .stream().findFirst().get();

        if(targetFile == null) {
            throw new FileNotFoundException("File do not exist: " + filename);
        }

        // Construct the http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(targetFile.getContentType()));
        headers.setContentLength(targetFile.getDataInBytes().length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + targetFile.getFilename());

        return new ResponseEntity<> (targetFile.getDataInBytes(), headers, HttpStatus.OK);
    }

    /*
      GET - /api/v1/files
    */
    @GetMapping
    public ResponseEntity<List<FileDTO>> listUploadFiles() {

        logger.info("GET request - all file name listing");
        List<FileDTO> listOfFilenames = fileRepository.findAll().stream()
                                                        .map(fileMapper::convertFileToFileDTO)
                                                        .collect(Collectors.toList());

        return ResponseEntity.ok(listOfFilenames != null ? listOfFilenames : null);
    }

    /*
       POST - /api/v1/files/upload
    */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadFiles(@Valid @RequestParam("file") MultipartFile uploadFile) throws IOException, DuplicateFileException {

        if(Objects.isNull(uploadFile)) {
            throw new IllegalArgumentException("Upload file cannot be null.");
        }

        if(!fileRepository.findByFilename(uploadFile.getOriginalFilename()).isEmpty()) {
            throw new DuplicateFileException("A file with the same filename already existed.");
        }

        logger.info("POST request - upload file with file name =" + uploadFile.getOriginalFilename());

        fileRepository.save(new File(uploadFile.getOriginalFilename(), uploadFile.getContentType(),
                                     uploadFile.getSize(), uploadFile.getBytes()));

        URI newLocation = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(newLocation).build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorDetails(ex.getMessage()));
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleFileNotFoundException(FileNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateFileException.class)
    public ResponseEntity<ErrorDetails> handleFileNotFoundException(DuplicateFileException ex) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
