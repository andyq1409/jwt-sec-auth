package jwt.sec.auth.domain.user;

import java.sql.Timestamp;

public class DbUsrRoles {
    private Long id;
    private Long id_user;
    private Long id_role;
    private String code_role;
    private String descr;
    private Timestamp data_from;
    private Timestamp data_to;

    public DbUsrRoles() {
    }

    public DbUsrRoles(Long id, Long id_user, Long id_role, String code_role, String descr, Timestamp data_from, Timestamp data_to) {
        this.id = id;
        this.id_user = id_user;
        this.id_role = id_role;
        this.code_role = code_role;
        this.descr = descr;
        this.data_from = data_from;
        this.data_to = data_to;
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

    public Timestamp getData_from() {
        return data_from;
    }

    public void setData_from(Timestamp data_from) {
        this.data_from = data_from;
    }

    public Timestamp getData_to() {
        return data_to;
    }

    public void setData_to(Timestamp data_to) {
        this.data_to = data_to;
    }
}

