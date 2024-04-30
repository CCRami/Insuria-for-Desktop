package Entities;

public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String avatar;
    private boolean isBlocked;
    private boolean isVerified;
    private String birth_date;
    private String role;
    private int phone_number;
    private String password;
    public User() {

    }

    public User(String nom, String prenom, String email, String mdp, int tel, boolean state, String image, String role) {
        this.last_name = nom;
        this.first_name = prenom;
        this.email = email;
        this.isVerified = state;
        this.role = role;
        this.password = mdp;
        this.avatar = image;
        this.phone_number = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int isBlocked() {
        return isBlocked ? 1 : 0;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int isVerified() {
        return isVerified ? 1 : 0;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return  last_name + "   " +
                first_name + "   " +
                email + "   " +
                phone_number + "   " +
                birth_date + "   " +
                isVerified + "   " +
                isBlocked + "   " +
                role
                ;
    }

    public User(String nom, String prenom, String email, String mdp, int tel, String bd, String role, Boolean isv, Boolean isb, String image) {
        this.last_name = nom;
        this.first_name = prenom;
        this.email = email;
        this.birth_date = bd;
        this.role = role;
        this.password = mdp;
        this.phone_number = tel;
        this.isVerified= isv;
        this.isBlocked=isb;
        this.avatar = image;
    }

    public User(int id,String nom,String prenom) {
        this.id = id;
        this.last_name=nom;
        this.first_name=prenom;
    }

    public User(String nom, String prenom, String email, boolean state, int tel, String mdp) {
        this.last_name = nom;
        this.first_name = prenom;
        this.email = email;
        this.isVerified = state;
        this.phone_number = tel;
        this.password = mdp;
    }

    public User(int id) {
        this.id = id;
    }
    public User(String role) {
        this.role = role;
    }
}
