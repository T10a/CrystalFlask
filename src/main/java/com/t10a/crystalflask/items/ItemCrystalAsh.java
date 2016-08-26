package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.item.Item;

public class ItemCrystalAsh extends Item
{
    public ItemCrystalAsh()
    {
        //It's a good idea to put the modid into the item's unlocalised name, to prevent conflicts in the en_US.lang.
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUSASH.getUnlocalizedName());
        setRegistryName(Reference.ItemBase.ESTUSASH.getRegistryName());
        setCreativeTab(CrystalFlask.ESTUSTAB);
    }
}
