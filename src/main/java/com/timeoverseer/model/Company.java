package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.timeoverseer.util.LocalDateDeserializer;
import com.timeoverseer.util.LocalDateSerializer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Company</code> class represents a business entity that focuses
 * on the development and manufacturing of technology.
 * <p>
 * <code>Company</code> provides business solutions for its
 * customers {@link Customer}.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "company", schema = "overseer")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "founded", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate founded;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "founders", nullable = false)
    private String founders;

    @Column(name = "products", nullable = false)
    private String products;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "company_customer",
            joinColumns = {@JoinColumn(name = "company_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "customer_id", nullable = false)})
    private Set<Customer> customers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employer", orphanRemoval = true)
    private Set<Employee> employees;

    protected Company() {
    }

    public Company(String name, LocalDate founded, String industry, String founders, String products) {
        this.name = name;
        this.founded = founded;
        this.industry = industry;
        this.founders = founders;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFounders() {
        return founders;
    }

    public void setFounders(String founders) {
        this.founders = founders;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (this.customers == null) {
            this.customers = new HashSet<>();
        }
        this.customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        this.customers.remove(customer);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new HashSet<>();
        }
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + this.id +
                ", name='" + this.name +
                ", founded=" + this.founded +
                ", industry='" + this.industry +
                ", founders='" + this.founders +
                ", products='" + this.products +
                ", customers=" + this.customers +
                ", employees=" + this.employees +
                '}';
    }
}