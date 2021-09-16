package com.game.model;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

public class PlayerModel {

    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public static PlayerModel toModel(Player entity) {
        PlayerModel pmodel = new PlayerModel();
        pmodel.setId(entity.getId());
        pmodel.setName(entity.getName());
        pmodel.setTitle(entity.getTitle());
        pmodel.setRace(entity.getRace());
        pmodel.setProfession(entity.getProfession());
        pmodel.setBirthday(entity.getBirthday().getTime());
        pmodel.setBanned(entity.getBanned());
        pmodel.setExperience(entity.getExperience());
        pmodel.setLevel(entity.getLevel());
        pmodel.setUntilNextLevel(entity.getUntilNextLevel());


        return pmodel;
    }

    public PlayerModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
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
}
