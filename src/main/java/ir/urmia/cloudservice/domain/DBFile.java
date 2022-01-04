package ir.urmia.cloudservice.domain;

import ir.urmia.cloudservice.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = DBFile.TABLE_NAME)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBFile extends BaseEntity<Long> {
    final static String TABLE_NAME = "files";
    private final static String FILE_NAME = "file_name";
    private final static String FILE_TYPE = "file_type";
    private final static String DATA = "data";


    @Column(name = FILE_NAME)
    private String fileName;

    @Column(name = FILE_TYPE)
    private String fileType;

    @Column(name = DATA)
    @Lob
    private byte[] data;

    @ManyToOne
    private User user;

    public long getSize() {
        return data.length;
    }
}
