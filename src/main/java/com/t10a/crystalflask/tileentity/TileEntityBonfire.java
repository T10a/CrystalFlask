package com.t10a.crystalflask.tileentity;

import com.t10a.crystalflask.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
/*
 * TODO: Re-implement the changes I did before, but properly. (i.e. use ItemStackhandler & Forge Capabilities to store items instead of Ints.).
*/
public class TileEntityBonfire extends TileEntity
{
    private int storedAsh, storedShard;

    public boolean addShard()
    {
        if(storedShard < 1 && storedAsh != 1)
        {
            storedShard++;
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        return false;
    }
    public boolean addAsh()
    {
        if(storedAsh < 1 && storedShard != 1)
        {
            storedAsh++;
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        return false;
    }

    //This tells the block how to handle removing items.
    public void removeAsh()
    {
        if(storedAsh > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_ash)));
            storedAsh--;
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    public void removeShard()
    {
        if(storedShard > 0)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
            storedShard--;
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    public int getStoredAsh()
    {
        return storedAsh;
    }

    public int getStoredShard()
    {
        return storedShard;
    }

    //This is a big chunk of code that used  to be on the flask. This handles the restocking the uses, and upgrading of the flask when this is called by BlockBonfire.
    public void estusRestock(ItemStack heldItem)
    {
        if(heldItem.getItem() == ModItems.estus_flask)
        {
            NBTTagCompound nbt;

            if (heldItem.hasTagCompound())
            {
                nbt = heldItem.getTagCompound();
            }
            else
            {
                nbt = new NBTTagCompound();
            }
            if (nbt.hasKey("Uses"))
            {
                if(storedShard > 0 && nbt.getInteger("Max Uses") < 12)
                {
                    nbt.setInteger("Max Uses", nbt.getInteger("Max Uses") + 1);
                    storedShard--;
                }
                else if(storedAsh > 0 && nbt.getInteger("Potency") < 5)
                {
                    nbt.setInteger("Potency", nbt.getInteger("Potency") + 1);
                    storedAsh--;
                }
                nbt.setInteger("Uses", nbt.getInteger("Max Uses"));
            }
            else
            {
                nbt.setInteger("Uses", 1);
                nbt.setInteger("Max Uses", 1);
                nbt.setInteger("Potency", 1);
            }
            heldItem.setTagCompound(nbt);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ShardCount", this.storedShard);
        compound.setInteger("AshCount", this.storedAsh);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.storedShard = compound.getInteger("ShardCount");
        this.storedAsh = compound.getInteger("AshCount");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound tag = pkt.getNbtCompound();
        readUpdateTag(tag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeUpdateTag(tag);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        writeUpdateTag(tag);
        return tag;
    }

    public void writeUpdateTag(NBTTagCompound tag)
    {
        tag.setInteger("ShardCount", this.storedShard);
        tag.setInteger("AshCount", this.storedAsh);
    }

    public void readUpdateTag(NBTTagCompound tag)
    {
        this.storedShard = tag.getInteger("ShardCount");
        this.storedAsh = tag.getInteger("AshCount");
    }
}
