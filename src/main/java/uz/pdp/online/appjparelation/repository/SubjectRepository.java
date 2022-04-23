package uz.pdp.online.appjparelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.appjparelation.entity.Subject;
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    boolean existsByName(String name);
}
