package uz.pdp.online.appjparelation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.appjparelation.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAllByGroup_Faculty_University_Id(Integer group_faculty_university_id, Pageable pageable);
    Page<Student> findAllByGroup_FacultyId(Integer group_faculty_id, Pageable pageable);
    Page<Student> findAllByGroupId(Integer group_id, Pageable pageable);
}
