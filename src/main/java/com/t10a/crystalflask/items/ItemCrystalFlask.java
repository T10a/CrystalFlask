package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCrystalFlask extends Item
{
    public ItemCrystalFlask()
    {
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUS.getUnlocalizedName());
        setRegistryName(Reference.ItemBase.ESTUS.getRegistryName());
        setCreativeTab(CrystalFlask.ESTUSTAB);
        this.setMaxStackSize(1);
    }

    /*
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
    {
        list.add("A crystallised flask, containing a magical flame. Drink to restore HP.");
        list.add("(totally not an estus flask)");
    }
    */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }

    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote)
        {
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH));
        }

        NBTTagCompound nbt;
        if (stack.hasTagCompound())
        {
            nbt = stack.getTagCompound();
        }
        else
        {
            nbt = new NBTTagCompound();
        }

        if (nbt.hasKey("Uses"))
        {
            nbt.setInteger("Uses", nbt.getInteger("Uses") + 1);
        }
        else
        {
            nbt.setInteger("Uses", 1);
        }
        stack.setTagCompound(nbt);

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lores, boolean b)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Uses"))
        {
            lores.add("Uses: " + Integer.toString(stack.getTagCompound().getInteger("Uses")));
        }
    }
}
