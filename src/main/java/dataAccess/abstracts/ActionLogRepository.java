package dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.concretes.ActionLog;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}