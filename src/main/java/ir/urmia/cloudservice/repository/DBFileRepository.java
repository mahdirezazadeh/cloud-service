package ir.urmia.cloudservice.repository;

import ir.urmia.cloudservice.domain.DBFile;
import ir.urmia.cloudservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DBFileRepository extends JpaRepository<DBFile, Long> {
    List<DBFile> findAllByUser(User user);

    @Query("SELECT sum(d.size) from DBFile d where d.user = ?1")
    long sumOfSizeByUser(User user);
}
