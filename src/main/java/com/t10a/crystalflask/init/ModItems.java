package com.t10a.crystalflask.init;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.items.ItemComponent;
import com.t10a.crystalflask.items.ItemCrystalFlask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    //Item list. Defines EVERY item in the mod.
    public static Item estus_flask;
    public static Item estus_shard;
    public static Item estus_ash;

    /*
    * First step in initialisation, this tells the game what the items listed above are actually instances of (e.g. estus_ash is an instance of ItemComponent,
    * with the name & creative tab as variables.
    */
    public static void init()
    {
        estus_flask = new ItemCrystalFlask("estus_flask", CrystalFlask.ESTUSTAB);
        estus_shard = new ItemComponent("estus_shard", CrystalFlask.ESTUSTAB);
        estus_ash = new ItemComponent("estus_ash", CrystalFlask.ESTUSTAB);
        register();
    }

    //Loaded after init() does it's thing, this tells the game to register the items into the game.
    private static void register()
    {
        GameRegistry.register(estus_flask);
        GameRegistry.register(estus_shard);
        GameRegistry.register(estus_ash);
    }

    //This tells the game to load the model of the following items. THIS IS LOADED CLIENTSIDE, AS THIS WILL CRASH DEDICATED SERVERS IF LOADED SERVERSIDE.
    public static void registerRenders()
    {
        registerRender(estus_flask);
        registerRender(estus_shard);
        registerRender(estus_ash);
    }

    //Yup, this is bad form. I know. This works for now, so it'll stay for a little bit.
    //Tells the game how to register the renders of the items listed in registerRenders.
    private static void registerRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(),"inventory"));
    }
}
