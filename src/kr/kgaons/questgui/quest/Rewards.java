package kr.kgaons.questgui.quest;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

/**
 * Created by SkyLightQP on 2017-01-06.
 */
public class Rewards {
    public static void giveReward(Player p, String name){
        String[] reward = Main.config.getString("Quests." + name + ".reward").split(":");
        switch (reward[0]) {
            case "item":
                p.getInventory().addItem(new MaterialData(Integer.parseInt(reward[1]), (byte) Integer.parseInt(reward[2])).toItemStack(Integer.parseInt(reward[3])));
                break;
        }
    }
}
