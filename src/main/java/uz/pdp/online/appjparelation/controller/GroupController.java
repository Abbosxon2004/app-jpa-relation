package uz.pdp.online.appjparelation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.appjparelation.entity.Faculty;
import uz.pdp.online.appjparelation.entity.Group;
import uz.pdp.online.appjparelation.payload.GroupDto;
import uz.pdp.online.appjparelation.repository.FacultyRepository;
import uz.pdp.online.appjparelation.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    //vazirlik uchun
    //READ
    @GetMapping
    public List<Group> getGroups() {
        List<Group> groupList = groupRepository.findAll();
        return groupList;
    }


    //Universitet masul xodimi uchun
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId) {
        List<Group> groupList = groupRepository.findAllByFaculty_University_Id(universityId);
        List<Group> groupList1 = groupRepository.getGroupsByUniversityId(universityId);
        List<Group> groupList2 = groupRepository.getGroupsByUniversityIdNative(universityId);

        return groupList;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!optionalFaculty.isPresent()){
            return "Such faculty not found.";
        }
        group.setFaculty(optionalFaculty.get());

        groupRepository.save(group);
        return "Group added";
    }
}
