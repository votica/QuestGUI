package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by SkyLightQP on 2017-01-05.
 */
public class MobKill extends CAbstract implements Listener{
    public MobKill(){}
    public MobKill(Player player, String QuestName) {
        super(player,QuestName);
    }
    @Override
    public String getObjective() {
        return "mobkill";
    }
    @Override
    public int getData(String QuestName){
        return Integer.parseInt(Main.config.getString("Quests." + QuestName + ".objective").split(":")[2]);
    }
    public String getMobType(String QuestName){
        return Main.config.getString("Quests." + QuestName + ".objective").split(":")[1];
    }
    @EventHandler
    public void onEvent(EntityDeathEvent e){
        String QuestName = Main.data.getString(e.getEntity().getKiller().getName() + ".temp-questname");
        MobKill mk = new MobKill(e.getEntity().getKiller(),QuestName);
        Player p = e.getEntity().getKiller();
        if(p.isOnline() && mk.isGoing() && mk.isObjective()){
            if(e.getEntity().getCustomName() == null){
                if(e.getEntity().getType().getName().equals(mk.getMobType(QuestName))){
                    if(mk.getNowData() < mk.getData(QuestName)){
                        Main.data.set(p.getName() + "." + mk.getQuestName() + "-data", mk.getNowData() + 1);
                        Main.saveconfig();
                    }
                    else if(mk.getNowData() == mk.getData(QuestName)){
                        p.sendMessage(Main.PREFIX + "§6" + mk.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                        Main.data.set(p.getName() + "." + mk.getQuestName() + "-data", null);
                        Main.data.set(p.getName() + ".temp-questname", null);
                        Main.data.set(p.getName() + "." + mk.getQuestName() + "-ing", null);
                        Main.data.set(p.getName() + "." + mk.getQuestName() + "-done", true);
                        Main.saveconfig();
                    }
                }
            }
            else if(e.getEntity().getCustomName().equals(mk.getMobType(QuestName))){
                if(mk.getNowData() < mk.getData(QuestName)){
                    Main.data.set(p.getName() + "." + mk.getQuestName() + "-data", mk.getNowData() + 1);
                    Main.saveconfig();
                }
                else if(mk.getNowData() == mk.getData(QuestName)){
                    p.sendMessage(Main.PREFIX + "§6" + mk.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                    Main.data.set(p.getName() + "." + mk.getQuestName() + "-data", null);
                    Main.data.set(p.getName() + ".temp-questname", null);
                    Main.data.set(p.getName() + "." + mk.getQuestName() + "-ing", null);
                    Main.data.set(p.getName() + "." + mk.getQuestName() + "-done", true);
                    Main.saveconfig();
                }
            }
        }
    }
}
