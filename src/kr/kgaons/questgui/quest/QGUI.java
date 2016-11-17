package kr.kgaons.questgui.quest;

import kr.kgaons.questgui.Main;
import kr.kgaons.questgui.utils.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class QGUI {
    public static void openGUI(String name, Player p){
        Boolean done = Main.data.getBoolean(p.getName() + "." + name + "-done");
        if(done == true) { // 퀘스트 완료해서 보상을 받아야할때 여는 GUI, 수락과 거절을 없앤다.
            Inventory inv = Bukkit.createInventory(null, 36, "QUEST| " + name);
            String npcname = Main.config.getString("Quests." + name + ".npcname");
            Util.Stack(npcname, 397, 3, 1, Arrays.asList(""), 13, inv);

            p.openInventory(inv);
        }
        else {
            Inventory inv = Bukkit.createInventory(null, 36, "QUEST| " + name);
            String npcname = Main.config.getString("Quests." + name + ".npcname");
            Util.Stack(npcname, 397, 3, 1, Arrays.asList(""), 13, inv);
            Util.Stack("§a수락", 351, 10, 1, Arrays.asList("§7해당 퀘스트를 수락합니다.", "§7모든 대화가 끝나야 가능합니다."), 21, inv);
            Util.Stack("§c거절", 351, 9, 1, Arrays.asList("§7해당 퀘스트를 수락합니다.", "§7모든 대화가 끝나야 가능합니다."), 23, inv);

            p.openInventory(inv);
        }
    }
}
