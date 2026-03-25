package com.globits.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.demo.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
