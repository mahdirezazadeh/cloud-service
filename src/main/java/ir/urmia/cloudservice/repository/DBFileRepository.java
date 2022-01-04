package ir.urmia.cloudservice.repository;

import ir.urmia.cloudservice.domain.DBFile;
import ir.urmia.cloudservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBFileRepository extends JpaRepository<DBFile, Long> {
    List<DBFile> findAllByUser(User user);
}
