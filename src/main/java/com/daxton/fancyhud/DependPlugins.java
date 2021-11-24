package com.daxton.fancyhud;

import com.daxton.fancyhud.config.FileConfig;
import org.bukkit.Bukkit;

import static com.daxton.fancyhud.config.FileConfig.languageConfig;

public class DependPlugins {

    public static boolean depend(){

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            //設定檔
            FileConfig.execute();
            FancyHUD.sendLogger(languageConfig.getString("LogMessage.LoadFancyCore"));
        }else {
            FancyHUD.sendLogger("*** FancyCore is not installed or not enabled. ***");
            FancyHUD.sendLogger("*** FancyCrafting will be disabled. ***");
            return false;
        }

        return true;
    }

}
