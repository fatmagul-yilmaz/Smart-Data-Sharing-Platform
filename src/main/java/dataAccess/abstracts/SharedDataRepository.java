package dataAccess.abstracts;

import entities.concretes.SharedData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedDataRepository extends JpaRepository<SharedData, Long> {
}