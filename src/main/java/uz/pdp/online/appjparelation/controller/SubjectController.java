package uz.pdp.online.appjparelation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.appjparelation.entity.Subject;
import uz.pdp.online.appjparelation.repository.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    //Create
    @RequestMapping(method = RequestMethod.POST)
    public String add(@RequestBody Subject subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exists.";
        subjectRepository.save(subject);
        return "Subject added.";
    }

    //Read
    @GetMapping
    public List<Subject> getSubjects(){
        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    //Update
}
