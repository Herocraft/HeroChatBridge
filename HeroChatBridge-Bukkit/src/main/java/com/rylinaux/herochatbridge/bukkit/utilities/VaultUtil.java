package com.rylinaux.herochatbridge.bukkit.utilities;

import com.rylinaux.herochatbridge.bukkit.HeroChatBridgeBukkit;

import org.bukkit.Bukkit;

public class VaultUtil {

    public static String getGroup(String player) {
        return HeroChatBridgeBukkit.getChat().getPrimaryGroup(Bukkit.getWorlds().get(0), player);
    }

    public static String getGroupPrefix(String player) {
        return HeroChatBridgeBukkit.getChat().getGroupPrefix(Bukkit.getWorlds().get(0), getGroup(player));
    }

    public static String getGroupSuffix(String player) {
        return HeroChatBridgeBukkit.getChat().getGroupSuffix(Bukkit.getWorlds().get(0), getGroup(player));
    }

    public static String getPlayerName(String player) {
        return VaultUtil.getPlayerPrefix(player) + VaultUtil.getPlayerInfoColor(player) + player + VaultUtil.getPlayerSuffix(player);
    }

    public static String getPlayerPrefix(String player) {
        return HeroChatBridgeBukkit.getChat().getPlayerPrefix(Bukkit.getWorlds().get(0), player);
    }

    public static String getPlayerSuffix(String player) {
        return HeroChatBridgeBukkit.getChat().getPlayerSuffix(Bukkit.getWorlds().get(0), player);
    }

    public static String getPlayerInfoColor(String player) {
        return HeroChatBridgeBukkit.getChat().getPlayerInfoString(Bukkit.getWorlds().get(0), player, "color", "");
    }
}
