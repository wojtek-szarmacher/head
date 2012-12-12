package org.mifos.framework.fileupload.service;

import java.io.InputStream;
import java.util.List;

import org.mifos.dto.screen.UploadedFileDto;

public interface ClientFileService {

    boolean create(Integer clientId, InputStream in, String contentType, String name, String description);

    UploadedFileDto read(Long fileId);
    
    List<UploadedFileDto> readAll(Integer clientId);

    boolean update(Integer clientId, InputStream in, String contentType, String name, String description);

    boolean delete(Integer clientId, Long fileId);

    byte[] getData(UploadedFileDto clientFile);

    boolean checkIfFileExists(Integer clientId, String fileName);
}
