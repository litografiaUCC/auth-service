package com.litografiaartesplanchas.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_documento")
public class TypeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
        private Integer id;
    
    @Column(name = "nombre", nullable = false, length = 45)
        private String name;
    
    @Column(name = "acronym", nullable = true, length = 10)
        private String acronym;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAcronym(){
        return acronym;
    }

    public void setAcronym(String acronym){
        this.acronym = acronym;
    }
}
