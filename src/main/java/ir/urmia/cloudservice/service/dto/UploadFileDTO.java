package ir.urmia.cloudservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UploadFileDTO {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private LocalDateTime createDate;

    public UploadFileDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
