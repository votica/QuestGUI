package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by SkyLightQP on 2017-01-05.
 */
public class DoCmd extends CAbstract implements Listener {
    public DoCmd(Player player, String QuestName) {
        super(player, QuestName);
    }
    @Override
    public String getObjective() {
        return "command";
    }
    @Override
    public int getData() {
        return 0;
    }
    public String getCommand(){
        return Main.config.getString("Quests." + QuestName + ".objective").split(":")[1];
    }
    public void onDoCmd(PlayerCommandPreprocessEvent e){
        DoCmd dc = new DoCmd(e.getPlayer(), Main.data.getString(e.getPlayer().getName() + ".temp-questname"));
        Player p = e.getPlayer();
        if(dc.isGoing() && dc.isObjective()){
            if(e.getMessage().contains(dc.getCommand())){
                p.sendMessage(Main.PREFIX + "§6" + dc.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                Main.data.set(p.getName() + "." + dc.getQuestName() + "-data", null);
                Main.data.set(p.getName() + ".temp-questname", null);
                Main.data.set(p.getName() + "." + dc.getQuestName() + "-ing", null);
                Main.data.set(p.getName() + "." + dc.getQuestName() + "-done", true);
                Main.saveconfig();
            }
        }
    }
}
