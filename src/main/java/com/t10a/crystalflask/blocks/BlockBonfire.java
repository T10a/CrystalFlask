package com.t10a.crystalflask.blocks;

import com.t10a.crystalflask.Reference;
import com.t10a.crystalflask.init.ModItems;
import com.t10a.crystalflask.tileentity.TileEntityBonfire;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockBonfire extends Block
{
    //private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    //This sets the hitbox for this block,
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 3, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 15, 0.0625 * 12);

    public BlockBonfire(String name, CreativeTabs tabs)
    {
        super(Material.ROCK);
        //It's a good idea to put the modid into the block's unlocalised name, to prevent conflicts in the en_US.lang.
        setUnlocalizedName(Reference.MOD_ID + "." + name);
        setRegistryName(Reference.MOD_ID, name);
        setCreativeTab(tabs);
        this.setLightLevel(5.0F);
    }

    /*
    * The following are obvious. isFullCube checks if this is a full cube (it isn't), isOpaqueCube checks if this cube is opaque (it isn't a cube, so no),
    * getBlockLayer returns this block is solid, getBoundingBox tells the game the hitbox we defined earlier(?), and addCollisionBoxToList registers the hitbox(?).
    */
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    //WIP. Pretty much is responsible for the particle effects this block emits.
    //TODO: Make the bonfire particles resemble the flames in the actual game. THEN make them change based on what's in it.
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = rand.nextDouble() * 0.6D - 0.7D;

        if (rand.nextDouble() < 0.3D)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
        /*
        if(worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityBonfire)
            {
                TileEntityBonfire bonfire = (TileEntityBonfire) tileEntity;
                if(bonfire.getStoredShard() > 0)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                    worldIn.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                }
                else if(bonfire.getStoredAsh() > 0)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                }
                else
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1 + d3, d2, 0.0D, 0.0D, 0.0D, 0);
                }
            }
        }
        */
    }
    //This pretty much tells the TileEntity class what to do based on what's right-clicking it.
    //TODO: As stated in TileEntityBonfire, implement a more proper way of handling items.
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityBonfire)
            {
                TileEntityBonfire bonfire = (TileEntityBonfire) tileEntity;
                if(heldItem != null)
                {
                    if (heldItem.getItem() == ModItems.estus_shard)
                    {
                        if(bonfire.addShard())
                        {
                            heldItem.stackSize--;
                            return true;
                        }
                    }
                    else if(heldItem.getItem() == ModItems.estus_ash)
                    {
                        if(bonfire.addAsh())
                        {
                            heldItem.stackSize--;
                            return true;
                        }
                    }
                    else if(heldItem.getItem() == ModItems.estus_flask)
                    {
                        bonfire.estusRestock(heldItem);
                        return true;
                    }
                }
                bonfire.removeShard();
                bonfire.removeAsh();
            }
        }
        return true;
    }

    //Tells the game this has a TileEntity
    @Override
    public boolean hasTileEntity(IBlockState state) 
    {
        return true;
    }

    //Tells the game what tile entity to create when placed
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBonfire();
    }
}
