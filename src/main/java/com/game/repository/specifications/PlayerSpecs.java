package com.game.repository.specifications;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PlayerSpecs {


    public static Specification<Player> nameContains(String word) {
        return (root, query, cb) -> word == null ? null : cb.like(root.get("name"), "%"+word+"%");
    }
    public static Specification<Player> titleContains(String word) {
        return (root, query, cb) -> word == null ? null : cb.like(root.get("title"), "%"+word+"%");
    }

    public static Specification<Player> minExp(Integer minExperience) {
        return (root, query, cb)  -> minExperience == null ? null : cb.greaterThanOrEqualTo(root.get("experience"), minExperience);
    }

    public static Specification<Player> maxExp(Integer maxExperience) {
        return (root, query, cb) -> maxExperience == null ? null : cb.lessThanOrEqualTo(root.get("experience"), maxExperience);
    }

    public static Specification<Player> minLevel(Integer minLevel) {
        return (root, query, cb)  -> minLevel == null ? null : cb.greaterThanOrEqualTo(root.get("level"), minLevel);
    }

    public static Specification<Player> maxLevel(Integer maxLevel) {
        return (root, query, cb) -> maxLevel == null ? null : cb.lessThanOrEqualTo(root.get("level"), maxLevel);
    }

    public static Specification<Player> raceFilter(Race race) {
        return (root, query, cb) -> race == null ? null : cb.equal(root.get("race"), race);
    }

    public static Specification<Player> professionFilter(Profession profession) {
        return (root, query, cb) -> profession == null ? null : cb.equal(root.get("profession"), profession);
    }
    public static Specification<Player> bannedFilter(Boolean banned) {
        return (root, query, cb) -> banned== null ? null : cb.equal(root.get("banned"), banned);
    }
    public static Specification<Player> activeFilter(Boolean banned) {
        return (root, query, cb) -> banned== null ? null : cb.equal(root.get("banned"), false);
    }

    public static Specification<Player> afterFilter(Long after) {
        return (root, query, cb)  -> after == null ? null : cb.greaterThan(root.get("birthday"),new Date(after));
    }
    public static Specification<Player> beforeFilter(Long before) {
        return (root, query, cb)  -> before == null ? null : cb.lessThan(root.get("birthday"),new Date(before));
    }

}
