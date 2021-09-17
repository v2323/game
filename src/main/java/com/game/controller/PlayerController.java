package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.model.PlayerModel;
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
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable Long id) throws Exception {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(playersService.getById(id));
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(playersService.delete(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getFilter(
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
            @RequestParam(value = "before", required = false) Long before) {


        Specification<Player> filter = new PlayerSpecs().getSpecs(name, title, minExperience, maxExperience, minLevel, maxLevel, race, profession, banned, after, before);
        List<Player> resultList = playersService.getAllPlayersWithFilter(filter, PageRequest.of(pageNumber, pageSize)).getContent();
        return ResponseEntity.ok(resultList);

    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> getCount(
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
            @RequestParam(value = "before", required = false) Long before) {

        Specification<Player> filter = new PlayerSpecs().getSpecs(name, title, minExperience, maxExperience, minLevel, maxLevel, race, profession, banned, after, before);

        List<Player> resultList = playersService.getAllPlayersWithFilter(filter, PageRequest.of(pageNumber, pageSize)).getContent();
        Integer count = resultList.size();
        return ResponseEntity.ok(count);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            return ResponseEntity.ok(playersService.createPlayer(player));

        } catch (
                BadRequestException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id,
                                       @Validated @RequestBody Player player) {
        try {
            return ResponseEntity.ok(playersService.updatePlayer(id, player));

        } catch (
                BadRequestException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

    }


}


