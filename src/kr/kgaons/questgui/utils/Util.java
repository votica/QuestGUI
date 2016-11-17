package kr.kgaons.questgui.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.logging.Logger;

public class Util {
	// Util Class Version : v1.5 //
	// Made by SkyLightQP //
	// http://blog.kgaons.kr //

	public static void plon(Logger log, String PluginName, double PluginVer){
		log.info("[" + PluginName + "] 플러그인 활성화 v" + PluginVer);
		Bukkit.getConsoleSender().sendMessage("§f[§a하늘빛QP 플러그인§f] " + PluginName + " v" + PluginVer);
	}
	public static void ploff(Logger log, String PluginName, double PluginVer){
		log.info("[" + PluginName + "] 플러그인 비활성화 v" + PluginVer);
		Bukkit.getConsoleSender().sendMessage("§f[§c하늘빛QP 플러그인§f] " + PluginName + " v" + PluginVer);
	}
	public static void logout(Logger log, String s){
		log.info(s);
	}
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> Lore, int Loc, Inventory inventory){
		ItemStack Icon = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(Display);
		Icon_Meta.setLore(Lore);
		Icon.setItemMeta(Icon_Meta);
		inventory.setItem(Loc, Icon);
	}
	public static ItemStack getPlayerSkull(String Display,int Stack, List<String> Lore, String PlayerName) {
		ItemStack i = new ItemStack(Material.SKULL_ITEM, Stack);
		i.setDurability((short)3);
		SkullMeta meta = (SkullMeta)i.getItemMeta();
		meta.setOwner(PlayerName);
		meta.setDisplayName(Display);
		meta.setLore(Lore);
		i.setItemMeta(meta);
		return i;
	}
	public static void ItemStackStack(ItemStack Item, int Loc, Inventory inventory) {
		inventory.setItem(Loc, Item);
	}
	public static void SoundPlayer(Player player, org.bukkit.Sound sound, float volume, float pitch) {
		if(Bukkit.getOfflinePlayer(player.getName()).isOnline() == true)
			player.playSound(player.getLocation(), sound ,volume, pitch);
	}
	public static void SoundPlayerLoc(Player player, Location loc, org.bukkit.Sound sound, float volume, float pitch) {
		if(Bukkit.getOfflinePlayer(player.getName()).isOnline() == true)
			player.playSound(loc, sound ,volume, pitch);
	}
	public static void SoundLoc(Location loc, org.bukkit.Sound sound, float volume,float pitch) {
		World world = loc.getWorld();
		world.playSound(loc, sound ,volume, pitch);
	}
	public static void serverOnlineOffline(){
		if(Bukkit.getOnlineMode() == false){
			Bukkit.getConsoleSender().sendMessage("§f[§b하늘빛QP 플러그인§f] §f이 서버는 오프라인 서버입니다.");
			Bukkit.getConsoleSender().sendMessage("§f[§b하늘빛QP 플러그인§f] §f온라인으로 바꾸는 것을 권장드립니다.");
		}
	}
}