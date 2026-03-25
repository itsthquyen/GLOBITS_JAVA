package com.globits.demo.service;

import com.globits.demo.model.Person;
import com.globits.demo.model.Company;
import com.globits.demo.repository.PersonRepository;
import com.globits.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Person createPerson(Person person) {
        if (person.getCompany() != null && person.getCompany().getId() != null) {
            Company existingCompany = companyRepository.findById(person.getCompany().getId()).orElse(null);
            person.setCompany(existingCompany);
        }
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Page<Person> getPersonsPage(int page, int size) {
        return personRepository.findAll(PageRequest.of(page, size));
    }

    public List<Person> getPersonsByCompanyId(Long companyId) {
        return personRepository.findByCompanyId(companyId);
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person updatePerson(Long id, Person updated) {
        if (personRepository.existsById(id)) {
            updated.setId(id);
            if (updated.getCompany() != null && updated.getCompany().getId() != null) {
                Company existingCompany = companyRepository.findById(updated.getCompany().getId()).orElse(null);
                updated.setCompany(existingCompany);
            }
            return personRepository.save(updated);
        }
        return null;
    }

    public Person uploadAvatar(Long id, MultipartFile file) throws IOException {
        Optional<Person> personOpt = personRepository.findById(id);
        if (personOpt.isPresent()) {
            Person person = personOpt.get();
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = id + "_" + originalFileName;
            String uploadDir = "uploads/avatars/";
            
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            try (java.io.InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            
  
            person.setAvatar("/uploads/avatars/" + fileName);
            return personRepository.save(person);
        }
        return null;
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
