package com.starterkit3.views.models;

public class Person extends ModelObject {
       private String firstName;

       private String lastName;


       public String getFirstName() {
             return firstName;
       }

       public void setFirstName(String firstName) {
             firePropertyChange("firstName", this.firstName, this.firstName = firstName);
//             System.out.println("set first name: ");
       }

       public String getLastName() {
             return lastName;
       }

       public void setLastName(String lastName) {
             firePropertyChange("lastName", this.lastName, this.lastName = lastName);
       }
         
         
}