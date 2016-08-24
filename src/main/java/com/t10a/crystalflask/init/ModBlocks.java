package com.t10a.crystalflask.init;

import com.t10a.crystalflask.blocks.BlockBonfire;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block bonfire;

    public static void init()
    {
        bonfire = new BlockBonfire();
    }

    public static void register()
    {
        registerBlock(bonfire);
    }

    private static void registerBlock(Block block)
    {
        GameRegistry.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    public static void registerRenders()
    {
        registerRender(bonfire);
    }

    private static void registerRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(),"inventory"));
    }
}
