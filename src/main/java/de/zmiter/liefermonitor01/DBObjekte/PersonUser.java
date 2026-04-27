/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.DBObjekte;

import java.util.ArrayList;

/**
 *
 * @author lepeschko
 */
public class PersonUser {
    int id = 0;
    String name = "Username";
    String login = "login";
    String password = "password";
    String abteilung = "abtName";
    String projekt = "projektName";
    String zugangsGruppe = "A";
    String email = "";
    String emailPasswort = "";
    ArrayList<Lieferung> lifetListPerson = new ArrayList<>();
    
    public PersonUser(String name){
        this.name = name;
    }
    
    public void setName(String str){
        this.name = str;
    }
    
     public void setLogin(String str){
        this.login = str;
    }
      public void setPassword(String str){
        this.password = str;
    }
       public void setAbteilung(String str){
        this.abteilung = str;
    }
       public void setProjekt(String projekt){
           this.projekt = projekt;
       }
             
         public void setZugangsGruppe(String zugangsGruppe){
           this.zugangsGruppe = zugangsGruppe;
       }
          public void setEmail(String email){
           this.email = email;
       }
           public void setPassEmail(String passEmail){
           this.emailPasswort = passEmail;
       }
     
      public String getLogin(){
           
           return login;
       }
       public String getPassword(){
           
           return password;
       }
       public String getAbteilung(){
           
           return abteilung;
       }
       public String getProjekt(){
           return projekt;
       }
       
       public String getZugangsGruppe(){
           return zugangsGruppe;
       }
       
    public String getEmail() {
        return email;
    }
     
    public String getPassEmail() {
        return emailPasswort;
    }  
       

    public String getName() {
        return name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
