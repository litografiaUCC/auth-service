package com.litografiaartesplanchas.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rol")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
        private Integer id_rol;

    @Column(name = "nombre", nullable = false, length = 45)
        private String name;

    public Integer getId(){
        return id_rol;
    }

    public void setId(Integer id_rol){
        this.id_rol = id_rol;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
