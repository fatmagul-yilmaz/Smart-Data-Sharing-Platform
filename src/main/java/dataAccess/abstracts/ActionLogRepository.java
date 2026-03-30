package dataAccess.abstracts;

import entities.concretes.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}