package jwt.sec.auth.domain.user;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class DbUser {
    private Long id;
    private String username;
    private String password;
    private String nazwisko;
    private String imie;
    private String email;
    private int locked;
    private Timestamp data_od;
    private Timestamp data_do;
    private Timestamp data_hasla;
    private Set<String> roles = new HashSet<>();

    public DbUser() {
    }

    public DbUser(Long id, String username, String password, String nazwisko,
                  String imie, String email, Timestamp data_od, Timestamp data_do, Timestamp data_hasla) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.email = email;
        this.data_od = data_od;
        this.data_do = data_do;
        this.data_hasla = data_hasla;
    }

    public DbUser(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getData_od() {
        return data_od;
    }

    public void setData_od(Timestamp data_od) {
        this.data_od = data_od;
    }

    public Timestamp getData_do() {
        return data_do;
    }

    public void setData_do(Timestamp data_do) {
        this.data_do = data_do;
    }

    public Timestamp getData_hasla() {
        return data_hasla;
    }

    public void setData_hasla(Timestamp data_hasla) {
        this.data_hasla = data_hasla;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> list) {
        this.roles = list;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}

