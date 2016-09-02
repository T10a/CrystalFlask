package com.t10a.crystalflask.items;

import com.t10a.crystalflask.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class ItemComponent extends Item
{
    public ItemComponent(String name, CreativeTabs tabs)
    {
        this.setUnlocalizedName(Reference.MOD_ID + "." + name);
        this.setRegistryName(Reference.MOD_ID, name);
        this.setCreativeTab(tabs);
    }
}
