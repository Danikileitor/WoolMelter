package dnk.woolmelter;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
//	Updater
		private PluginDescriptionFile desc = getDescription();
		private static final int ID = 364774;
		private static Updater updater;
		public static boolean update = false;

		private boolean checkUpdate() {
			updater = new Updater(this, ID, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;

			return update;
		}

//  Los Arrays
		String[] lana = new String[]{"WHITE_WOOL", "ORANGE_WOOL", "MAGENTA_WOOL", "LIGHT_BLUE_WOOL", "YELLOW_WOOL", "LIME_WOOL", "PINK_WOOL", "GRAY_WOOL", "LIGHT_GRAY_WOOL", "CYAN_WOOL", "PURPLE_WOOL", "BLUE_WOOL", "BROWN_WOOL", "GREEN_WOOL", "RED_WOOL", "BLACK_WOOL"};
		String[] tinte = new String[]{"WHITE_DYE", "ORANGE_DYE", "MAGENTA_DYE", "LIGHT_BLUE_DYE", "YELLOW_DYE", "LIME_DYE", "PINK_DYE", "GRAY_DYE", "LIGHT_GRAY_DYE", "CYAN_DYE", "PURPLE_DYE", "BLUE_DYE", "BROWN_DYE", "GREEN_DYE", "RED_DYE", "BLACK_DYE"};
	
    @Override
    public void onEnable() {
    	Logger log = Bukkit.getLogger();
    	log.info("[WM] Enjoy melting wool!");
    	
		if (checkUpdate()) {
			getServer().getConsoleSender()
			.sendMessage("§WMFR] An update is available, usewmfrupdate to update to the lastest version (from v"
					+ desc.getVersion() + " to v" + updater.getRemoteVersion() + ")");
		}

    for(int i = 0; i<16; i++){
    	getServer().addRecipe(new FurnaceRecipe(new NamespacedKey (this, lana[i]), new ItemStack(Material.getMaterial(tinte[i])), Material.getMaterial(lana[i]), 5.00f, 200));    		
    }
   }

    @Override
    public void onDisable() {
    	Logger log = Bukkit.getLogger();
    	log.info("[WM] :(");
    	getServer().clearRecipes();
    	getServer().resetRecipes();
    }
    
    //Para los permisos
	@EventHandler
	public void OnInteraction(PlayerInteractEvent e) {
   	Player p = e.getPlayer();
   	Material m = p.getInventory().getItemInMainHand().getType();
    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.CAMPFIRE)
    		if (!p.hasPermission("cfr.bonus"))
    			if(m.equals(Material.GUNPOWDER) || m.equals(Material.WHEAT_SEEDS) || m.equals(Material.STONE_SWORD) || m.equals(Material.CACTUS) || m.equals(Material.PUFFERFISH))
    				e.setCancelled(true);
    }
	   
   @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if (command.getName().equalsIgnoreCase("wmupdate")) {
        	if (sender.hasPermission("wm.update")) {
        		if (checkUpdate()) {
        			sender.sendMessage("§b[WM] Updating WoolMelter...");
					updater = new Updater(this, ID, this.getFile(), Updater.UpdateType.DEFAULT, true);
					updater.getResult();
					sender.sendMessage("§b[WM] Use §e/reload §bto apply changes.");
        		} else {
					sender.sendMessage("§b[WM] This plugin is already up to date.");
				}
        		
        	} else {sender.sendMessage("§4You don't have permission to use this command.");}
            return true;
        }
        return false;
    }

    
}