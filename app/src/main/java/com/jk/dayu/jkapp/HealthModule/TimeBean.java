package com.jk.dayu.jkapp.HealthModule;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import lombok.Data;


@Data
@Table("TimeBean")
public class TimeBean {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;
    @Column("name")
    public String name;
    @Column("time")
    public String time;
    @Column("tip")
    public String tip;
    @Column("uid")
    public String uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
