package com.t10a.crystalflask.items;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import com.t10a.crystalflask.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

//Yes, this is a ton of code. I'll try to clean it up ASAP.
public class ItemCrystalFlask extends Item
{
    public ItemCrystalFlask()
    {
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.ItemBase.ESTUS.getUnlocalizedName());
        setRegistryName(Reference.ItemBase.ESTUS.getRegistryName());
        this.addPropertyOverride(new ResourceLocation("empty"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return ItemCrystalFlask.getUsable(stack) ? 0.0F : 1.0F;
            }
        });
        setCreativeTab(CrystalFlask.ESTUSTAB);
        this.setMaxStackSize(1);
    }
    //Catches whether or not this item is drinkable or not.
    public static boolean getUsable(ItemStack stack)
    {
        if(EnumAction.DRINK == stack.getItemUseAction())
        {
            return true;
        }
        else return false;
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

    /*Big chunk o' code. This checks whether if the player can edit blocks (this'll be useful for when this can be used to upgrade the flask.), and then check the Item for NBT,
    * and then finally, if the block it's checking is a Bonfire it'll refill it to the Max Uses NBT (This also happens when Uses is applied to the item.)
    */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            NBTTagCompound nbt;
            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();

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
                if (facing != EnumFacing.DOWN && block == ModBlocks.bonfire)
                {
                    nbt.setInteger("Uses", nbt.getInteger("Max Uses"));
                    return EnumActionResult.SUCCESS;
                }
                else return EnumActionResult.PASS;
            }
            else
            {
                nbt.setInteger("Uses", 1);
                nbt.setInteger("Max Uses", 1);
            }
            stack.setTagCompound(nbt);

            return EnumActionResult.PASS;
        }
    }

    //Another chunk. If this item has metadata, it'll get it. Then, if it has the metadata "Uses", it'll check to see if it is more than 0. If it is, and this isn't on the server side, it'll
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
                    entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
                }
            }
        }
        else
        {
            nbt.setInteger("Uses", 1);
            nbt.setInteger("Max Uses", 1);
        }
        stack.setTagCompound(nbt);

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List lores, boolean b)
    {
        lores.add("A lone flask, used to contain §mestus§r§7 a magic flame that restores the health of the drinker. ");
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Uses"))
        {
            lores.add("Uses: " + Integer.toString(stack.getTagCompound().getInteger("Uses")));
        }
    }
}
