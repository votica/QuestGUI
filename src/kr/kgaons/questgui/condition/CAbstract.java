package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Created by SkyLightQP on 2017-01-05.
 */
abstract class CAbstract {
    protected Player player;
    protected String QuestName;
    protected CAbstract(){}
    public CAbstract(Player player, String QuestName){
        this.player = player;
        this.QuestName = QuestName;
    }
    abstract public String getObjective();
    public boolean isGoing() {
        return Main.data.getBoolean(player.getName() + "." + QuestName + "-ing");
    }
    public boolean isObjective(){return getObjective().equals(Main.config.getString("Quests." + QuestName + ".objective").split(":")[0]);}
    public boolean isDone() {
        return Main.data.getBoolean(player.getName() + "." + QuestName + "-done");
    }
    public int getNowData() {
        return Main.data.getInt(player.getName() + "." + QuestName + "-data");
    }
    abstract public int getData(String QuestName);
    public String getQuestName() {
        return QuestName;
    }
}
