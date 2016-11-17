package kr.kgaons.questgui.utils;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebHook {
    public static List<String> getVersion(String url){
        try
        {
            URL updateurl = new URL(url);
            HttpURLConnection huc = (HttpURLConnection)updateurl.openConnection();
            huc.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String inputline;
            List<String> list = new ArrayList<String>();
            while ((inputline = br.readLine()) != null)
            {
                list.add(inputline);
            }
            return list;
        }
        catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage("§f[§b하늘빛QP 플러그인§f] §c버전 로드에 실패하였습니다.");
            Bukkit.getConsoleSender().sendMessage("§f[§b하늘빛QP 플러그인§f] §c관리자에게 문의해주세요.");
            return null;
        }
    }
}