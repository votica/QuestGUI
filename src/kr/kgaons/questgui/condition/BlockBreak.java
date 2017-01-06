package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import net.minecraft.server.v1_10_R1.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by SkyLightQP on 2017-01-06.
 */
public class BlockBreak extends CAbstract implements Listener{
    public BlockBreak(){}
    public BlockBreak(Player player, String QuestName) {
        super(player, QuestName);
    }

    @Override
    public String getObjective() {
        return "blockbreak";
    }

    @Override
    public int getData() {
        return Integer.parseInt(Main.config.getString("Quests." + QuestName + ".objective").split(":")[2]);
    }
    public String getBlockCode(){
        return Main.config.getString("Quests." + QuestName + ".objective").split(":")[1];
    }
    @EventHandler
    public void onEvent(BlockBreakEvent e){
        BlockBreak bb = new BlockBreak(e.getPlayer(), Main.data.getString(e.getPlayer().getName() + ".temp-questname"));
        Player p = e.getPlayer();
        if(bb.isGoing() && bb.isObjective()){
            if(e.getBlock().getTypeId() == Integer.parseInt(bb.getBlockCode())){
                if(bb.getNowData() < bb.getData()){
                    Main.data.set(p.getName() + "." + bb.getQuestName() + "-data", bb.getNowData() + 1);
                    Main.saveconfig();
                }
                else if(bb.getNowData() == bb.getData()){
                    p.sendMessage(Main.PREFIX + "§6" + bb.getQuestName() + "퀘스트를 완료 하였습니다! NPC에게 찾아가보세요!");
                    Main.data.set(p.getName() + "." + bb.getQuestName() + "-data", null);
                    Main.data.set(p.getName() + ".temp-questname", null);
                    Main.data.set(p.getName() + "." + bb.getQuestName() + "-ing", null);
                    Main.data.set(p.getName() + "." + bb.getQuestName() + "-done", true);
                    Main.saveconfig();
                }
            }
        }
    }
}
