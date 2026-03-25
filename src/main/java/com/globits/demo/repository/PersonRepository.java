package com.globits.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.globits.demo.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompanyId(Long companyId);
}
