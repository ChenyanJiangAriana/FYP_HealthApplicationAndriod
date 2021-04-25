package com.jk.dayu.jkapp.HealthModule;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;


@Table("health")
public class HealthBean {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("id")
    public String id;
    @Column("name")
    public String name;

    @Column("date")
    public String date;

    // 基础测试
    // 年龄
    @Column("age")
    public String age;

    // 体重
    @Column("weight")
    public String weight;

    // 身高
    @Column("height")
    public String height;

    // BMI
    @Column("bmi")
    public String bmi;

    // 心跳
    @Column("beat")
    public String beat;

// 进阶测试

    // 高血压
    @Column("presshigh")
    public String presshigh;

    // 低血压
    @Column("presslow")
    public String presslow;

    // 血糖
    @Column("bloodsugar")
    public String bloodsugar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }

    public String getPresshigh() {
        return presshigh;
    }

    public void setPresshigh(String presshigh) {
        this.presshigh = presshigh;
    }

    public String getPresslow() {
        return presslow;
    }

    public void setPresslow(String presslow) {
        this.presslow = presslow;
    }

    public String getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(String bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
