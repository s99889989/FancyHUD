package com.daxton.fancyhud.command;



import com.daxton.fancyhud.FancyHUD;
import com.daxton.fancyhud.task.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.daxton.fancyhud.config.FileConfig.languageConfig;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player && !sender.isOp()){
            return true;
        }
        //重新讀取設定
        if(args.length == 1) {

            if(args[0].equalsIgnoreCase("reload")){
                //重新讀取的一些程序
                Reload.execute();

                if(sender instanceof Player){
                    Player player = (Player) sender;
                    player.sendMessage(languageConfig.getString("OpMessage.Reload")+"");
                }
                FancyHUD.sendLogger(languageConfig.getString("LogMessage.Reload"));
            }

        }

        return true;
    }

}
