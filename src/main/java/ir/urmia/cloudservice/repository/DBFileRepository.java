package ir.urmia.cloudservice.repository;

import ir.urmia.cloudservice.domain.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBFileRepository extends JpaRepository<DBFile, Long> {
}
