package uz.pdp.online.appjparelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.appjparelation.entity.University;
@Repository
public interface UniversityRepository extends JpaRepository<University,Integer> {
}
