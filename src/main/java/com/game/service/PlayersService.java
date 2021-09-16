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

    public void checkId(Long id) {
        if(id <= 0) {
    throw new BadRequestException();
        } else  {
            try {
                playersRepository.findById(id).get();
            } catch (Exception ex) {
                throw new NotFoundException();
            }
        }
    }

    public void checkExperience(Integer exp) {
        if(exp != null && exp < 0 || exp > 10000000){
            throw new BadRequestException();

        }
    }

    public void checkDate(Long date) {
        if(date <= 609L || date >= 92461892400000L) {
            throw new BadRequestException();
        }
    }

    public void checkName(String name) {
        if (name.length() > 12) {
            throw new BadRequestException();
        }
    }

    public void checkTitle(String title) {
        if(title.length() > 30){
            throw new BadRequestException();
        }
    }

    public PlayerModel getById(Long id) throws Exception {
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

    public Player updatePlayer(Long id, Player player) throws Exception {
        checkId(id);
        if(id==null){throw new NotFoundException();}
        Player oldPlayer = playersRepository.findById(id).get();


        if (player.getName() != null) {
            checkName(player.getName());
            oldPlayer.setName(player.getName());
        }
        if (player.getTitle() != null) {
            oldPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            oldPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            oldPlayer.setProfession(player.getProfession());
        }

        if (player.getBirthday() != null) {
            checkDate(player.getBirthday().getTime());
            oldPlayer.setBirthday(player.getBirthday());
        }

        if (player.getExperience() != null) {
            checkExperience(player.getExperience());
            oldPlayer.setExperience(player.getExperience());
            oldPlayer.setLevel(calculateLvl(oldPlayer.getExperience()));
            oldPlayer.setUntilNextLevel(calculateExpUntilNextLvl(oldPlayer.getLevel(), oldPlayer.getExperience()));
        }
        if(player.getBanned()!=null) {
            oldPlayer.setBanned(player.getBanned());
        }

        return playersRepository.save(oldPlayer);
    }

    public Player createPlayer1(Player player) throws Exception {

        Player oldPlayer = new Player();


        if (player.getName() != null) {
            checkName(player.getName());
            oldPlayer.setName(player.getName());
        } else{throw new BadRequestException();}
        if (player.getTitle() != null) {
            checkTitle(player.getTitle());
            oldPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            oldPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            oldPlayer.setProfession(player.getProfession());
        }

        if (player.getBirthday() != null) {
            checkDate(player.getBirthday().getTime());
            oldPlayer.setBirthday(player.getBirthday());
        }

        if (player.getExperience() != null) {
            checkExperience(player.getExperience());
            oldPlayer.setExperience(player.getExperience());
            oldPlayer.setLevel(calculateLvl(oldPlayer.getExperience()));
            oldPlayer.setUntilNextLevel(calculateExpUntilNextLvl(oldPlayer.getLevel(), oldPlayer.getExperience()));
        }
        if(player.getBanned()!=null) {
            oldPlayer.setBanned(player.getBanned());
        }

        return playersRepository.save(oldPlayer);
    }


    public Page<Player> getAllPlayersWithFilter(Specification<Player> playerSpecification, Pageable pageable) {

        return playersRepository.findAll(playerSpecification, pageable);
    }
}


