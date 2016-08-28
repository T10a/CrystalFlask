package com.t10a.crystalflask.init;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
    public static void register()
    {
        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.bonfire), "  w", " r ", "aaa", 'w', new ItemStack(Items.IRON_SWORD), 'a', new ItemStack(ModItems.estus_ash), 'r', new ItemStack(Items.BLAZE_ROD));
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.estus_flask), "gbg","epe"," e ", 'e', new ItemStack(Items.EMERALD), 'b', new ItemStack(Items.BLAZE_POWDER), 'p', PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HEALING), 'g', new ItemStack(Items.GOLD_INGOT));
    }
}
