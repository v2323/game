package com.game.service;

import com.game.entity.Player;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.model.PlayerModel;
import com.game.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayersService {


    private PlayersRepository playersRepository;

    @Autowired
    public void setPlayersRepository(PlayersRepository playersRepository) {
        this.playersRepository = playersRepository;
    }

    public Integer calculateLvl(Integer exp) {
        return (int) ((Math.sqrt(2500 + 200 * exp) - 50) / 100);
    }

    public Integer calculateExpUntilNextLvl(Integer lvl, Integer exp) {
        return (50 * (lvl + 1) * (lvl + 2) - exp);
    }

    public Player createOrUpdatePlayer(Player tempPlayer, Player respPlayer) {
        if (respPlayer.getName() != null) {
            tempPlayer.setName(respPlayer.getName());
        }
        if (respPlayer.getTitle() != null) {
            tempPlayer.setTitle(respPlayer.getTitle());
        }
        if (respPlayer.getRace() != null) {
            tempPlayer.setRace(respPlayer.getRace());
        }
        if (respPlayer.getProfession() != null) {
            tempPlayer.setProfession(respPlayer.getProfession());
        }

        if (respPlayer.getBirthday() != null) {
            tempPlayer.setBirthday(respPlayer.getBirthday());
        }

        if (respPlayer.getExperience() != null) {
            tempPlayer.setExperience(respPlayer.getExperience());
            tempPlayer.setLevel(calculateLvl(tempPlayer.getExperience()));
            tempPlayer.setUntilNextLevel(calculateExpUntilNextLvl(tempPlayer.getLevel(), tempPlayer.getExperience()));
        }
        if(respPlayer.getBanned()!=null) {
            tempPlayer.setBanned(respPlayer.getBanned());
        }

        return playersRepository.save(tempPlayer);
    }


    public PlayerModel getById(Long id) {
        Player player = playersRepository.findById(id).get();
        return PlayerModel.toModel(player);
    }

    public List<Player> getAll() {
        return playersRepository.findAll();
    }

    public Long delete(Long id) {
        playersRepository.deleteById(id);
        return id;
    }

    public Player updatePlayer(Long id, Player player) {
        player.checkId(id);
        if (id > playersRepository.count()) {
            throw new NotFoundException();
        }
        Player oldPlayer = playersRepository.findById(id).get();
        return createOrUpdatePlayer(oldPlayer, player);
    }

    public Player createPlayer(Player player){

        Player newPlayer = new Player();
        if(player.getName()==null){
            throw new BadRequestException();
        }
        return createOrUpdatePlayer(newPlayer, player);
    }


    public Page<Player> getAllPlayersWithFilter(Specification<Player> playerSpecification, Pageable pageable) {

        return playersRepository.findAll(playerSpecification, pageable);
    }
}



