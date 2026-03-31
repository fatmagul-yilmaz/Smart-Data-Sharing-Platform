package dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.concretes.SharedData;

public interface SharedDataRepository extends JpaRepository<SharedData, Long> {
}