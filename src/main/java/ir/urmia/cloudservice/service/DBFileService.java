package ir.urmia.cloudservice.service;

import ir.urmia.cloudservice.base.service.BaseService;
import ir.urmia.cloudservice.domain.DBFile;
import org.springframework.web.multipart.MultipartFile;

public interface DBFileService extends BaseService<DBFile, Long> {

    DBFile storeFile(MultipartFile file, String username);

    DBFile getFile(Long fileId);
}