package jwt.sec.auth.domain.user;

import java.sql.Timestamp;

public class DbUsrRoles {
    private Long id;
    private Long id_user;
    private Long id_role;
    private String code_role;
    private String descr;
    private Timestamp date_from;
    private Timestamp date_to;

    public DbUsrRoles() {
    }

    public DbUsrRoles(Long id, Long id_user, Long id_role, String code_role, String descr, Timestamp date_from, Timestamp date_to) {
        this.id = id;
        this.id_user = id_user;
        this.id_role = id_role;
        this.code_role = code_role;
        this.descr = descr;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public String getCode_role() {
        return code_role;
    }

    public void setCode_role(String code_role) {
        this.code_role = code_role;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Timestamp getDate_from() {
        return date_from;
    }

    public void setDate_from(Timestamp date_from) {
        this.date_from = date_from;
    }

    public Timestamp getDate_to() {
        return date_to;
    }

    public void setDate_to(Timestamp date_to) {
        this.date_to = date_to;
    }
}

