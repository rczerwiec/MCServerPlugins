
package pl.smpickaxe.spawners;

import org.bukkit.entity.EntityType;

public class EntityTypesInSpawner {
    //Random EntityType
    public static EntityType RandomEntityType(){
        int rand = (int) (Math.random() * (15 - 1 + 1) + 1);
        switch(rand){
            case 1:return EntityType.CAVE_SPIDER;
            case 2:return EntityType.CHICKEN;
            case 3:return EntityType.WOLF;
            case 4:return EntityType.OCELOT;
            case 5:return EntityType.ENDERMAN;
            case 6:return EntityType.HOGLIN;
            case 7:return EntityType.PIGLIN;
            case 8:return EntityType.POLAR_BEAR;
            case 9:return EntityType.RABBIT;
            case 10:return EntityType.SHEEP;
            case 11:return EntityType.SPIDER;
            case 12:return EntityType.SKELETON;
            case 13:return EntityType.ZOMBIE;
            case 14:return EntityType.PIG;
            default:return EntityType.PIG;


        }

    }
}


