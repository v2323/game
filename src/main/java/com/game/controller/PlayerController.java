package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.repository.specifications.PlayerSpecs;
import com.game.service.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private PlayersService playersService;

    @Autowired
    public void setPlayersService(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("/players/{id}")
    public ResponseEntity getPlayer(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Введен не валидный ID");
        }
        try {
            return ResponseEntity.ok(playersService.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Введен не валидный ID");
        }
        try {
            return ResponseEntity.ok(playersService.delete(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/players")
    public ResponseEntity getFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "order", required = false) PlayerOrder order) {


        Specification<Player> filter = Specification.where(PlayerSpecs.nameContains(name))
                .and(PlayerSpecs.titleContains(title))
                .and(PlayerSpecs.minExp(minExperience))
                .and(PlayerSpecs.maxExp(maxExperience))
                .and(PlayerSpecs.minLevel(minLevel))
                .and(PlayerSpecs.maxLevel(maxLevel))
                .and(PlayerSpecs.raceFilter(race))
                .and(PlayerSpecs.professionFilter(profession))
                .and(PlayerSpecs.bannedFilter(banned))
                .and(PlayerSpecs.afterFilter(after))
                .and(PlayerSpecs.beforeFilter(before));
        List<Player> resultList = playersService.getAllPlayersWithFilter(filter, PageRequest.of(pageNumber, pageSize)).getContent();
        return ResponseEntity.ok(resultList);

    }

    @GetMapping("/players/count")
    public ResponseEntity getCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "pageSize", defaultValue = "40") Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "order", required = false) PlayerOrder order) {

        Specification<Player> filter = Specification.where(PlayerSpecs.nameContains(name))
                .and(PlayerSpecs.titleContains(title))
                .and(PlayerSpecs.minExp(minExperience))
                .and(PlayerSpecs.maxExp(maxExperience))
                .and(PlayerSpecs.minLevel(minLevel))
                .and(PlayerSpecs.maxLevel(maxLevel))
                .and(PlayerSpecs.raceFilter(race))
                .and(PlayerSpecs.professionFilter(profession))
                .and(PlayerSpecs.bannedFilter(banned))
                .and(PlayerSpecs.afterFilter(after))
                .and(PlayerSpecs.beforeFilter(before));

        List<Player> resultList = playersService.getAllPlayersWithFilter(filter, PageRequest.of(pageNumber, pageSize)).getContent();
        Integer count = resultList.size();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/players")
    public ResponseEntity createPlayer(@RequestBody Player player
    ) throws Exception{
        try{
            return ResponseEntity.ok(playersService.createPlayer1(player));

        } catch (
                BadRequestException ex) {
            return ResponseEntity.badRequest().body("2");
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping("/players/{id}")
    public ResponseEntity updatePlayer(@PathVariable Long id,
                                       @Validated @RequestBody Player player

    ) throws Exception {

        try{
            return ResponseEntity.ok(playersService.updatePlayer(id, player));

            } catch (
                BadRequestException ex) {
            return ResponseEntity.badRequest().body("2");
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

    }


}


