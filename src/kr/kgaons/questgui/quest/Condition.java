package kr.kgaons.questgui.quest;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.IOException;

/**
 * Created by SkyLightQP on 2016-11-13.
 */
public class Condition implements Listener{
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        String name = Main.data.getString(p.getName() + ".temp-questname");
        boolean ing = Main.data.getBoolean(p.getName() + "." + name + "-ing");
        boolean done = Main.data.getBoolean(p.getName() + "." + name + "-done");
        int data = Main.data.getInt(p.getName() + "." + name + "-data");
        String[] objective = Main.config.getString("Quests." + name + ".objective").split(":");
        if (p != null) {
            if (ing) {
                switch (objective[0]){
                    case "killmob":
                        if(e.getEntity().getCustomName() == null){
                            if (e.getEntity().getType().name().equals(objective[1])) {
                                if (data < Integer.parseInt(objective[2])) {
                                    Main.data.set(p.getName() + "." + name + "-data", data + 1);
                                    try {
                                        Main.data.save(Main.datafile);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                if (data == Integer.parseInt(objective[2])) {
                                    p.sendMessage(Main.PREFIX + "§6" + name + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                                    Main.data.set(p.getName() + "." + name + "-data", null);
                                    Main.data.set(p.getName() + ".temp-questname", null);
                                    Main.data.set(p.getName() + "." + name + "-ing", null);
                                    Main.data.set(p.getName() + "." + name + "-done", true);
                                    try {
                                        Main.data.save(Main.datafile);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                        else {
                            if (e.getEntity().getCustomName().equals(objective[1])) {
                                if (data < Integer.parseInt(objective[2])) {
                                    Main.data.set(p.getName() + "." + name + "-data", data + 1);
                                    try {
                                        Main.data.save(Main.datafile);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                if (data == Integer.parseInt(objective[2])) {
                                    p.sendMessage(Main.PREFIX + "§6" + name + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                                    Main.data.set(p.getName() + "." + name + "-data", null);
                                    Main.data.set(p.getName() + ".temp-questname", null);
                                    Main.data.set(p.getName() + "." + name + "-ing", null);
                                    Main.data.set(p.getName() + "." + name + "-done", true);
                                    try {
                                        Main.data.save(Main.datafile);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
}