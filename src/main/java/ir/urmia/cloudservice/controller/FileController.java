package ir.urmia.cloudservice.controller;

import ir.urmia.cloudservice.domain.DBFile;
import ir.urmia.cloudservice.service.DBFileService;
import ir.urmia.cloudservice.service.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final DBFileService fileService;

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DBFile dbFile = fileService.storeFile(file, username);

        return getUploadFileResponse(dbFile);
    }

    private UploadFileDTO getUploadFileResponse(DBFile dbFile) {
        return new UploadFileDTO(dbFile.getFileName(), getFileDownloadUri(dbFile),
                dbFile.getFileType(), dbFile.getSize());
    }

    private String getFileDownloadUri(DBFile dbFile) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();
    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Load file as Resource
        DBFile dbFile = fileService.getFile(fileId, username);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/files")
    public List<UploadFileDTO> getMyFiles() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DBFile> files = fileService.getAllFilesByUsername(username);
        return files.stream().map(this::getUploadFileResponse).collect(Collectors.toList());
    }


    @GetMapping("/allUploudedSizeByUser")
    public long uploadedSizeByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return fileService.getUsedSpaceByUser(username);
    }
}