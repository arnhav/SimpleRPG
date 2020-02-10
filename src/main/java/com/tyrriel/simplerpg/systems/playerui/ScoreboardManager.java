package com.tyrriel.simplerpg.systems.playerui;

import com.tyrriel.simplerpg.systems.characters.CharacterManager;
import com.tyrriel.simplerpg.systems.characters.RPGCharacter;
import com.tyrriel.simplerpg.util.HiddenStringUtils;
import com.tyrriel.simplerpg.util.SpecialChars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {

    public static void hideScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        for (Objective objective : scoreboard.getObjectives()){
            objective.unregister();
        }
        for (Team team : scoreboard.getTeams()){
            team.unregister();
        }
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    public static void sendPartyScoreboard(Player player, Player[] party){
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Info");
        Score score;
        if (objective == null){
            objective = scoreboard.registerNewObjective("Info", "dummy", ChatColor.BOLD + "Party");
            for (int i = 0; i < party.length; i++){
                RPGCharacter character = CharacterManager.characters.get(party[i]);

                Team line = scoreboard.registerNewTeam("line" + i);
                Team phealth = scoreboard.registerNewTeam("phealth" + i);
                Team pmana = scoreboard.registerNewTeam("pmana" + i);

                int p = (i+1)*3;
                int k = p-1;
                int j = k-1;

                String message = "";

                line.addEntry(HiddenStringUtils.encodeString("l" + i));
                line.setPrefix(ChatColor.GRAY + "----------");
                score = objective.getScore(HiddenStringUtils.encodeString("l" + i));
                score.setScore(p);
                phealth.addEntry(HiddenStringUtils.encodeString("ph" + i));
                message = getColor(i) + SpecialChars.partyMember() + ChatColor.WHITE + " - " +
                               ChatColor.RED + character.getHealth() + ChatColor.WHITE + "/" + ChatColor.RED + character.getMaxHealth();
                phealth.setPrefix(message);
                score = objective.getScore(HiddenStringUtils.encodeString("ph" + i));
                score.setScore(k);
                pmana.addEntry(HiddenStringUtils.encodeString("pm" + i));
                message = ChatColor.WHITE + "   - " +
                                ChatColor.AQUA + character.getMana() + ChatColor.WHITE + "/" + ChatColor.AQUA + character.getMaxMana();
                pmana.setPrefix(message);
                score = objective.getScore(HiddenStringUtils.encodeString("pm" + i));
                score.setScore(j);
            }
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } else {
            for (int i = 0; i < party.length; i++){
                RPGCharacter character = CharacterManager.characters.get(party[i]);

                Team phealth = scoreboard.getTeam("phealth" + i);
                Team pmana = scoreboard.getTeam("pmana" + i);

                String message = "";
                if (character.getHealth() > 0) {
                    message = getColor(i) + SpecialChars.partyMember() + ChatColor.WHITE + " - " +
                            ChatColor.RED + character.getHealth() + ChatColor.WHITE + "/" + ChatColor.RED + character.getMaxHealth();
                } else {
                    message = getColor(i) + SpecialChars.partyMember() + ChatColor.WHITE + " - " +
                            ChatColor.GRAY + SpecialChars.dead();
                }
                phealth.setPrefix(message);
                message = ChatColor.WHITE + "   - " +
                        ChatColor.AQUA + character.getMana() + ChatColor.WHITE + "/" + ChatColor.AQUA + character.getMaxMana();
                pmana.setPrefix(message);
            }
        }
        player.setScoreboard(scoreboard);
    }

    private static ChatColor getColor(int i){
        if (i == 0) return ChatColor.DARK_PURPLE;
        if (i == 1) return ChatColor.YELLOW;
        if (i == 2) return ChatColor.GREEN;
        if (i == 3) return ChatColor.DARK_RED;
        return ChatColor.DARK_RED;
    }

}
