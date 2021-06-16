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
import javax.persistence.OneToMany;

/**
 *
 * @author SJUBE
 */
@Entity
@NamedQuery(name = "Walker.deleteAllRows", query = "DELETE from Walker")
public class Walker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phone;
//    @JoinTable(name = "walker_dogs", joinColumns = {
//        @JoinColumn(name = "name", referencedColumnName = "name")}, inverseJoinColumns = {
//        @JoinColumn(name = "name", referencedColumnName = "name")})
//    @JoinTable(name = "walker_dogs")
    @ManyToMany(mappedBy = "walkerList", cascade = CascadeType.PERSIST)
    List<Dog> dogList;

    public Walker(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogList = new ArrayList<>();
    }

    public Walker() {
    }

    public List<String> getDogsAsStrings() {
        if (dogList.isEmpty()) {
            return null;
        }
        List<String> dogsAsStrings = new ArrayList<>();
        dogList.forEach((dog) -> {
            dogsAsStrings.add(dog.getName());
        });
        return dogsAsStrings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addDog(Dog dog) {
        if (dog != null) {
            this.dogList.add(dog);
            dog.getWalkerList().add(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Walker{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append('}');
        return sb.toString();
    }

}
