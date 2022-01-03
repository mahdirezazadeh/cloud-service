package ir.urmia.cloudservice.service.impl;

import ir.urmia.cloudservice.base.service.impl.BaseServiceImpl;
import ir.urmia.cloudservice.domain.DBFile;
import ir.urmia.cloudservice.exception.FileStorageException;
import ir.urmia.cloudservice.exception.MyFileNotFoundException;
import ir.urmia.cloudservice.repository.DBFileRepository;
import ir.urmia.cloudservice.service.DBFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class DBFileServiceImpl extends BaseServiceImpl<DBFile, Long, DBFileRepository> implements DBFileService {
    public DBFileServiceImpl(DBFileRepository repository) {
        super(repository);
    }

    @Override
    public DBFile storeFile(MultipartFile file, String username) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

//            TODO set user to dbFile by username

            return repository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public DBFile getFile(Long fileId) {
        return repository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
