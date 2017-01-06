package kr.kgaons.questgui.quest;

import kr.kgaons.questgui.Main;
import kr.kgaons.questgui.utils.Util;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.MaterialData;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Event implements Listener{
    public HashMap<UUID,Integer> size = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(Main.data.get(p.getName()) == null){
            Main.data.set(p.getName() + ".temp","TEMP");
            Main.saveconfig();
            Util.logout(Logger.getLogger(""),"[QuestGUI] " + p.getName() + "님의 퀘스트 데이터 파일이 생성되었습니다.");
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getTitle().contains("QUEST| ")){
            e.setCancelled(true);
            if(e.getSlot() == 13){
                String name = e.getInventory().getTitle().replace("QUEST| ","");
                String npcname = Main.config.getString("Quests." + name + ".npcname");
                List npctext = Main.config.getStringList("Quests." + name + ".npctext");
                List completenpctext = Main.config.getStringList("Quests." + name + ".complete-npctext");
                Boolean done = Main.data.getBoolean(p.getName() + "." + name + "-done");
                Boolean npcdone = Main.data.getBoolean(p.getName() + "." + name + "-npcdone");
                Boolean repeat = Main.config.getBoolean("Quests." + name + ".repeat");
                String FollowQuest = Main.config.getString("Quests." + name + ".FollowQuest");
                Boolean Follow = Main.config.getBoolean("Quests." + name + ".Follow");
                // 연계 퀘스트
                if(Follow){
                    if(Main.data.getBoolean(p.getName() + "." + FollowQuest + "-npcdone")) { // 전 퀘스트를 완료 했다면
                        if (done == true && npcdone == true) { // 이미 퀘스트를 끝내고 보상을 받았다면
                            p.closeInventory();
                            p.sendMessage(Main.PREFIX + "§c해당 퀘스트는 이미 완료한 퀘스트입니다!");
                            Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                        } else {
                            if (done == true) { // 연계 퀘스트인데 퀘스트 완료를 해서 보상을 받아야 된다면
                                if (!size.containsKey(p.getUniqueId())) { // GUI 처음 열었다면
                                    size.put(p.getUniqueId(), 0);
                                    if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                        Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                        Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                        p.updateInventory();
                                        size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                    }
                                } else { // 대화를 한번 이상 시작 했다면
                                    if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                        if (size.get(p.getUniqueId()) == completenpctext.size()) { // 대화를 모두 다 읽었다면
                                            String[] reward = Main.config.getString("Quests." + name + ".reward").split(":");
                                            //Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                            Rewards.giveReward(p,name);
                                            Main.data.set(p.getName() + "." + name + "-npcdone", true);
                                            try {
                                                Main.data.save(Main.datafile);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                            Util.SoundPlayer(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1.0, (float) 1.0);
                                            p.sendMessage(Main.PREFIX + "§6퀘스트를 완료하고 보상을 받았습니다!");
                                            p.closeInventory();
                                        } else { // 대화를 아직 다 안 읽었다면
                                            Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        }
                                    }
                                }
                            }
                            else {
                                try {
                                    if (!size.containsKey(p.getUniqueId())) { // 연계퀘스트가 처음일때
                                        size.put(p.getUniqueId(), 0);
                                        if (size.get(p.getUniqueId()) <= npctext.size()) {
                                            Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        }
                                    } else {
                                        if (size.get(p.getUniqueId()) <= npctext.size()) {
                                            Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        }
                                    }
                                } catch (IndexOutOfBoundsException c) {
                                    Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                                }
                            }
                        }
                    }
                    else{
                        p.closeInventory();
                        p.sendMessage(Main.PREFIX + "§c먼저 §4\"" + FollowQuest + "\"§c을 완료 해주세요!");
                        Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                    }
                }
                else {
                   if (repeat == true) { // 만약 반복퀘스트라면
                        if (done == true) { // 퀘스트 완료해서 보상을 받을때 NPC 대사
                            if (!size.containsKey(p.getUniqueId())) {
                                size.put(p.getUniqueId(), 0);
                                if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                    Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                    Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                    p.updateInventory();
                                    size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                }
                            } else {
                                if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                    if (size.get(p.getUniqueId()) == completenpctext.size()) {
                                        String[] reward = Main.config.getString("Quests." + name + ".reward").split(":");
                                        //Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                        Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                        p.updateInventory();
                                        size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        Rewards.giveReward(p,name);
                                        Main.data.set(p.getName() + "." + name + "-npcdone", true);
                                        try {
                                            Main.data.save(Main.datafile);
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                        Util.SoundPlayer(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1.0, (float) 1.0);
                                        p.sendMessage(Main.PREFIX + "§6퀘스트를 완료하고 보상을 받았습니다!");
                                        Main.data.set(p.getName() + "." + name + "-done", null);
                                        try {
                                            Main.data.save(Main.datafile);
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                        p.closeInventory();
                                    } else {
                                        Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                        Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                        p.updateInventory();
                                        size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                    }
                                }
                            }
                        }
                        else {
                            if (Main.data.get(p.getName() + "temp-questname") == null) {
                                try {
                                    if (!size.containsKey(p.getUniqueId())) {
                                        size.put(p.getUniqueId(), 0);
                                        if (size.get(p.getUniqueId()) <= npctext.size()) {
                                            Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        }
                                    } else {
                                        if (size.get(p.getUniqueId()) <= npctext.size()) {
                                            Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                            Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                            p.updateInventory();
                                            size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                        }
                                    }
                                } catch (IndexOutOfBoundsException c) {
                                    Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                                }
                            }
                        }
                    } else { // 반복퀘스트가 아니라면
                       if (done == true && npcdone == true) { // 이미 퀘스트를 끝내고 보상을 받았다면
                           p.closeInventory();
                           p.sendMessage(Main.PREFIX + "§c해당 퀘스트는 이미 완료한 퀘스트입니다!");
                           Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                       } else {
                           if (done == true && npcdone != true) { // 퀘스트 완료해서 보상을 받을때 NPC 대사
                               if (!size.containsKey(p.getUniqueId())) { // GUI 처음 열었다면
                                   size.put(p.getUniqueId(), 0);
                                   if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                       Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                       Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                       p.updateInventory();
                                       size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                   }
                               } else { // 대화를 한번 이상 시작 했다면
                                   if (size.get(p.getUniqueId()) <= completenpctext.size()) {
                                       if (size.get(p.getUniqueId()) == completenpctext.size()) { // 대화를 모두 다 읽었다면
                                           String[] reward = Main.config.getString("Quests." + name + ".reward").split(":");
                                           //Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                           Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                           p.updateInventory();
                                           size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                           Rewards.giveReward(p,name);
                                           Main.data.set(p.getName() + "." + name + "-npcdone", true);
                                           try {
                                               Main.data.save(Main.datafile);
                                           } catch (IOException e1) {
                                               e1.printStackTrace();
                                           }
                                           Util.SoundPlayer(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1.0, (float) 1.0);
                                           p.sendMessage(Main.PREFIX + "§6퀘스트를 완료하고 보상을 받았습니다!");
                                           p.closeInventory();
                                       } else { // 대화를 아직 다 안 읽었다면
                                           Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + completenpctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                           Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                           p.updateInventory();
                                           size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                       }
                                   }
                               }
                           } else { // 보상 받을 차례가 아니고 아직 진행 상태라면
                               if (Main.data.get(p.getName() + "temp-questname") == null) {
                                   try {
                                       if (!size.containsKey(p.getUniqueId())) {
                                           size.put(p.getUniqueId(), 0);
                                           if (size.get(p.getUniqueId()) <= npctext.size()) {
                                               Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                               Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                               p.updateInventory();
                                               size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                           }
                                       } else {
                                           if (size.get(p.getUniqueId()) <= npctext.size()) {
                                               Util.Stack(npcname, 397, 3, 1, Arrays.asList("§f" + npctext.get(size.get(p.getUniqueId()))), e.getSlot(), e.getInventory());
                                               Util.SoundPlayer(p, Sound.UI_BUTTON_CLICK, (float) 1.0, (float) 1.0);
                                               p.updateInventory();
                                               size.put(p.getUniqueId(), size.get(p.getUniqueId()) + 1);
                                           }
                                       }
                                   } catch (IndexOutOfBoundsException c) {
                                       Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                                   }
                               }
                           }
                       }
                    }
                }
            }
            if(e.getSlot() == 21) {
                String name = e.getInventory().getTitle().replace("QUEST| ", "");
                List npctext = Main.config.getStringList("Quests." + name + ".npctext");
                if (Main.data.getBoolean(p.getName() + "." + name + "-ing") == true) {
                    p.closeInventory();
                    p.sendMessage(Main.PREFIX + "§6퀘스트가 이미 진행 중 입니다.");
                }
                else {
                    try {
                        if (size.get(p.getUniqueId()) == npctext.size()) {
                            p.sendMessage(Main.PREFIX + "§6퀘스트를 수락하였습니다!");
                            Main.data.set(p.getName() + "." + name + "-ing", true);
                            Main.data.set(p.getName() + ".temp-questname", name);
                            try {
                                Main.data.save(Main.datafile);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            p.closeInventory();
                        } else {
                            Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                            p.sendMessage(Main.PREFIX + "§c모든 대사를 다 읽어주세요!");
                        }
                    }catch (NullPointerException e6){}
                }
            }
            if(e.getSlot() == 23){
                String name = e.getInventory().getTitle().replace("QUEST| ","");
                List npctext = Main.config.getStringList("Quests." + name + ".npctext");
                if(Main.data.getBoolean(p.getName() + "." + name + "-ing") == true){
                    p.closeInventory();
                    p.sendMessage(Main.PREFIX + "§6퀘스트가 이미 진행 중 입니다.");
                }
                else {
                    try{
                        if (size.get(p.getUniqueId()) == npctext.size()) {
                            p.sendMessage(Main.PREFIX + "§6퀘스트를 거절하였습니다!");
                            Util.SoundPlayer(p, Sound.ENTITY_VILLAGER_NO, (float) 1.0, (float) 1.0);
                            p.closeInventory();
                        } else {
                            Util.SoundPlayer(p, Sound.BLOCK_ANVIL_PLACE, (float) 1.0, (float) 1.0);
                            p.sendMessage(Main.PREFIX + "§c모든 대사를 다 읽어주세요!");
                        }
                    }
                    catch (NullPointerException e5){}
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory().getTitle().contains("QUEST| ")){
            size.remove(e.getPlayer().getUniqueId());
        }
    }
}
