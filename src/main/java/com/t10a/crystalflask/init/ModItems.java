package com.t10a.crystalflask.init;

import com.t10a.crystalflask.items.ItemCrystalAsh;
import com.t10a.crystalflask.items.ItemCrystalFlask;
import com.t10a.crystalflask.items.ItemCrystalShard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    public static Item estus_flask;
    public static Item estus_shard;
    public static Item estus_ash;

    public static void init()
    {
        estus_flask = new ItemCrystalFlask();
        estus_shard = new ItemCrystalShard();
        estus_ash = new ItemCrystalAsh();
    }

    public static void register()
    {
        GameRegistry.register(estus_flask);
        GameRegistry.register(estus_shard);
        GameRegistry.register(estus_ash);
    }

    public static void registerRenders()
    {
        registerRender(estus_flask);
        registerRender(estus_shard);
        registerRender(estus_ash);
    }

    private static void registerRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(),"inventory"));
    }
}
