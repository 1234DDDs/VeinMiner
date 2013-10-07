package portablejim.veinminer.event;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import portablejim.veinminer.server.MinerServer;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: james
 * Date: 12/08/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityDropHook {

    @ForgeSubscribe
    public void tryAddEntity(EntityJoinWorldEvent event) {
        Entity entity = event.entity;

        if(event.isCanceled()) {
            return;
        }

        if(MinerServer.instance == null) {
            return;
        }

        int entityX = (int)Math.floor(entity.posX);
        int entityY = (int)Math.floor(entity.posY);
        int entityZ = (int)Math.floor(entity.posZ);

        if(!EntityItem.class.isInstance(entity)) {
            return;
        }

        EntityItem entityItem = (EntityItem) entity;

        boolean isBlock = false;
        boolean isItem = false;

        if(entityItem.getEntityItem().itemID < Block.blocksList.length) {
            isBlock = Block.blocksList[entityItem.getEntityItem().itemID] != null;
        }
        if(entityItem.getEntityItem().itemID < Item.itemsList.length) {
            isItem = Item.itemsList[entityItem.getEntityItem().itemID] != null;
        }

        //new MinerServer();
        if((isBlock || isItem) && MinerServer.instance != null && MinerServer.instance.isRegistered(entityX, entityY, entityZ)) {
            MinerServer.instance.addEntity(entity);
            event.setCanceled(true);
        }
    }
}
