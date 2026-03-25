package com.globits.demo.service;

import com.globits.demo.model.Department;
import com.globits.demo.repository.DepartmentRepository;
import com.globits.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Department createDepartment(Department department) {
        if (department.getCompany() != null && department.getCompany().getId() != null) {
            companyRepository.findById(department.getCompany().getId())
                             .ifPresent(department::setCompany);
        }
        if (department.getParent() != null && department.getParent().getId() != null) {
            departmentRepository.findById(department.getParent().getId())
                                .ifPresent(department::setParent);
        }
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public List<Department> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findByCompanyId(companyId);
    }

    public Department updateDepartment(Long id, Department updated) {
        Optional<Department> currentOpt = departmentRepository.findById(id);
        if (currentOpt.isPresent()) {
            Department current = currentOpt.get();
            updated.setId(id);
            
           
            if (updated.getCompany() != null && updated.getCompany().getId() != null) {
                companyRepository.findById(updated.getCompany().getId())
                                 .ifPresent(updated::setCompany);
            } else if (updated.getCompany() == null) {
                updated.setCompany(current.getCompany());
            }

   
            if (updated.getParent() != null && updated.getParent().getId() != null) {
                departmentRepository.findById(updated.getParent().getId())
                                    .ifPresent(updated::setParent);
            } else if (updated.getParent() == null) {
                updated.setParent(current.getParent());
            }

            return departmentRepository.save(updated);
        }
        return null; 
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
