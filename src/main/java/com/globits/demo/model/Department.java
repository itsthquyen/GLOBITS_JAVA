package com.globits.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parent;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Department getParent() { return parent; }
    public void setParent(Department parent) { this.parent = parent; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
}
