package cub.aco.svc.librarymanage.repository;

import cub.aco.svc.librarymanage.entity.BorrowLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowLogRepository extends JpaRepository<BorrowLog, Integer> {
}
