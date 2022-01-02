package com.daxton.fancyhud.task;

import com.daxton.fancyhud.FancyHUD;
import org.bukkit.scheduler.BukkitRunnable;

public class TestRun {

	   public boolean b = true;

		public void time(){
			if(b){
				b = false;
				new BukkitRunnable() {
					@Override
					public void run() {
						b = true;
					}
				}.runTaskLater(FancyHUD.fancyHUD, 30);
			}
		}


}
