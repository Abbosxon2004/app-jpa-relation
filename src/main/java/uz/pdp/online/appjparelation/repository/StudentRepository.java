package uz.pdp.online.appjparelation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.appjparelation.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAllByGroup_Faculty_UniversityId(Integer univesityId,Pageable pageable);
}
