package com.daxton.fancyhud;

import com.daxton.fancyhud.command.MainCommand;
import com.daxton.fancyhud.command.TabCommand;
import com.daxton.fancyhud.listener.PlayerListener;
import com.daxton.fancyhud.task.Start;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FancyHUD extends JavaPlugin {

	public static FancyHUD fancyHUD;

	@Override
	public void onEnable() {
		fancyHUD = this;
		//前置插件
		if(!DependPlugins.depend()){
			fancyHUD.setEnabled(false);
			return;
		}
		//指令
		Objects.requireNonNull(Bukkit.getPluginCommand("fancyhud")).setExecutor(new MainCommand());
		Objects.requireNonNull(Bukkit.getPluginCommand("fancyhud")).setTabCompleter(new TabCommand());
		//玩家監聽
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyHUD);
		//執行任務
		Start.execute();
	}

	@Override
	public void onDisable() {
		sendLogger("§4FancyHUD uninstall.");
	}

	public static void sendLogger(String message){
		FancyHUD.fancyHUD.getLogger().info(message);
	}

}
