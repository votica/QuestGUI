package kr.kgaons.questgui.commands;

import kr.kgaons.questgui.Main;
import kr.kgaons.questgui.quest.QGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by SkyLightQP on 2016-11-12.
 */
public class QuestCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        Player p = (Player) cs;
        if(cs instanceof Player) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("open")) {
                    if (args.length > 2) {
                        QGUI.openGUI(args[1],Bukkit.getPlayerExact(args[2]));
                    } else {
                        p.sendMessage(Main.PREFIX + "§c사용법: /quest open <name> <player>");
                    }
                }
                if(args[0].equalsIgnoreCase("reload")){
                    Main.config = YamlConfiguration.loadConfiguration(Main.file);
                    try {
                        Main.config.save(Main.file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage(Main.PREFIX + "§6리로드 완료!");
                }
            }
            else {
                p.sendMessage(Main.PREFIX + "§6/quest open <name> <player>");
                p.sendMessage(Main.PREFIX+ "§7└ <player>에게 <name> 퀘스트를 엽니다.");
                p.sendMessage(Main.PREFIX + "§6/quest reload");
                p.sendMessage(Main.PREFIX+ "§7└ 퀘스트 파일 리로드");
            }
        }
        else{
            Bukkit.getConsoleSender().sendMessage("§c[QuestGUI] 콘솔에서 실행 불가능한 명령어입니다.");
        }
        return false;
    }
}
