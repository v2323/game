package com.game.entity;

import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name="race")
    private Race race;

    @Enumerated(EnumType.STRING)
    @Column(name="profession")
    private Profession profession;

    @Column(name="experience")
    private Integer experience;

    @Column(name="level")
    private Integer level;

    @Column(name="untilNextLevel")
    private Integer untilNextLevel;

    @Column(name="birthday")
    private Date birthday;

    @Column(name="banned")
    private Boolean banned;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        checkId(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 12) {
            throw new BadRequestException();
        }
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.length() > 30){
            throw new BadRequestException();
        }
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        if(experience != null && (experience < 0 || experience > 10000000)){
            throw new BadRequestException();

        }
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        if(birthday.getTime() <= 946684800L || birthday.getTime() >= 92461892400000L) {
            throw new BadRequestException();
        }
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Player() {
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Integer experience, Integer level, Integer untilNextLevel, Date birthday, Boolean banned) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
        this.birthday = birthday;
        this.banned = banned;
    }

    public Player(String name, String title, Race race, Profession profession, Integer experience, Date birthday, Boolean banned) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.birthday = birthday;
        this.banned = banned;
    }

    public void checkId(Long id){
        if(id==null) {
            throw new NotFoundException();
        }
        if(id <= 0) {
            throw new BadRequestException();
        }
    }

}
