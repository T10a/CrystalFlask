package com.t10a.crystalflask.tileentity;

import com.t10a.crystalflask.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;

public class TileEntityBonfire extends TileEntity
{
    public int shardCount = 0;
    public int ashCount = 0;

    public boolean addShard()
    {
        if(shardCount < 1 && ashCount == 0)
        {
            shardCount++;
            return true;
        }
        return false;
    }

    public void removeShard()
    {
        if(shardCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
            shardCount--;
        }
    }

    public boolean addAsh()
    {
        if(ashCount < 1 && shardCount == 0)
        {
            ashCount++;
            return true;
        }
        return false;
    }

    public void removeAsh()
    {
        if(ashCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_ash)));
            ashCount--;
        }
    }
    public void estusRestock(ItemStack stack)
    {
        if(stack.getItem() == ModItems.estus_flask)
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
                if(shardCount > 0 && nbt.getInteger("Max Uses") < 12)
                {
                    nbt.setInteger("Max Uses", nbt.getInteger("Max Uses") + 1);
                    shardCount--;
                }
                else if(ashCount > 0 && nbt.getInteger("Potency") < 5)
                {
                    nbt.setInteger("Potency", nbt.getInteger("Potency") + 1);
                    ashCount--;
                }
                nbt.setInteger("Uses", nbt.getInteger("Max Uses"));
            }
            else
            {
                nbt.setInteger("Uses", 1);
                nbt.setInteger("Max Uses", 1);
                nbt.setInteger("Potency", 1);
            }
            stack.setTagCompound(nbt);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ShardCount", shardCount);
        compound.setInteger("AshCount", ashCount);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.shardCount = compound.getInteger("ShardCount");
        this.ashCount = compound.getInteger("AshCount");
    }
}
