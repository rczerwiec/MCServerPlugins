package banknotysm.configs;

import banknotysm.BanknotySM;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static final String NO_PERM_KEY = "locale.noperm";
    public static final String INVALID_PLAYER_KEY = "locale.invalidplayer";
    public static final String ONLY_PLAYER_KEY = "locale.onlyplayers";
    public static final String GIVE_KEY = "locale.givebanknot";
    public static final String SELL_KEY = "locale.sellbanknot";
    public static final String BUY_KEY = "locale.buybanknot";
    public static final String RELOAD_KEY = "locale.reload";
    public static final String NO_IN_HAND_KEY = "locale.noinhand";
    public static final String NO_MONEY_KEY = "locale.nomoney";

    private String noPermMessage, invalidPlayerMessage, onlyPlayersMessage, reloadMessage, sellBanknotMessage,noMoneyMessage, buyBanknotMessage, noBanknotInHandMessage;

    public Config(){
        setDefaults();
        loadConfig();
    }

    public void loadConfig(){
        FileConfiguration config = BanknotySM.getInstance().getConfig();

        noPermMessage = config.getString(NO_PERM_KEY,"&4Nie masz do tego permisji");
        invalidPlayerMessage = config.getString(INVALID_PLAYER_KEY,"&4To nie jest prawidłowy gracz!");
        onlyPlayersMessage = config.getString(ONLY_PLAYER_KEY,"Tylko gracz moze uzyc tej komendy");
        sellBanknotMessage = config.getString(SELL_KEY,"&2%PLAYER% sprzedal banknot");
        buyBanknotMessage = config.getString(BUY_KEY,"&2%PLAYER% kupil banknot");
        reloadMessage = config.getString(RELOAD_KEY,"&2[Config Banknotow Zreloadowany!");
        noBanknotInHandMessage = config.getString(NO_IN_HAND_KEY,"&2Musisz trzymać banknot w dłoni!");
        noMoneyMessage = config.getString(NO_MONEY_KEY,"&2Nie masz wystarczająco gotówki!");
    }

    public void setDefaults(){
        FileConfiguration config = BanknotySM.getInstance().getConfig();

        config.addDefault(NO_PERM_KEY,"&4Nie masz do tego permisji");
        config.addDefault(INVALID_PLAYER_KEY,"&4To nie jest prawidłowy gracz!");
        config.addDefault(ONLY_PLAYER_KEY,"Tylko gracz moze uzyc tej komendy");
        config.addDefault(RELOAD_KEY,"&2[Config Banknotow Zreloadowany!");
        config.addDefault(SELL_KEY,"Sprzedales banknot! Twoje konto zostało doładowane!");
        config.addDefault(BUY_KEY,"&2%PLAYER% kupil banknot");
        config.addDefault(NO_IN_HAND_KEY,"&2Musisz trzymać banknot w dłoni!");
        config.addDefault(NO_MONEY_KEY,"&2Nie masz wystarczająco gotówki!");

        config.options().copyDefaults(true);
        BanknotySM.getInstance().saveConfig();
    }

    public void saveConfig(){
        FileConfiguration config = BanknotySM.getInstance().getConfig();

        config.set(NO_PERM_KEY,noPermMessage);
        config.set(INVALID_PLAYER_KEY,invalidPlayerMessage);
        config.set(ONLY_PLAYER_KEY,onlyPlayersMessage);
        config.set(RELOAD_KEY,reloadMessage);
        config.set(BUY_KEY,buyBanknotMessage);
        config.set(SELL_KEY,sellBanknotMessage);
        config.set(NO_IN_HAND_KEY,noBanknotInHandMessage);
        config.set(NO_MONEY_KEY,noMoneyMessage);

        BanknotySM.getInstance().saveConfig();
    }

    public void reloadConfig(){
        loadConfig();
    }


    public void setNoPermMessage(String message){
        this.noPermMessage = message;
    }
    public String getNoPermMessage() {
        return noPermMessage;
    }

    public void setInvalidPlayerMessage(String message){
        this.invalidPlayerMessage = message;
    }

    public String getInvalidPlayerMessage(){
        return invalidPlayerMessage;
    }

    public void setOnlyPlayersMessage(String message) {
        this.onlyPlayersMessage = message;
    }

    public String getOnlyPlayersMessage(){
        return onlyPlayersMessage;
    }

    public void setReloadMessage(String message){
        this.reloadMessage = message;
    }

    public String getReloadMessage(){
        return reloadMessage;
    }

    public void setBuyMessage(String message){this.buyBanknotMessage = message;}

    public String getBuyBanknotMessage(){return buyBanknotMessage;}

    public void setSellBanknotMessage(String message){this.sellBanknotMessage = message;}

    public String getSellBanknotMessage(){return sellBanknotMessage;}

    public void setNoInHandMessage(String message){this.noBanknotInHandMessage = message;}

    public String getNoBanknotInHandMessage(){return noBanknotInHandMessage;}

    public void setNoMoneyMessage(String message){this.noMoneyMessage = message;}

    public String getNoMoneyMessage(){return noMoneyMessage;}
}
