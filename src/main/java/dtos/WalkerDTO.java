/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Walker;

/**
 *
 * @author SJUBE
 */
public class WalkerDTO {

    private int id;
    private String name;
    private String address;
    private String phone;

    public WalkerDTO(Walker wk) {
        this.id = wk.getId();
        this.name = wk.getName();
        this.address = wk.getAddress();
        this.phone = wk.getPhone();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WalkerDTO{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phone);
        sb.append('}');
        return sb.toString();
    }

}
