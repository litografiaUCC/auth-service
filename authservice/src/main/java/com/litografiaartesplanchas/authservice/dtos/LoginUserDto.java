package com.litografiaartesplanchas.authservice.dtos;

public class LoginUserDto {
    private String email;
    
    private String password;

    private int typeUser;
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(int typeUser) {
        this.typeUser = typeUser;
    }

}
