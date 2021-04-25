package com.jk.dayu.jkapp.UserModule;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;
import lombok.Data;

@Table("doctor")
@Data
public class Doctor implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("did")
    private String did;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("introduce")
    private String introduce;
    @Column("role")
    private String role;

    public String getDid() {
        return did;
    }
    public void setDid(String did) {
        this.did = did;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


