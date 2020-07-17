package com.jt.file.ud.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jason Tao on 7/15/2020
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByFilename(String filename);
}
