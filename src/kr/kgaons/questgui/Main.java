package kr.kgaons.questgui;

import kr.kgaons.questgui.commands.QuestCommand;
import kr.kgaons.questgui.condition.BlockBreak;
import kr.kgaons.questgui.condition.DoCmd;
import kr.kgaons.questgui.condition.Eating;
import kr.kgaons.questgui.condition.MobKill;
import kr.kgaons.questgui.quest.Event;
import kr.kgaons.questgui.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by SkyLightQP on 2016-11-12.
 */
public class Main extends JavaPlugin{
    public static File file = new File("plugins/" + "QuestGUI" + "/Quests.yml");
    public static FileConfiguration config;
    public static File datafile = new File("plugins/" + "QuestGUI" + "/QuestDatas.yml");
    public static FileConfiguration data;
    private Logger log = Logger.getLogger("");
    private final double VERSION = 1.1;
    public static String PREFIX = "§f[§a가온§f] §r";

    public void onEnable(){
        Util.plon(log,"QuestGUI",VERSION);
        Util.serverOnlineOffline();
        registerEvents(this);
        getCommand("quest").setExecutor(new QuestCommand());
        LoadMultiConfig();
    }
    public void registerEvents(Plugin p){
        getServer().getPluginManager().registerEvents(new Event(), p);
        getServer().getPluginManager().registerEvents(new MobKill(), p);
        getServer().getPluginManager().registerEvents(new DoCmd(), p);
        getServer().getPluginManager().registerEvents(new BlockBreak(), p);
        getServer().getPluginManager().registerEvents(new Eating(), p);
    }
    private void LoadMultiConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        data = YamlConfiguration.loadConfiguration(datafile);
        try {
            if (!file.exists()) {
                config.set("Quests.fq.npcname","Steve");
                List list = new ArrayList();
                list.add("Hello, I am a Steve.");
                list.add("I am a Test Quest NPC.");
                list.add("Nice to meet you.");
                config.set("Quests.fq.npctext",list);
                List lists = new ArrayList();
                lists.add("Thank you!");
                lists.add("i give item to you.");
                config.set("Quests.fq.complete-npctext",lists);
                config.set("Quests.fq.repeat",false);
                config.set("Quests.fq.Follow",false);
                config.set("Quests.fq.FollowQuest","none");
                config.set("Quests.fq.objective","killmob:Zombie:5");
                config.set("Quests.fq.reward","item:1:0:5");
                config.save(file);
            }
            config.load(file);

            if (!datafile.exists()) {
                data.save(datafile);
            }
            data.load(datafile);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }
    public static void saveconfig(){
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onDisable(){
        Util.ploff(log,"QuestGUI",VERSION);
    }
}
