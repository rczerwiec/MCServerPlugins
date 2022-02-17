package stylowy.creativecontrol;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;
import stylowy.creativecontrol.logs.Log_BuilderTaken;
import stylowy.creativecontrol.logs.Log_ItemsTaken;
import stylowy.creativecontrol.logs.Log_SkeletonKilled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckCreativeEvent implements Listener {

    CreativeControl plugin = CreativeControl.GetInstance();

    @EventHandler
    public void OnGameModeChange(PlayerGameModeChangeEvent e){
        Player p = e.getPlayer();
        p.getInventory();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            if(p.hasPermission("creativecontrol.isbuilder") && plugin.getConfig().getBoolean("check_builders")){


                String Tableformat = "|%1$-26s|%2$-26s|%3$-26s|%4$-26s|%5$-26s|";
                Log_BuilderTaken log = new Log_BuilderTaken();
                log.logToFile("");
                String sum =  "[" + format.format(now) + "]" + e.getPlayer() + " changed Gamemode for " + e.getNewGameMode().name();
                log.logToFile(sum);
                sum="HIS STARTING INVENTORY:";
                log.logToFile(sum);
                int counter = 0;
                List<String> eqSlots = new ArrayList<>();
                for(ItemStack i : p.getInventory().getContents()){
                    final boolean b = counter == 4 || counter == 8 || counter == 13 || counter == 17 || counter == 22 || counter == 26 || counter == 31 || counter == 35 || counter == 40;
                    if(i==null){
                        eqSlots.add("S"+counter +": ");
                        //sum = sum + "S"+counter +": ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    else {
                        eqSlots.add("S"+counter +":"+ i.getType() +" x"+ i.getAmount());
                        //sum = sum + "S"+counter +":"+ i.getType() +" x"+ i.getAmount()+" | ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    counter++;
                }


            }
            else{


                String Tableformat = "|%1$-26s|%2$-26s|%3$-26s|%4$-26s|%5$-26s|";
                Log_ItemsTaken log = new Log_ItemsTaken();
                log.logToFile("");
                String sum =  "[" + format.format(now) + "]" + e.getPlayer() + " changed Gamemode for " + e.getNewGameMode().name();
                log.logToFile(sum);
                sum="HIS STARTING INVENTORY:";
                log.logToFile(sum);
                int counter = 0;
                List<String> eqSlots = new ArrayList<>();
                for(ItemStack i : p.getInventory().getContents()){
                final boolean b = counter == 4 || counter == 8 || counter == 13 || counter == 17 || counter == 22 || counter == 26 || counter == 31 || counter == 35 || counter == 40;
                if(i==null){
                    eqSlots.add("S"+counter +": ");
                    //sum = sum + "S"+counter +": ";
                    if(b){
                        if(eqSlots.size() == 4){
                            sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                        }
                        else{
                            sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                        }
                        log.logToFile(sum);
                        //sum = "";
                        eqSlots.clear();
                    }
                }
                else {
                    eqSlots.add("S"+counter +":"+ i.getType() +" x"+ i.getAmount());
                    //sum = sum + "S"+counter +":"+ i.getType() +" x"+ i.getAmount()+" | ";
                    if(b){
                        if(eqSlots.size() == 4){
                            sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                        }
                        else{
                            sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                        }
                        log.logToFile(sum);
                        //sum = "";
                        eqSlots.clear();
                    }
                }
                counter++;
            }

            }


    }

    @EventHandler
    public void CheckEQ(InventoryCreativeEvent e){
        Material item = e.getCursor().getType();

        int ilosc = e.getCursor().getAmount();
        String itemname = item.toString();
        String name = e.getWhoClicked().getName();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(item != Material.AIR) {
            Player p = (Player)e.getWhoClicked();
            if(p.hasPermission("creativecontrol.isbuilder") && plugin.getConfig().getBoolean("check_builders")){


                String Tableformat = "|%1$-26s|%2$-26s|%3$-26s|%4$-26s|%5$-26s|";
                Log_BuilderTaken log = new Log_BuilderTaken();
                log.logToFile("");
                String sum =  "[" + format.format(now) + "]" + name + " moved " + itemname + " x "+ilosc+" on CREATIVE mode to SLOT:"+e.getSlot();
                log.logToFile(sum);
                sum="HIS INVENTORY AFTER THAT:";
                log.logToFile(sum);
                int counter = 0;
                List<String> eqSlots = new ArrayList<>();
                for(ItemStack i : p.getInventory().getContents()){
                    final boolean b = counter == 4 || counter == 8 || counter == 13 || counter == 17 || counter == 22 || counter == 26 || counter == 31 || counter == 35 || counter == 40;
                    if(i==null){
                        eqSlots.add("S"+counter +": ");
                        //sum = sum + "S"+counter +": ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    else {
                        eqSlots.add("S"+counter +":"+ i.getType() +" x"+ i.getAmount());
                        //sum = sum + "S"+counter +":"+ i.getType() +" x"+ i.getAmount()+" | ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    counter++;
                }
            }
            else{
                //Bukkit.broadcastMessage("SLOT"+e.getSlot());
                String Tableformat = "|%1$-26s|%2$-26s|%3$-26s|%4$-26s|%5$-26s|";
                Log_ItemsTaken log = new Log_ItemsTaken();
                log.logToFile("");
                String sum =  "[" + format.format(now) + "]" + name + " moved " + itemname + " x "+ilosc+" on CREATIVE mode to SLOT:"+e.getSlot();
                log.logToFile(sum);
                sum="HIS INVENTORY AFTER THAT:";
                log.logToFile(sum);
                int counter = 0;
                List<String> eqSlots = new ArrayList<>();
                for(ItemStack i : p.getInventory().getContents()){
                    final boolean b = counter == 4 || counter == 8 || counter == 13 || counter == 17 || counter == 22 || counter == 26 || counter == 31 || counter == 35 || counter == 40;
                    if(i==null){
                        if(counter == e.getSlot()){
                            eqSlots.add("S"+counter +": XXXXXXXXXXX");
                        }
                        else{
                            eqSlots.add("S"+counter +": ");
                        }

                        //sum = sum + "S"+counter +": ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    else {
                        if(counter == e.getSlot()){
                            eqSlots.add("S"+counter +": XXXXXXXXXXX");
                        }
                        else{
                            eqSlots.add("S"+counter +":"+ i.getType() +" x"+ i.getAmount());
                        }
                        //sum = sum + "S"+counter +":"+ i.getType() +" x"+ i.getAmount()+" | ";
                        if(b){
                            if(eqSlots.size() == 4){
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),"");
                            }
                            else{
                                sum = String.format(Tableformat,eqSlots.get(0),eqSlots.get(1),eqSlots.get(2),eqSlots.get(3),eqSlots.get(4));
                            }
                            log.logToFile(sum);
                            //sum = "";
                            eqSlots.clear();
                        }
                    }
                    counter++;
                }
            }

        }
    }


    @EventHandler
    public void CheckKilledSkeletons(EntityDeathEvent e){
        if(plugin.getConfig().getBoolean("check_killedSkeletons")){
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(e.getEntityType().equals(EntityType.WITHER_SKELETON)){
            Log_SkeletonKilled log = new Log_SkeletonKilled();
            Player p = (Player)e.getEntity().getKiller();
            assert p != null;
            String sum =  "[" + format.format(now) + "]"+p.getName()+plugin.getConfig().getString("witherskeleton_killed");
            log.LogThis(sum);
        }
     }
    }
}

