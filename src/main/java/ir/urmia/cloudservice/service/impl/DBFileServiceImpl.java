package ir.urmia.cloudservice.service.impl;

import ir.urmia.cloudservice.base.service.impl.BaseServiceImpl;
import ir.urmia.cloudservice.config.RedisConfig;
import ir.urmia.cloudservice.domain.DBFile;
import ir.urmia.cloudservice.domain.User;
import ir.urmia.cloudservice.exception.FileStorageException;
import ir.urmia.cloudservice.exception.MyFileNotFoundException;
import ir.urmia.cloudservice.repository.DBFileRepository;
import ir.urmia.cloudservice.service.DBFileService;
import ir.urmia.cloudservice.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Import({RedisConfig.class})
@EnableCaching
@Service
public class DBFileServiceImpl extends BaseServiceImpl<DBFile, Long, DBFileRepository> implements DBFileService {

    private final UserService userService;

    public DBFileServiceImpl(DBFileRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public DBFile storeFile(MultipartFile file, String username) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            User user = null;
            Optional<User> optionalUser = userService.findByUsername(username);
            if (optionalUser.isPresent())
                user = optionalUser.get();

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), user);

            return repository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    @Cacheable(value = "fileCache")
    public DBFile getFile(Long fileId) {
        return repository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    @Override
    public List<DBFile> getAllFilesByUsername(String username) {

//        get user from database
        User user = null;
        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent())
            user = optionalUser.get();

        return repository.findAllByUser(user);
    }
}
