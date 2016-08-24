package com.t10a.crystalflask;

import com.t10a.crystalflask.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CrystalTab extends CreativeTabs
{
    public CrystalTab() {
        super("tabCrystalFlask");
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.estus_flask;
    }
}
