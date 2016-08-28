package com.t10a.crystalflask.tileentity;

import com.t10a.crystalflask.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBonfire extends TileEntity
{
    //Variables telling the TileEntity what's currently contained.
    private int shardCount = 0;
    private int ashCount = 0;
    private int blazerodCount = 0;

    //This tells the block how to handle adding new Shards.
    public boolean addShard()
    {
        if(shardCount < 1 && ashCount == 0 && blazerodCount == 0)
        {
            shardCount++;
            return true;
        }
        return false;
    }
    //This tells the block how to handle removing a shard.
    public void removeShard()
    {
        if(shardCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
            shardCount--;
        }
    }
    //addAsh &  removeAsh does the same as addShard & removeShard, but for the Ash item. I COULD unify them under one call, but for now this works.
    public boolean addAsh()
    {
        if(ashCount < 1 && shardCount == 0 && blazerodCount == 0)
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

    //Same as above, but for blaze rods. I'm definitely going to unify them under 1 call eventually.
    public boolean addBlazeRod()
    {
        if(blazerodCount < 1 && shardCount == 0 && ashCount == 0)
        {
            blazerodCount++;
            return true;
        }
        return false;
    }

    public void removeBlazeRod()
    {
        if(blazerodCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(Items.BLAZE_ROD)));
            blazerodCount--;
        }
    }

    public void bonfireCraft(ItemStack stack)
    {
        //TODO: Delete this, and make a dedicated recipe handler, so it's easier to add recipes to. For both me and addon developers.
        if(stack.getItem() == Items.PRISMARINE_SHARD && blazerodCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
            stack.stackSize--;
            blazerodCount--;
        }
        else if(stack.getItem() == Items.SKULL && stack.getMetadata() == 1 && blazerodCount > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_ash)));
            stack.stackSize--;
            blazerodCount--;
        }
    }

    //This is a big chunk of code that used  to be on the flask. This handles the restocking the uses, and upgrading of the flask when this is called by BlockBonfire.
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

    //This merely saves the variables defined earlier to NBT.
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ShardCount", shardCount);
        compound.setInteger("AshCount", ashCount);

        return compound;
    }

    //Similar to above, but it loads from NBT instead.
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.shardCount = compound.getInteger("ShardCount");
        this.ashCount = compound.getInteger("AshCount");
    }
}
