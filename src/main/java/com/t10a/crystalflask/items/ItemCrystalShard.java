package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.item.Item;

public class ItemCrystalShard extends Item
{
    public ItemCrystalShard()
    {
        {
            //It's a good idea to put the modid into the item's unlocalised name, to prevent conflicts in the en_US.lang.
            setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUSSHARD.getUnlocalizedName());
            setRegistryName(Reference.ItemBase.ESTUSSHARD.getRegistryName());
            setCreativeTab(CrystalFlask.ESTUSTAB);
        }
    }
}
