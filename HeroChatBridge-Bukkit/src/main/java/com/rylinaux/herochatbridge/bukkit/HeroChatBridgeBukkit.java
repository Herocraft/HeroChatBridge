package com.rylinaux.herochatbridge.bukkit;

import net.milkbowl.vault.chat.Chat;

import com.rylinaux.herochatbridge.bukkit.listeners.HeroChatListener;
import com.rylinaux.herochatbridge.bukkit.listeners.HeroChatPluginMessageListener;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroChatBridgeBukkit extends JavaPlugin {

    public static final String CHANNEL = "HeroChatBridge";

    private static Chat chat = null;

    private String serverKey = null;

    private List<String> channels = null;

    private List<String> ignored = null;

    public  HeroChatListener hcl;

    private ServicesManager sm;

    @Override
    public void onEnable() {

        if (!initChat()) {
            this.getServer().getLogger().log(Level.SEVERE, "Vault not installed, disabling.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        initConfig();
        hookAPI();

        hcl = new HeroChatListener(this);
        this.getServer().getPluginManager().registerEvents(hcl, this);

        this.getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL, new HeroChatPluginMessageListener(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL);

    }

    private boolean initChat() {
        RegisteredServiceProvider<Chat> chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null)
            chat = chatProvider.getProvider();
        return (chat != null);
    }

    private void initConfig() {
        this.getConfig().options().copyDefaults(true);
        serverKey = this.getConfig().getString("key");
        channels = this.getConfig().getStringList("channels");
        ignored = this.getConfig().getStringList("ignored");
        this.saveConfig();
    }

    public boolean isIgnored(String channel) {
        return ignored.contains(channel);
    }

    public boolean isValidChannel(String channel) {
        return channels.contains(channel);
    }

    public static Chat getChat() {
        return chat;
    }

    public String getServerKey() {
        return serverKey;
    }

    public List<String> getChannels() {
        return channels;
    }

    public List<String> getIgnored() {
        return ignored;
    }

    public void sendJsonMessage(String channel, String player, String json , String world) {
        hcl.transmit(channel, player, json, world, true) ;
    }

    private void hookAPI () {
        try {

            this.getServer().getServicesManager().register(HeroChatBridgeBukkit.class, this, this, ServicePriority.Normal);

        } catch (Exception e) {
            this.getLogger().info("[API Connect] There was an error hooking HeroChatBridgeBukkit - check to make sure you're using a compatible version!");
        }
    }
}
