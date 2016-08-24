package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.item.Item;

public class ItemCrystalShard extends Item
{
    public ItemCrystalShard()
    {
        {
            setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUSSHARD.getUnlocalizedName());
            setRegistryName(Reference.ItemBase.ESTUSSHARD.getRegistryName());
            setCreativeTab(CrystalFlask.ESTUSTAB);
        }
    }
}
