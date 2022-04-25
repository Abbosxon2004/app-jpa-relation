package uz.pdp.online.appjparelation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.appjparelation.entity.Address;
import uz.pdp.online.appjparelation.entity.Group;
import uz.pdp.online.appjparelation.entity.Student;
import uz.pdp.online.appjparelation.entity.Subject;
import uz.pdp.online.appjparelation.payload.StudentDto;
import uz.pdp.online.appjparelation.repository.AddressRepository;
import uz.pdp.online.appjparelation.repository.GroupRepository;
import uz.pdp.online.appjparelation.repository.StudentRepository;
import uz.pdp.online.appjparelation.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AddressRepository addressRepository;

    //Vazirlik
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //Universitetlar
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_University_Id(universityId, pageable);
        return studentPage;
    }

    //Faculty Dekanat
    @GetMapping("/forFaculty/{FacultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                  @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_FacultyId(facultyId, pageable);
        return studentPage;
    }

    //Group owner
    @GetMapping("/forGroup/{GroupId}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer groupId,
                                                @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroupId(groupId, pageable);
        return studentPage;
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) {
            return "Group id not found";
        }
        Student student = new Student();
        final Group group = optionalGroup.get();
        student.setGroup(group);

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address();
        address.setStreet(studentDto.getStreet());
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        addressRepository.save(address);

        List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectIds());

        student.setAddress(address);
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return "Student added";
    }

    @PutMapping("/{studentId}")
    public String editStudent(@RequestBody StudentDto studentDto, @PathVariable Integer studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (!optionalStudent.isPresent()) {
            return "Student id not found ";
        }
        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) {
            return "Group id not found";
        }
        Student student = optionalStudent.get();
        final Group group = optionalGroup.get();
        student.setGroup(group);

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address();
        address.setStreet(studentDto.getStreet());
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        addressRepository.save(address);

        List<Subject> subjectList = subjectRepository.findAllById(studentDto.getSubjectIds());

        student.setAddress(address);
        student.setSubjects(subjectList);
        studentRepository.save(student);
        return "Student edited";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer studentId) {
        studentRepository.deleteById(studentId);
        return "Student deleted";
    }
}
