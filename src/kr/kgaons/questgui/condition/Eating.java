package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;

/**
 * Created by SkyLightQP on 2017-01-06.
 */
public class Eating extends CAbstract implements Listener {
    public Eating(){}
    public Eating(Player player, String QuestName) {
        super(player, QuestName);
    }

    @Override
    public String getObjective() {
        return "eat";
    }

    @Override
    public int getData() {
        return Integer.parseInt(Main.config.getString("Quests." + QuestName + ".objective").split(":")[2]);
    }
    public String getFood(){ return Main.config.getString("Quests." + QuestName + ".objective").split(":")[1]; }
    @EventHandler
    public void onEvent(PlayerItemConsumeEvent e){
        Eating ee = new Eating(e.getPlayer(), Main.data.getString(e.getPlayer().getName() + ".temp-questname"));
        Player p = e.getPlayer();
        if(ee.isGoing() && ee.isObjective()){
            if(e.getItem().getItemMeta().hasDisplayName()){
                if(e.getItem().getItemMeta().getDisplayName().equals(ee.getFood())){
                    if(ee.getNowData() < ee.getData()){
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-data", ee.getNowData() + 1);
                        Main.saveconfig();
                    }
                    else if(ee.getNowData() == ee.getData()){
                        p.sendMessage(Main.PREFIX + "§6" + ee.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-data", null);
                        Main.data.set(p.getName() + ".temp-questname", null);
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-ing", null);
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-done", true);
                        Main.saveconfig();
                    }
                }
            }
            else{
                if(e.getItem().getTypeId() == Integer.parseInt(ee.getFood())){
                    if(ee.getNowData() < ee.getData()){
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-data", ee.getNowData() + 1);
                        Main.saveconfig();
                    }
                    else if(ee.getNowData() == ee.getData()){
                        p.sendMessage(Main.PREFIX + "§6" + ee.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-data", null);
                        Main.data.set(p.getName() + ".temp-questname", null);
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-ing", null);
                        Main.data.set(p.getName() + "." + ee.getQuestName() + "-done", true);
                        Main.saveconfig();
                    }
                }
            }
        }
    }

}
