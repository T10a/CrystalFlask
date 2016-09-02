package com.t10a.crystalflask.tileentity;

import com.t10a.crystalflask.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBonfire extends TileEntity
{
    public ItemStack storedItem;

    public static class StackHandler extends ItemStackHandler
    {
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return super.insertItem(slot, stack, simulate);
        }
        
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return super.extractItem(slot, amount, simulate);
        }
        protected int getStackLimit(int slot, ItemStack stack) 
        {
            return 1;
        }
        public NBTTagCompound serializeNBT() 
        {
            return super.serializeNBT();
        }
        public void deserializeNBT(NBTTagCompound nbt) 
        {
            super.deserializeNBT(nbt);
        }
    }

    //Variables telling the TileEntity what's currently contained.;
    //BELOW IS COMMENTED OUT, MAINLY FOR CHECKING OUT THE NEW ITEMSTACKHANDLER
    //This tells the block how to handle adding items.
    /*
    public boolean addItem(ItemStack heldItem)
    {
        if(heldItem.getItem() == ModItems.estus_shard)
        {
            storedItem = new ItemStack(ModItems.estus_shard);
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        else if(heldItem.getItem() == ModItems.estus_ash)
        {
            storedItem = new ItemStack(ModItems.estus_ash);
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        else if(heldItem.getItem() == Items.BLAZE_ROD)
        {
            storedItem = new ItemStack(Items.BLAZE_ROD);
            markDirty();
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        return false;
    }
    //This tells the block how to handle removing items.
    public void removeItem()
    {
        if(storedItem.getItem() == ModItems.estus_ash)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_ash)));
        }
        else if(storedItem.getItem() == ModItems.estus_shard)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
        }
        else if(storedItem.getItem() == Items.BLAZE_ROD)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(Items.BLAZE_ROD)));
        }
        storedItem = null;
        markDirty();
        IBlockState state = worldObj.getBlockState(pos);
        worldObj.notifyBlockUpdate(pos, state, state, 3);
    }
    */
    //Temporary crafting class. It'll be replaced by a dedicated crafting thing, so it's easier to add recipes, or other mods can add to it, in jolly modding cooperation \[T]/
    public void bonfireCraft(ItemStack heldItem)
    {
        //TODO: Delete this, and make a dedicated recipe handler, so it's easier to add recipes to. For both me and addon developers.
        if(heldItem.getItem() == Items.PRISMARINE_SHARD && storedItem.getItem() == Items.BLAZE_ROD)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_shard)));
            heldItem.stackSize--;
            storedItem.stackSize--;
        }
        else if(heldItem.getItem() == Items.SKULL && heldItem.getMetadata() == 1 && storedItem.getItem() == Items.BLAZE_ROD)
        {
            worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.estus_ash)));
            heldItem.stackSize--;
            storedItem.stackSize--;
        }
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
                if(storedItem.getItem() == ModItems.estus_shard && nbt.getInteger("Max Uses") < 12)
                {
                    nbt.setInteger("Max Uses", nbt.getInteger("Max Uses") + 1);
                    storedItem.stackSize--;
                }
                else if(storedItem.getItem() == ModItems.estus_ash && nbt.getInteger("Potency") < 5)
                {
                    nbt.setInteger("Potency", nbt.getInteger("Potency") + 1);
                    storedItem.stackSize--;
                }
                nbt.setInteger("Uses", nbt.getInteger("Max Uses"));
            }
            else
            {
                nbt.setInteger("Uses", 1);
                nbt.setInteger("Max Uses", 1);
                nbt.setInteger("Potency", 1);
            }
            storedItem.setTagCompound(nbt);
        }
    }

    //This merely saves the variables defined earlier to NBT.
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {   
    	if(storedItem != null)
    	{
			super.writeToNBT(compound);
			compound.setTag("ContainedItems", storedItem.serializeNBT());
    	}
    	return compound;
    }

    //Similar to above, but it loads from NBT instead.
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.storedItem = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("ContainedItems"));
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
    	if(storedItem != null)
    	{
            tag.setTag("ContainedItems", storedItem.writeToNBT(tag));
    	}
    }

    public void readUpdateTag(NBTTagCompound tag)
    {
        this.storedItem = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ContainedItems"));
    }
}
