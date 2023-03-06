package tn.esprit.lssafe;

public class User {

    public String fullName, serial, email,ceinture;


    public User() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCeinture() {
        return ceinture;
    }

    public void setCeinture(String ceinture) {
        this.ceinture = ceinture;
    }

    public User(String fullName, String serial, String email, String ceinture) {
        this.fullName = fullName;
        this.serial = serial;
        this.email = email;
        this.ceinture = ceinture;
    }

    public User(String fullName, String serial, String email) {
        this.fullName = fullName;
        this.serial = serial;
        this.email = email;
    }
}