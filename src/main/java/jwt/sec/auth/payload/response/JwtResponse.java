package jwt.sec.auth.payload.response;

import java.util.List;

public class JwtResponse {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;

    public JwtResponse(String username, String lastname, String firstName, String email, List<String> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.lastName = lastname;
        this.firstName = firstName;
        this.id = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setImie(String firstName) {
        this.firstName = firstName;
    }

}
