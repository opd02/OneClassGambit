package me.opd02.gambittesting;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import com.connorlinfoot.titleapi.TitleAPI;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener {
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
	}
	//BoundingBox redArena = new BoundingBox(127, 71, -29, 53, 36, 59);
	//BoundingBox blueArena = new BoundingBox(-9, 36, 66, -87, 69, -22);
	public ArrayList<Entity> frozen = new ArrayList<Entity>();
    public  int i = 10; // Task will run 10 times.
    public int high = 100;
    public Random rand = new Random();
    public static BukkitTask task = null;
    public static BukkitTask stask = null;
	public void invSet(Player p, Color color){
		PlayerInventory pi = p.getInventory();
		ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta sMeta = sword.getItemMeta();
		sMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		sMeta.setUnbreakable(true);
		sword.setItemMeta(sMeta);
		pi.setItem(0, sword);
		
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta bm = bow.getItemMeta();
		bm.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		bm.setUnbreakable(true);
		bow.setItemMeta(bm);
		pi.setItem(8, bow);
		
		ItemStack arrow = new ItemStack(Material.ARROW, 1);
		pi.setItem(17, arrow);
		
		ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);
		pi.setItem(7, steak);
		
		ItemStack axe = new ItemStack(Material.IRON_AXE, 1);
		ItemMeta am = axe.getItemMeta();
		am.setUnbreakable(true);
		am.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
		axe.setItemMeta(am);
		pi.setItem(1, axe);
		
		ItemStack hat = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta hatLM = (LeatherArmorMeta) hat.getItemMeta();
		hatLM.setColor(color);
		hatLM.setUnbreakable(true);
		hatLM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		hat.setItemMeta(hatLM);
		pi.setHelmet(hat);
		
		ItemStack ches = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta chesLM = (LeatherArmorMeta) ches.getItemMeta();
		chesLM.setColor(color);
		chesLM.setUnbreakable(true);
		chesLM.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
		ches.setItemMeta(chesLM);
		pi.setChestplate(ches);
		
		ItemStack pa = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta paLM = (LeatherArmorMeta) pa.getItemMeta();
		paLM.setColor(color);
		paLM.setUnbreakable(true);
		paLM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		pa.setItemMeta(paLM);
		pi.setLeggings(pa);
		
		ItemStack boo = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta booLM = (LeatherArmorMeta) boo.getItemMeta();
		booLM.setColor(color);
		booLM.setUnbreakable(true);
		booLM.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		boo.setItemMeta(booLM);
		pi.setBoots(boo);
		
		ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 2);
		pi.setItem(6, apple);
		
		ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 4);
		pi.setItem(5, pearl);
	}
	public void configSet(){
		getConfig().set("Teams.red", 0);
		getConfig().set("Teams.blue", 0);
		getConfig().set("Bosses.blue1", 2);
		getConfig().set("Bosses.blue2", 3);
		getConfig().set("Bosses.blue3", 1);
		getConfig().set("Bosses.red1", 2);
		getConfig().set("Bosses.red2", 3);
		getConfig().set("Bosses.red3", 1);
		getConfig().set("SpawnFreeze.red", "false");
		getConfig().set("SpawnFreeze.blue", "false");
		getConfig().set("Game.Stop", "false");
		getConfig().set("redbossout", "false");
		getConfig().set("bluebossout", "false");
		saveConfig();
	}
	public void spinItem(Player p, Location loc){
		int got = rand.nextInt(9);
		if(got==0){
			ItemStack sugar = new ItemStack(Material.SUGAR, 3);
			ItemMeta sugarmeta = sugar.getItemMeta();
			sugarmeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Team Speed Boost");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Drop this item to give");
			lore.add(ChatColor.GRAY + "your team a boost!");
			sugarmeta.setLore(lore);
			sugar.setItemMeta(sugarmeta);
			p.getWorld().dropItem(loc, sugar);
		}
		if(got==1){
			ItemStack inc = new ItemStack(Material.INK_SAC, 2);
			ItemMeta incmeta = inc.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Drop this item to");
			lore.add(ChatColor.GRAY + "blind your opponents!");
			incmeta.setLore(lore);
			incmeta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Ink Bomb");
			inc.setItemMeta(incmeta);
			p.getWorld().dropItem(loc, inc);
		}
		if(got==2){
			ItemStack dia = new ItemStack(Material.DIAMOND, 1);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Armor Upgrade Module");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Drop this item to");
			lore.add("upgrade your armor!");
			diam.setLore(lore);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==3){
			ItemStack dia = new ItemStack(Material.ANVIL, 1);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Weapon Upgrade Module");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Drop this item to");
			lore.add("upgrade your weapons!");
			diam.setLore(lore);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==4){
			ItemStack dia = new ItemStack(Material.BONE, 2);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Summon Wolf");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Drop this item to");
			lore.add(ChatColor.GRAY + "summon a wolf!");
			diam.setLore(lore);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==5){
			ItemStack dia = new ItemStack(Material.ICE, 2);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Freeze Mobs");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Drop this item to");
			lore.add(ChatColor.GRAY + "freeze nearby mobs!");
			diam.setLore(lore);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==6){
			ItemStack dia = new ItemStack(Material.MAGMA_CREAM, 1);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Hot Hand");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Attack with this to");
			lore.add("set foes a blaze!");
			diam.setLore(lore);
			diam.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==7){
			ItemStack dia = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Help of Notch");
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
		if(got==8){
			ItemStack dia = new ItemStack(Material.SCUTE, 2);
			ItemMeta diam = dia.getItemMeta();
			diam.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Turtle Shell");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Drop this item to");
			lore.add(ChatColor.GRAY + "slow your opponents!");
			diam.setLore(lore);
			dia.setItemMeta(diam);
			p.getWorld().dropItem(loc, dia);
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("token")){
			ItemStack item = new ItemStack(Material.SUNFLOWER, 64);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Spin Token");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Right click on the enderchest");
			lore.add(ChatColor.GRAY + "to get a prize!");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			Player p = (Player) sender;
			p.getInventory().addItem(item);
			p.sendMessage(ChatColor.YELLOW + "You have recieved a token!");
		}
		if(cmd.getName().equalsIgnoreCase("redspawnfreeze")){
			if(getConfig().get("SpawnFreeze.red")=="false"){
				getConfig().set("SpawnFreeze.red", "true");
				saveConfig();
				sender.sendMessage(ChatColor.RED + "Red's Spawn Freeze has been turned on!");
				return true;
			}
			getConfig().set("SpawnFreeze.red", "false");
			saveConfig();
			sender.sendMessage(ChatColor.RED + "Red's Spawn Freeze has been turned off!");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("bluespawnfreeze")){
			if(getConfig().get("SpawnFreeze.blue")=="false"){
				getConfig().set("SpawnFreeze.blue", "true");
				saveConfig();
				sender.sendMessage(ChatColor.BLUE + "Blue's Spawn Freeze has been turned on!");
				return true;
			}
			getConfig().set("SpawnFreeze.blue", "false");
			saveConfig();
			sender.sendMessage(ChatColor.BLUE + "Blue's Spawn Freeze has been turned off!");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("stopgame")){
			if(!(sender instanceof Player)){
				getConfig().set("Game.Stop", "true");
        		for(Entity e : Bukkit.getServer().getWorld("world").getEntities()){
        			if(e.getType()==EntityType.WOLF||e.getType()==EntityType.ZOMBIE||e.getType()==EntityType.PILLAGER||e.getType()==EntityType.RAVAGER||e.getType()==EntityType.WITHER||e.getType()==EntityType.VINDICATOR){
        				e.remove();
        			}
        		}
        		Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "THE GAME HAS BEEN STOPPED BY CONSOLE");
				sender.sendMessage(ChatColor.RED + "Game stopped");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "This command is for console only!");
		}
		if(cmd.getName().equalsIgnoreCase("gearup")){
			if(!(sender.getName().equalsIgnoreCase("opd02"))){
				sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
				return true;
			}
            for (Player p : Bukkit.getOnlinePlayers()){
        		Team team = p.getScoreboard().getPlayerTeam(p);
        		p.setLevel(0);
        		p.setExp((float) 1);
        		p.getInventory().clear();
        		p.setGameMode(GameMode.ADVENTURE);
        		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 2));
        		p.setHealth(20);
        		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
                if(team.getDisplayName().equalsIgnoreCase("blue")){
                	invSet(p, Color.BLUE);
                }
                if(team.getDisplayName().equalsIgnoreCase("red")){
                	invSet(p, Color.RED);
                }
            }
		}
		if(cmd.getName().equalsIgnoreCase("start")){
			if((sender instanceof Player)&&!(sender.getName().equalsIgnoreCase("opd02"))){
				sender.sendMessage(ChatColor.RED + "This command is for the Game Master only!");
				return true;
			}
			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Starting up Gambit...");
			configSet();
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 97 47 12 99 46 12 minecraft:yellow_stained_glass_pane");
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 99 46 24 97 46 24 minecraft:yellow_stained_glass_pane");
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -39 47 13 -37 46 13 minecraft:yellow_stained_glass_pane");
    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -37 46 25 -39 46 25 minecraft:yellow_stained_glass_pane");
            task = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                @Override
                public void run() {
                    if(i != 0) {
                       Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + i + "...");
                   	if(i ==1||i==2||i==3){
                   		for(Player p : Bukkit.getServer().getOnlinePlayers()){
                    		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float).5, (float).5);
                   		}
                	}
                        i--;
                    } else {
                        // If "i" is zero, we cancel the task.
                		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 97 47 12 99 46 12 minecraft:air destroy");
                		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill 99 46 24 97 46 24 minecraft:air destroy");
                		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -39 47 13 -37 46 13 minecraft:air destroy");
                		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -37 46 25 -39 46 25 minecraft:air destroy");
                   		for(Player p : Bukkit.getServer().getOnlinePlayers()){
                    		p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, (float)1, (float)1);
                   		}
                        task.cancel();
                		startSpawning();
                    }
                }
            }, 20, 20);
            for (Player p : Bukkit.getOnlinePlayers()){
        		Team team = p.getScoreboard().getPlayerTeam(p);
        		p.setLevel(0);
        		p.setExp((float) 1);
        		p.getInventory().clear();
        		p.setGameMode(GameMode.ADVENTURE);
        		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 2));
        		p.setHealth(20);
                if(team.getDisplayName().equalsIgnoreCase("blue")){
                    p.sendMessage(ChatColor.BLUE + "You are on the BLUE team!");
                    p.teleport(new Location(p.getWorld(), -37.5, 45, 19.5));
                    p.setBedSpawnLocation(new Location(p.getWorld(), -37.5, 45, 19.5), true);
                    invSet(p, Color.BLUE);
                }
                if(team.getDisplayName().equalsIgnoreCase("red")){
                    p.sendMessage(ChatColor.RED + "You are on the RED team!");
                    p.teleport(new Location(p.getWorld(), 98.5, 45, 18.5));
                    p.setBedSpawnLocation(new Location(p.getWorld(), 98.5, 45, 18.5), true);
                    invSet(p, Color.RED);
                }
            }
		}
		if(cmd.getName().equalsIgnoreCase("resetbanks")){
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "The banks' config has been reset!");
			configSet();
			return true;
		}
		return false;
	}
	public void startSpawning(){
        stask = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                if(high != 0) {
                	if(getConfig().get("Game.Stop")=="true"){
                        task.cancel();
                        return;
                	}
                	int newRan = rand.nextInt(7);
                    for (Player p : Bukkit.getOnlinePlayers()){
                    	p.playSound(p.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 1, 1);
                    }
                	if(!(getConfig().get("SpawnFreeze.red").equals("true"))){
                		if(newRan==0){
                			spawnHere(73.5, 37, -14.5);
                		}
                		if(newRan==1){
                			spawnHere(103.5, 39, -19.5);
                		}
                		if(newRan==2){
                			spawnHere(104.5, 50, -22.5);
                		}
                		if(newRan==3){
                			spawnHere(121.5, 45, 11.5);
                		}
                		if(newRan==4){
                			spawnHere(97.5, 39, 61.5);
                		}
                		if(newRan==5){
                			spawnHere(73.5, 37, 50.5);
                		}
                		if(newRan==6){
                			spawnHere(73.5, 47, 49.5);
                		}
                	}
                	if(!(getConfig().get("SpawnFreeze.blue").equals("true"))){
                		if(newRan==0){
                			spawnHere(-62.5, 47, 50.5);
                		}
                		if(newRan==1){
                			spawnHere(-62.5, 37, 51.5);
                		}
                		if(newRan==2){
                			spawnHere(-38.5, 39, 62.5);
                		}
                		if(newRan==3){
                			spawnHere(-14.5, 45, 12.5);
                		}
                		if(newRan==4){
                			spawnHere(-32.5, 39, -18.5);
                		}
                		if(newRan==5){
                			spawnHere(-31, 50, -21.5);
                		}
                		if(newRan==6){
                			spawnHere(-62.5, 37, -13.5);
                		}
                	}
                    high--;
                } else {
                    // If "i" is zero, we cancel the task.
                	Bukkit.broadcastMessage(ChatColor.BOLD + "SERVER RAN OUT OF SPAWNING REPEATS");
                    task.cancel();
                }
            }
        }, 0, 900L);
	}
	public void spawnHere(double d, double y, double e){
		World world = Bukkit.getServer().getWorld("world");
		Location loc = new Location(world, d, y, e);
		world.spawnEntity(loc, EntityType.ZOMBIE);
		world.spawnEntity(loc, EntityType.ZOMBIE);
		world.spawnEntity(loc, EntityType.ZOMBIE);
		world.spawnEntity(loc, EntityType.ZOMBIE);
		world.spawnEntity(loc, EntityType.ZOMBIE);
		world.spawnEntity(loc, EntityType.PILLAGER);
	}
	@EventHandler	
	public void onMobDie(EntityDeathEvent e){
		Entity en = e.getEntity();
		Location l = en.getLocation();
		if(e.getEntityType().equals(EntityType.RAVAGER)||e.getEntityType().equals(EntityType.EVOKER)){
			ItemStack item = new ItemStack(Material.SUNFLOWER, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Spin Token");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Right click on the enderchest");
			lore.add(ChatColor.GRAY + "to get a prize!");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			en.getWorld().dropItemNaturally(l, item);
			Player p = e.getEntity().getKiller();
			String team = p.getScoreboard().getPlayerTeam(p).getDisplayName();
			if(team.contains("ed")){
				if(en.getType()==EntityType.EVOKER){
						int i = getConfig().getInt("Bosses.red1");
						getConfig().set("Bosses.red1", i-1);
						saveConfig();
						if(i-1==0){
							getConfig().set("SpawnFreeze.red", "false");
							getConfig().set("redbossout", "false");
							saveConfig();
						}
						return;
				}
				if(en.getType()==EntityType.RAVAGER){
						int i = getConfig().getInt("Bosses.red2");
						getConfig().set("Bosses.red2", i-1);
						saveConfig();
						if(i-1==0){
							getConfig().set("SpawnFreeze.red", "false");
							getConfig().set("redbossout", "false");
							saveConfig();
						}
						return;
				}
			}
			else if(team.contains("lue")){
				if(en.getType()==EntityType.EVOKER){
						int i = getConfig().getInt("Bosses.blue1");
						getConfig().set("Bosses.blue1", i-1);
						saveConfig();
						if(i-1==0){
							getConfig().set("SpawnFreeze.blue", "false");
							getConfig().set("bluebossout", "false");
							saveConfig();
						}
						return;
				}
				if(en.getType()==EntityType.RAVAGER){
						int i = getConfig().getInt("Bosses.blue2");
						getConfig().set("Bosses.blue2", i-1);
						saveConfig();
						if(i-1==0){
							getConfig().set("SpawnFreeze.blue", "false");
							getConfig().set("bluebossout", "false");
							saveConfig();
						}
						return;
				}
			}
			return;
		}
		else if(e.getEntityType().equals(EntityType.ZOMBIE)||e.getEntityType().equals(EntityType.PILLAGER)){
			ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Orb");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("Bank these orbs in");
			lore.add("your command tower!");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			en.getWorld().dropItemNaturally(l, item);
			return;
		}
		else if(e.getEntityType().equals(EntityType.WITHER)){
			ItemStack item = new ItemStack(Material.SUNFLOWER, 3);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Spin Token");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Right click on the enderchest");
			lore.add(ChatColor.GRAY + "to get a prize!");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			en.getWorld().dropItemNaturally(l, item);
			Player p = e.getEntity().getKiller();
			String team = p.getScoreboard().getPlayerTeam(p).getDisplayName();
			if(team.contains("lue")){
				getConfig().set("SpawnFreeze.blue", "true");
				getConfig().set("bluebossout", "true");
				saveConfig();
	    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone -78 82 23 -78 87 16 -78 63 16");
	    		Bukkit.getServer().broadcastMessage("�9The BLUE team has open their portal! Watch out �cRED �9team!");
				return;
			}
				getConfig().set("SpawnFreeze.red", "true");
				getConfig().set("redbossout", "true");
				saveConfig();
	    		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clone 58 83 22 58 88 15 58 63 15");
	    		Bukkit.getServer().broadcastMessage("�cThe RED team has open their portal! Watch out �9BLUE �cteam!");
			return;
		}

	}
	@EventHandler
	public void onItemPickUp(PlayerPickupItemEvent e){
		Item i = e.getItem();
		int in = i.getItemStack().getAmount();
		if(!(i.getItemStack().getType()==Material.NETHER_STAR)){
			return;
		}
		Player p = e.getPlayer();
		if(p.getLevel()==15){
			p.sendMessage(ChatColor.RED + "You are already carrying the maximum number of motes!");
			i.setPickupDelay(20 * 10);
			e.setCancelled(true);
			return;
		}
		p.setExp((float) 1);
		p.giveExpLevels(in);
		p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		p.spawnParticle(Particle.CLOUD, p.getLocation(), 100);
	}
	@EventHandler
	public void onBowShoot(EntityShootBowEvent e){
		if(frozen.contains(e.getEntity())){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(e.getItemDrop().getName().contains("Axe")||e.getItemDrop().getName().contains("Sword")&&!(p.getGameMode()==GameMode.CREATIVE)){
			p.sendMessage(ChatColor.RED + "You can not this drop item in the arena!");
			e.setCancelled(true);
			return;
		}
		if(e.getItemDrop().getItemStack().getType().equals(Material.NETHER_STAR)&&!(p.getGameMode()==GameMode.CREATIVE)){
			p.sendMessage(ChatColor.RED + "You can not this drop item in the arena!");
			e.setCancelled(true);
			return;
		}
		ItemStack dropped = (ItemStack) e.getItemDrop().getItemStack();
		if(dropped.getItemMeta().getDisplayName().contains("Speed")){
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used a team speed boost!");
            for (Player pl : Bukkit.getOnlinePlayers()){
        		Team team = pl.getScoreboard().getPlayerTeam(pl);
        		if(team.getColor()==p.getScoreboard().getPlayerTeam(p).getColor()){
               		pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1, false));
               		pl.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1, 1);
        		}
    			e.getItemDrop().remove();
    			continue;
            }
           }
		if(dropped.getItemMeta().getDisplayName().contains("Ink")){
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used an ink bomb!");
            for (Player pl : Bukkit.getOnlinePlayers()){
        		Team team = pl.getScoreboard().getPlayerTeam(pl);	
        		if(!(team.getColor()==p.getScoreboard().getPlayerTeam(p).getColor())){
               		pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 160, 2, false));
               		pl.playSound(pl.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 1, 1);
            		}
    			e.getItemDrop().remove();
    			continue;
            	}
		}
		if(dropped.getItemMeta().getDisplayName().contains("Freeze")){
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used a freeze bomb!");
			p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 1);
	        for (Entity en : p.getNearbyEntities(15, 15, 15)){
	            if((en instanceof LivingEntity)&&!(en instanceof Player)){
		            frozen.add(en);
		            LivingEntity le = (LivingEntity) en;
	           		le.getEquipment().setHelmet(new ItemStack(Material.ICE, 1));
	           		le.setAI(false);
	           		this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	           		  public void run() {
	           		      frozen.remove(en);
	           		      le.getEquipment().setHelmet(new ItemStack(Material.AIR));
	           		      p.getWorld().playSound(le.getLocation(), Sound.BLOCK_GLASS_PLACE, 1, 1);
	           		      le.setAI(true);
	           		  }
	           		}, 200L);
	            }
	        }
			e.getItemDrop().remove();
			return;
		}
		if(dropped.getItemMeta().getDisplayName().contains("Turtle")){
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used tutrle shell!");
            for (Player pl : Bukkit.getOnlinePlayers()){
        		Team team = pl.getScoreboard().getPlayerTeam(pl);	
        		if(!(team.getColor()==p.getScoreboard().getPlayerTeam(p).getColor())){
               		pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 3, false));
               		pl.playSound(pl.getLocation(), Sound.ENTITY_SLIME_JUMP, (float)0.5, (float)0.5);
            		}
    			e.getItemDrop().remove();
    			continue;
            	}
		}
		if(dropped.getItemMeta().getDisplayName().contains("Armor")){
			PlayerInventory pi = p.getInventory();
			if(pi.getChestplate().getType()==Material.DIAMOND_CHESTPLATE){
				p.sendMessage(ChatColor.RED + "Your armor is already maxed out!");
				return;
			}
			if(pi.getChestplate().getType()==Material.LEATHER_CHESTPLATE){
				Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used an armor upgrade!");
				pi.getHelmet().setType(Material.IRON_HELMET);
				pi.getChestplate().setType(Material.IRON_CHESTPLATE);
				pi.getLeggings().setType(Material.IRON_LEGGINGS);
				pi.getBoots().setType(Material.IRON_BOOTS);
           		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
    			e.getItemDrop().remove();
    			return;
			}
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used an armor upgrade!");
			pi.getHelmet().setType(Material.DIAMOND_HELMET);
			pi.getChestplate().setType(Material.DIAMOND_CHESTPLATE);
			pi.getLeggings().setType(Material.DIAMOND_LEGGINGS);
			pi.getBoots().setType(Material.DIAMOND_BOOTS);
       		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
			e.getItemDrop().remove();
			return;
		}
		if(dropped.getItemMeta().getDisplayName().contains("Weapon")){
			if(p.getInventory().contains(Material.DIAMOND_AXE)){
				p.sendMessage(ChatColor.RED + "Your weapons are already maxed out!");
				return;
			}
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has used a weapon upgrade!");
			e.getItemDrop().remove();
       		p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
			for(ItemStack i : p.getInventory().getContents()){
				if(i==null){
					continue;
				}
				if(i.getType()==Material.IRON_AXE){
					i.setType(Material.DIAMOND_AXE);
				}
				if(i.getType()==Material.BOW){
					i.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
				}
				if(i.getType()==Material.IRON_SWORD){
					i.setType(Material.DIAMOND_SWORD);
				}
			}
		}
		if(dropped.getItemMeta().getDisplayName().contains("Wolf")){
			Bukkit.getServer().broadcastMessage(p.getName() + "" + ChatColor.GOLD + " has summoned a wolf!");
			  Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
		        wolf.setAdult();
		        wolf.setTamed(true);
		        wolf.setOwner(p);
		        wolf.setBreed(false);
		        wolf.setCustomName(ChatColor.YELLOW + p.getName() + "'s Wolf");
		        wolf.setCustomNameVisible(true);
		        wolf.setCollarColor(DyeColor.RED);
		        wolf.setHealth(wolf.getMaxHealth());
		        wolf.setInvulnerable(true);
				e.getItemDrop().remove();
				return;
		}
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		PlayerInventory pi = p.getInventory();
		pi.remove(Material.NETHER_STAR);
		p.sendMessage("�cYou have died and lost all the orbs you were carrying!");
		p.setLevel(0);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			  public void run() {
	        		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 2));
			  }
			}, 2L);
		return;
	}
	@EventHandler
	public void onOpenEnderchest(PlayerInteractEvent e){
		Block b = e.getClickedBlock();
	if(!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
		return;
	}
		if(!(e.getClickedBlock().getType().equals(Material.ENDER_CHEST))){
			return;
		}
		Player p = (Player) e.getPlayer();
		if(p.getInventory().getItemInMainHand().getType()==Material.AIR){
			e.setCancelled(true);
			return;
		}
		if(!(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Spin"))){
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "That is not a spin token!");
			p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, (float) 0.5, (float) 0.5);
			e.setCancelled(true);
			return;
		}
		int num = p.getInventory().getItemInMainHand().getAmount() - 1;
		p.getInventory().getItemInMainHand().setAmount(num);
		Location fwl = new Location(p.getWorld(), b.getX() + 0.5, b.getY() + 1, b.getZ() + 0.5);
		Firework fw = (Firework) p.getWorld().spawn(fwl, Firework.class);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.setPower(0);
		fm.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).withFade(Color.RED).with(Type.BALL).build());
		fw.setFireworkMeta(fm);
		fw.detonate();
		p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, (float) 1, (float) 1);
		spinItem(p, fwl);
		e.setCancelled(true);
	}
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getCause().equals(DamageCause.ENTITY_EXPLOSION)){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onButtonPress(PlayerInteractEvent e){
		Player p = e.getPlayer();
			Block b = e.getClickedBlock();
		if(!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
			return;
		}
		if(!(b.getType()==Material.STONE_BUTTON)){
			return;
		}
		int levels = p.getLevel();
		if(levels==0){
			p.sendMessage(ChatColor.RED + "You have nothing to bank!");
			p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_INFECT, 10, (float)1);
			return;
		}
		Team team = p.getScoreboard().getPlayerTeam(p);	
		if(team==null){
			p.sendMessage(ChatColor.BOLD + "You are not on a team silly!");
			return;
		}
		if(team.getDisplayName().equalsIgnoreCase("red")&&getConfig().get("redbossout")=="true"){
			p.sendMessage(ChatColor.RED + "You cannnot bank while there is a boss out!");
			return;
		}
		if(team.getDisplayName().equalsIgnoreCase("blue")&&getConfig().get("bluebossout")=="true"){
			p.sendMessage(ChatColor.RED + "You cannnot bank while there is a boss out!");
			return;
		}
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
		p.getInventory().remove(Material.NETHER_STAR);
		int spingive = levels / 5;
		if(spingive >= 1){
			ItemStack item = new ItemStack(Material.SUNFLOWER, spingive);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Spin Token");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Right click on the enderchest");
			lore.add(ChatColor.GRAY + "to get a prize!");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			p.getInventory().addItem(item);
			p.sendMessage(ChatColor.YELLOW + "You received �b" + Integer.toString(spingive) + " �espin token(s)!");
		}
		int redbanked = getConfig().getInt("Teams.red") + levels;
		int bluebanked = getConfig().getInt("Teams.blue") + levels;
		
		if(team.getDisplayName().equalsIgnoreCase("red")){
			getConfig().set("Teams.red", redbanked);
			saveConfig();
			TitleAPI.sendTitle(p, 10, 45, 20, "�6�l" + getConfig().getInt("Teams.red") + " �corbs now in the bank");
			p.sendMessage("�aYou have successfully banked �e�l" + p.getLevel() + " orbs�r�a!");
			Location loc1 = new Location(p.getWorld(), 101, 39, 0);
			Location loc2 = new Location(p.getWorld(), 101, 39, 36);
			if(redbanked>=25&&getConfig().getInt("Bosses.red1")==2){
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Red team has spawned in Boss 1!");
				p.getWorld().playSound(loc1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc1, EntityType.EVOKER);
				entity.setRemoveWhenFarAway(false);
				LivingEntity entity2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc2, EntityType.EVOKER);
				entity2.setRemoveWhenFarAway(false);
				getConfig().set("SpawnFreeze.red", "true");
				getConfig().set("redbossout", "true");
				saveConfig();
				p.setLevel(0);
				return;
			}
			if(redbanked>=35&&getConfig().getInt("Bosses.red2")==3){
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Red team has spawned in Boss 2!");
				Location loc3 = new Location(p.getWorld(), 75, 37, 18);
				p.getWorld().playSound(loc1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc1, EntityType.RAVAGER);
				entity.setRemoveWhenFarAway(false);
				LivingEntity entity2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc2, EntityType.RAVAGER);
				entity2.setRemoveWhenFarAway(false);
				LivingEntity entity3 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc3, EntityType.RAVAGER);
				entity3.setRemoveWhenFarAway(false);
				getConfig().set("SpawnFreeze.red", "true");
				getConfig().set("redbossout", "true");
				saveConfig();
				p.setLevel(0);
				return;
			}
			if(redbanked>=50&&getConfig().getInt("Bosses.red3")==1){
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Red team has spawned in Boss 3!");
				Location loc1b = new Location(p.getWorld(), 101, 59, 18);
				p.getWorld().playSound(loc1b, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				Wither wither = (Wither) Bukkit.getWorld("world").spawnEntity(loc1b, EntityType.WITHER);
				wither.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + "RED BOSS");
				wither.setRemoveWhenFarAway(false);
				wither.getBossBar().setColor(BarColor.RED);
				getConfig().set("SpawnFreeze.red", "true");
				getConfig().set("redbossout", "true");
				saveConfig();
				p.setLevel(0);
			}
			p.setLevel(0);
		}
		if(team.getDisplayName().equalsIgnoreCase("blue")){
			getConfig().set("Teams.blue", bluebanked);
			saveConfig();
			TitleAPI.sendTitle(p, 10, 45, 20, "�6" + getConfig().getInt("Teams.blue") + " �9orbs now in the bank");
			p.sendMessage("�a�lYou have successfully banked �e�l" + p.getLevel() + "�l orbs�r�a!");
			if(bluebanked>=25&&getConfig().getInt("Bosses.blue1")==2){
				Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue team has spawned in Boss 1!");
				Location loc1 = new Location(p.getWorld(), -45, 39, 39);
				Location loc2 = new Location(p.getWorld(), -41, 39, 1);
				p.getWorld().playSound(loc1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc1, EntityType.EVOKER);
				entity.setRemoveWhenFarAway(false);
				LivingEntity entity2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc2, EntityType.EVOKER);
				entity2.setRemoveWhenFarAway(false);
				getConfig().set("SpawnFreeze.blue", "true");
				getConfig().set("bluebossout", "true");
				saveConfig();
				p.setLevel(0);
				return;
			}
			if(bluebanked>=35&&getConfig().getInt("Bosses.blue2")==3){
				Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue team has spawned in Boss 2!");
				Location loc1 = new Location(p.getWorld(), -45, 39, 39);
				Location loc2 = new Location(p.getWorld(), -41, 39, 1);
				Location loc3 = new Location(p.getWorld(), -60, 37, 20);
				p.getWorld().playSound(loc1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc1, EntityType.RAVAGER);
				entity.setRemoveWhenFarAway(false);
				LivingEntity entity2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc2, EntityType.RAVAGER);
				entity2.setRemoveWhenFarAway(false);
				LivingEntity entity3 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc3, EntityType.RAVAGER);
				entity3.setRemoveWhenFarAway(false);
				getConfig().set("SpawnFreeze.blue", "true");
				getConfig().set("bluebossout", "true");
				saveConfig();
				p.setLevel(0);
				return;
			}
			if(bluebanked>=50&&getConfig().getInt("Bosses.blue3")==1){
				Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue team has spawned in Boss 3!");
				Location loc1 = new Location(p.getWorld(), -35, 59, 19);
				p.getWorld().playSound(loc1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 10);
				Wither wither = (Wither) Bukkit.getWorld("world").spawnEntity(loc1, EntityType.WITHER);
				wither.setCustomName(ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE BOSS");
				wither.setRemoveWhenFarAway(false);
				wither.getBossBar().setColor(BarColor.BLUE);
				getConfig().set("SpawnFreeze.blue", "true");
				getConfig().set("bluebossout", "true");
				saveConfig();
				p.setLevel(0);
			}
		}
		p.setLevel(0);
	}
}