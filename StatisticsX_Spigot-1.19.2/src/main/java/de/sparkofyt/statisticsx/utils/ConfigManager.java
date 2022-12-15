package de.sparkofyt.statisticsx.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    /* Variables */
    Plugin plugin;

    File file;
    FileConfiguration configuration;

    String fileName;
    String fileDirectory;

    /* Constructor */
    public ConfigManager(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.fileDirectory = plugin.getDataFolder().getAbsolutePath();
        this.file = new File(fileDirectory + "/" + fileName);

        init();
    }

    public ConfigManager(Plugin plugin, String directory, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.fileDirectory = directory;
        this.file = new File(fileDirectory + "/" + fileName);

        init();
    }

    /* Methods */
    private void init() {
        File directory = new File(fileDirectory);
        if(!directory.exists()) directory.mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Getters & Setters */
    public Object get(String path) { return configuration.get(path); }
    public String getString(String path) { return configuration.getString(path); }
    public int getInteger(String path) { return configuration.getInt(path); }
    public double getDouble(String path) { return configuration.getDouble(path); }
    public long getLong(String path) { return configuration.getLong(path); }
    public boolean getBoolean(String path) { return configuration.getBoolean(path); }
    public List<?> getList(String path) { return configuration.getList(path); }
    public List<String> getStringList(String path) { return configuration.getStringList(path); }
    public List<Integer> getIntegerList(String path) { return configuration.getIntegerList(path); }
    public List<Double> getDoubleList(String path) { return configuration.getDoubleList(path); }
    public List<Byte> getByteList(String path) { return configuration.getByteList(path); }
    public List<Long> getLongList(String path) { return configuration.getLongList(path); }
    public List<Boolean> getBooleanList(String path) { return configuration.getBooleanList(path); }
    public Color getColor(String path) { return configuration.getColor(path); }
    public Location getLocation(String path) { return configuration.getLocation(path); }
    public ItemStack getItemStack(String path) { return configuration.getItemStack(path); }
    public ChatColor getChatColor(String path){
        return ChatColor.valueOf(getString(path));
    }

    public Object getOrSetDefault(String path, Object defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return get(path);
    }
    public String getStringOrSetDefault(String path, String defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getString(path);
    }
    public int getIntegerOrSetDefault(String path, int defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getInteger(path);
    }
    public double getDoubleOrSetDefault(String path, double defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getDouble(path);
    }
    public long getLongOrSetDefault(String path, long defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getLong(path);
    }
    public boolean getBooleanOrSetDefault(String path, boolean defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getBoolean(path);
    }
    public Color getColorOrSetDefault(String path, Color defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getColor(path);
    }
    public Location getLocationOrSetDefault(String path, Location defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getLocation(path);
    }
    public ItemStack getItemStackOrSetDefault(String path, ItemStack defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getItemStack(path);
    }
    public ChatColor getChatColorOrSetDefault(String path, ChatColor defaultValue){
        if (!configuration.contains(path)){
            configuration.set(path, defaultValue.name());
            save();
            return defaultValue;
        }
        return ChatColor.getByChar(getString(path));
    }

    public List<?> getListOrSetDefault(String path, List<?> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getList(path);
    }
    public List<String> getStringListOrSetDefault(String path, List<String> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getStringList(path);
    }
    public List<Integer> getIntegerListOrSetDefault(String path, List<Integer> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getIntegerList(path);
    }
    public List<Double> getDoubleListOrSetDefault(String path, List<Double> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getDoubleList(path);
    }
    public List<Long> getLongListOrSetDefault(String path, List<Long> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getLongList(path);
    }
    public List<Boolean> getBooleanListOrSetDefault(String path, List<Boolean> defaultValue) {
        if(!configuration.contains(path)) {
            configuration.set(path, defaultValue);
            save();
            return defaultValue;
        }
        return getBooleanList(path);
    }

    public Plugin getPlugin() {
        return plugin;
    }
    public File getFile() {
        return file;
    }
    public FileConfiguration getConfiguration() {
        return configuration;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFileDirectory() {
        return fileDirectory;
    }
}
