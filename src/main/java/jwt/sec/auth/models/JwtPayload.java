package jwt.sec.auth.models;

import java.util.HashSet;
import java.util.Set;

public class JwtPayload {
    private String iss;
    private String aud;
    private String sub;
    private int exp;
    private Set<String> rol = new HashSet<>();


    public JwtPayload() {
    }

    public JwtPayload(String iss, String aud, String sub, int exp, Set<String> rol) {
        super();
        this.iss = iss;
        this.aud = aud;
        this.sub = sub;
        this.exp = exp;
        this.rol = rol;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Set<String> getRol() {
        return rol;
    }

    public void setRol(Set<String> rol) {
        this.rol = rol;
    }


}
