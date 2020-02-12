package su.binair.security;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_7_R4.EnumChatFormat;

public class IPCheck extends JavaPlugin implements Listener
{	
    private List<String> pseudos = new ArrayList<String>();    
	
	public void onEnable()
	{
		saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents((Listener) this, this);
		pseudos = (List<String>) this.getConfig().getList("Staff.Liste");
    }

	
	@EventHandler
	public void onJoinCheck(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		String pseudo = p.getName();
        String ip = p.getAddress().getAddress().getHostAddress();

        if(pseudos.contains(pseudo))
        {
        	String goodip = this.getConfig().getString("Staff.Ip." + pseudo);
        	System.out.println("IP: " + ip + " - Bonne IP: " + goodip);
        	
        	if(ip.equals(goodip))
        	{
        		System.out.println(EnumChatFormat.YELLOW + "Authentification de + " + pseudo + " réussie. Ip: OK");
        	}
        	else
        	{
        		System.out.println(EnumChatFormat.RED + "Authentification de + " + pseudo + " échouée. Ip: Mauvaise");
        		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "banip " + ip + " Authentification échouée, Tentative de hack");
        		e.getPlayer().setBanned(true);
        		e.getPlayer().kickPlayer("Authentification échouée, Tentative de hack");
        	}
        }
	}
	
}
