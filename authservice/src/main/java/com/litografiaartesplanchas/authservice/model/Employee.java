package com.litografiaartesplanchas.authservice.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleado")
public class Employee implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "apellido", nullable = false, length = 100)
    private String lastName;

    @Column(name = "numero_documento", nullable = false)
    private BigInteger documentNumber;

    @Column(name = "correo", nullable = false, length = 255, unique =  true)
    private String email;

    @Column(name = "contraseña", nullable = false, length = 255)
    private String password;

    @Column(name = "telefono", nullable = true, length = 30)
    private String phone;

    @Column(name = "foto", nullable = true , length = 255)
	private String photo;

    @Column(name = "activo", columnDefinition = "BOOLEAN DEFAULT true")
	private boolean IsActive;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento")
    private TypeDocument typeDocument;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }
    
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigInteger getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigInteger document_number) {
		this.documentNumber = document_number;
	}

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPhoneNumber(){
        return phone;
    }

    public void setPhoneNumber(String phone){
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

}
