package com.globits.demo.controller;

import com.globits.demo.model.Person;
import com.globits.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping
    public List<Person> getAll() {
        return personService.getAllPersons();
    }

    @GetMapping("/page")
    public Page<Person> getPage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return personService.getPersonsPage(page, size);
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable("id") Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.orElse(null);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable("id") Long id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        personService.deletePerson(id);
        return "Đã xóa thành công person có id: " + id;
    }

    @PostMapping("/{id}/avatar")
    public Person uploadAvatar(@PathVariable("id") Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            return personService.uploadAvatar(id, file);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Lỗi hệ thống: Không thể lưu file", e);
        }
    }
}
