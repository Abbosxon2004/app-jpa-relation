package uz.pdp.online.appjparelation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.appjparelation.entity.Address;
import uz.pdp.online.appjparelation.entity.University;
import uz.pdp.online.appjparelation.payload.UniversityDto;
import uz.pdp.online.appjparelation.repository.AddressRepository;
import uz.pdp.online.appjparelation.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    AddressRepository addressRepository;

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversities(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            return university;
        }
        return new University();
    }

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address savedAddress = addressRepository.save(address);// id si bilan qaytaradi

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University added";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id) {
        Integer idAddress = universityRepository.findById(id).get().getAddress().getId();
        universityRepository.deleteById(id);
        addressRepository.deleteById(idAddress);
        return "University and it`s address successfully deleted!";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            university.setName(universityDto.getName());
            university.getAddress().setCity(universityDto.getCity());
            university.getAddress().setDistrict(universityDto.getDistrict());
            university.getAddress().setStreet(universityDto.getStreet());
            universityRepository.save(university);
            return "University information successfully edited.";
        }
        return "University not found.";
    }


}
