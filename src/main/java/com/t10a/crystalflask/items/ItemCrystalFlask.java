package com.t10a.crystalflask.items;

import com.t10a.crystalflask.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

//Yes, this is a ton of code. I'll try to clean it up whenever I can.
public class ItemCrystalFlask extends Item
{
    public ItemCrystalFlask(String name, CreativeTabs tabs)
    {
        //It's a good idea to put the modid into the item's unlocalised name, to prevent conflicts in the en_US.lang.
        this.setUnlocalizedName(Reference.MOD_ID + "." + name);
        this.setRegistryName(Reference.MOD_ID, name);
        this.setCreativeTab(tabs);

        this.addPropertyOverride(new ResourceLocation("uses"), new IItemPropertyGetter()
        {
            NBTTagCompound nbt;
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                nbt = stack.getTagCompound();
                return MathHelper.clamp_float((float)nbt.getInteger("Uses") / (float)nbt.getInteger("Max Uses"), 0.0F, 1.0F);
            }
        });

        this.addPropertyOverride(new ResourceLocation("empty"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemCrystalFlask.getUsable(stack) ? 0.0F : 1.0F;
            }
        });
        this.setMaxStackSize(1);

    }

    //Catches whether or not this item is drinkable or not.
    private static boolean getUsable(ItemStack stack)
    {
        return stack.getItemUseAction() == EnumAction.DRINK;
    }

    //If it's drinkable, return the drinking time ticks. If not, return 1 tick for the empty/null flask.
    public int getMaxItemUseDuration(ItemStack stack)
    {
        if(getUsable(stack))
        {
            return 32;
        }
        else return 1;
    }

    //Check whether the item actually has data, and set the EnumAction based on the result.
    public EnumAction getItemUseAction(ItemStack stack)
    {
        NBTTagCompound nbt;
        if (stack.hasTagCompound())
        {
            nbt = stack.getTagCompound();
        }
        else
        {
            nbt = new NBTTagCompound();
        }
        if(nbt.hasKey("Uses"))
        {
            if(nbt.getInteger("Uses") <= 0)
            {
                return EnumAction.NONE;
            }
            else return EnumAction.DRINK;
        }
        else
        {
            return EnumAction.NONE;
        }
    }

    //Just checking to see if the item can be right-clicked.
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        playerIn.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }

    //A pretty big chunk. If this item has metadata, it'll get it. Then, if it has the metadata "Uses", it'll check to see if it is more than 0. If it is, and this isn't on the server side, it'll
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
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
            if(nbt.getInteger("Uses") > 0)
            {
                nbt.setInteger("Uses", nbt.getInteger("Uses") - 1);
                if (!worldIn.isRemote)
                {
                    if(nbt.getInteger("Potency") >= 0)
                    {
                        entityLiving.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 25 * nbt.getInteger("Potency"), 3));
                    }
                }
            }
        }
        else
        {
            nbt.setInteger("Uses", 1);
            nbt.setInteger("Max Uses", 1);
            nbt.setInteger("Potency", 1);
        }
        stack.setTagCompound(nbt);

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    //Prints info to the item's description.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lores, boolean b)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Uses"))
        {
            lores.add("Uses: " + Integer.toString(stack.getTagCompound().getInteger("Uses")));
            lores.add("Max Uses: " + Integer.toString(stack.getTagCompound().getInteger("Max Uses")));
            lores.add("Potency: " + Integer.toString(stack.getTagCompound().getInteger("Potency")));
        }
        else lores.add("Right click to activate the flask's potential.");
    }
}
