package kr.kgaons.questgui.condition;

import kr.kgaons.questgui.Main;
import org.bukkit.entity.Player;

/**
 * Created by SkyLightQP on 2017-01-05.
 */
abstract class CAbstract {
    protected Player player;
    protected String QuestName;
    public CAbstract(Player player, String QuestName){
        this.player = player;
        this.QuestName = QuestName;
    }
    abstract public String getObjective();

    public boolean isGoing() {
        return Main.data.getBoolean(player.getName() + "." + QuestName + "-ing");
    }
    public boolean isObjective(){return getObjective() == Main.config.getString("Quests." + QuestName + ".objective").split(":")[1];}
    public boolean isDone() {
        return Main.data.getBoolean(player.getName() + "." + QuestName + "-done");
    }
    public int getNowData() {
        return Main.data.getInt(player.getName() + "." + QuestName + "-data");
    }
    abstract public int getData();
    public String getQuestName() {
        return QuestName;
    }
}
