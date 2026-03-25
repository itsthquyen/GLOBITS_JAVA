package com.globits.demo.controller;
import com.globits.demo.model.Company;
import com.globits.demo.model.Department;
import com.globits.demo.model.Person;
import com.globits.demo.service.CompanyService;
import com.globits.demo.service.DepartmentService;
import com.globits.demo.service.PersonService;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PersonService personService;

    @PostMapping
    public Company create(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @GetMapping
    public List<Company> getAll() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getById(@PathVariable("id") Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.orElse(null);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable("id") Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "Deleted successfully company id: " + id;
    }


    @GetMapping("/{id}/departments")
    public List<Department> getDepartmentsByCompany(@PathVariable("id") Long id) {
        return departmentService.getDepartmentsByCompanyId(id);
    }


    @GetMapping("/{id}/persons")
    public List<Person> getPersonsByCompany(@PathVariable("id") Long id) {
        return personService.getPersonsByCompanyId(id);
    }

    @GetMapping("/page")
    public Page<Company> getPage(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return companyService.getCompaniesPage(page, size);
    }
}
