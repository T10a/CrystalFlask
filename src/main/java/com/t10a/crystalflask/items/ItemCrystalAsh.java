package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.item.Item;

public class ItemCrystalAsh extends Item
{
    public ItemCrystalAsh()
    {
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUSASH.getUnlocalizedName());
        setRegistryName(Reference.ItemBase.ESTUSASH.getRegistryName());
        setCreativeTab(CrystalFlask.ESTUSTAB);
    }
}
