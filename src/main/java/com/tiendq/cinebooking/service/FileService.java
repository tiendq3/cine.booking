package com.tiendq.cinebooking.service;

import com.tiendq.cinebooking.model.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileService {
    Page<File> getAllFilePaging(int pageNumber, int pageSize, String[] properties, Sort.Direction sort);

    File getFileById(Long id);

    @Transactional
    void deleteFile(Long id);

//    @Transactional
    List<File> uploadFile(MultipartFile[] files);
}
