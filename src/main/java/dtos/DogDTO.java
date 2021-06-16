/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Dog;
import entities.Walker;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SJUBE
 */
public class DogDTO {

    private Integer id;
    private String name;
    private String breed;
    private String gender;
    private String birthDate;
    private List<WalkerDTO> walkersDTO;

    public DogDTO(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.gender = dog.getGender();
        this.birthDate = dog.getBirthDate();
        this.walkersDTO = new ArrayList<>();
        dog.getWalkerList().forEach(walker -> this.walkersDTO.add(new WalkerDTO(walker)));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DogDTO{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", breed=").append(breed);
        sb.append(", gender=").append(gender);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", walkers=").append(walkersDTO);
        sb.append('}');
        return sb.toString();
    }

}
