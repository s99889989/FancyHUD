package com.daxton.fancyhud.task;

import com.daxton.fancycore.api.fancyclient.ClientConnect;
import com.daxton.fancycore.api.fancyclient.json.JsonCtrl;
import com.daxton.fancycore.api.fancyclient.json.hub.HubJson;
import com.daxton.fancycore.api.fancyclient.json.menu_object.button.ClickButtonJson;
import com.daxton.fancycore.api.fancyclient.json.menu_object.image.ImageShowJson;
import com.daxton.fancycore.api.fancyclient.json.menu_object.item.ItemShowJson;
import com.daxton.fancycore.api.fancyclient.json.menu_object.text.TextLabelJson;
import com.daxton.fancycore.api.fancyclient.json.menu_object.textfield.TextFieldJson;
import com.daxton.fancyhud.FancyHUD;
import com.daxton.fancyhud.config.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class RunTask {

	public static BukkitRunnable bukkitRunnable;

	//定時執行任務
	public static void execute(){
		FileConfiguration config = FileConfig.config_Map.get("config.yml");
		boolean enable = config.getBoolean("enable");
		if(!enable){
			if(bukkitRunnable != null){
				if(!bukkitRunnable.isCancelled()){
					bukkitRunnable.cancel();
				}
			}
			return;
		}
		int refresh_time = config.getInt("refresh_time");
		if(refresh_time < 1){
			refresh_time = 1;
		}
		if(bukkitRunnable != null){
			bukkitRunnable.cancel();
		}

		bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(RunTask::sendHubJson);
			}
		};
		bukkitRunnable.runTaskTimer(FancyHUD.fancyHUD, 0, refresh_time); //20L *

	}

	public static void sendHubJson(Player player){
		FileConfiguration config = FileConfig.config_Map.get("hud.yml");
		HubJson hubJson = new HubJson();

		Set<String> object_key = config.getConfigurationSection("ObjectList").getKeys(false);

		for(String key : object_key){
			String objectString = config.getString("ObjectList."+key+".Object");
			String[] keys = new String[0];
			if (objectString != null) {
				keys = objectString.split("\\.");
			}
			if(keys.length != 2){
				return;
			}
			FileConfiguration object_config = FileConfig.config_Map.get("module/"+keys[0]+".yml");
			String type = object_config.getString(keys[1]+".Type");
			if(type != null){
				if(type.equalsIgnoreCase("Button")){
					ClickButtonJson clickButtonJson = new ClickButtonJson(player, config, object_config, key, keys[0], keys[1]);
					hubJson.addObject(type+key, JsonCtrl.writeJSON(clickButtonJson));
					continue;
				}
				if(type.equalsIgnoreCase("TextField")){
					TextFieldJson textFieldJson = new TextFieldJson(player, config, object_config, key, keys[0], keys[1]);
					hubJson.addObject(type+key, JsonCtrl.writeJSON(textFieldJson));
					continue;
				}
				if(type.equalsIgnoreCase("Image")){
					ImageShowJson imageShowJson = new ImageShowJson(player, config, object_config, key, keys[0], keys[1]);
					hubJson.addObject(type+key, JsonCtrl.writeJSON(imageShowJson));
					continue;
				}
				if(type.equalsIgnoreCase("Text")){
					TextLabelJson textLabelJson = new TextLabelJson(player, config, object_config, key, keys[0], keys[1]);
					hubJson.addObject(type+key, JsonCtrl.writeJSON(textLabelJson));
					continue;
				}
				if(type.equalsIgnoreCase("Item")){
					ItemShowJson imageShowJson = new ItemShowJson(player, config, object_config, key, keys[0], keys[1]);
					hubJson.addObject(type+key, JsonCtrl.writeJSON(imageShowJson));

				}
			}

		}
		ClientConnect.sendMessage(player, "ShowHUD", JsonCtrl.writeJSON(hubJson));
	}

}
