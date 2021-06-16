/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 *
 * @author SJUBE
 */
@Entity
@NamedQuery(name = "Dog.deleteAllRows", query = "DELETE from Dog")

public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String breed;
    private String gender;
    //Potentiallity: Would have used a Date datatype for here if i wasent not under pressure of 1 day of production time.
    private String birthDate;
//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "dogList")
    @ManyToMany
    private List<Walker> walkerList;

    public Dog() {
    }

    public Dog(String name, String breed, String gender, String birthDate) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.birthDate = birthDate;
        this.walkerList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Walker> getWalkerList() {
        return walkerList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dog{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", breed=").append(breed);
        sb.append(", gender=").append(gender);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", walkerList=").append(walkerList);
        sb.append('}');
        return sb.toString();
    }

}
