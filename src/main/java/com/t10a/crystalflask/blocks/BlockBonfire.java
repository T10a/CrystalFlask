package com.t10a.crystalflask.blocks;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import com.t10a.crystalflask.init.ModItems;
import com.t10a.crystalflask.tileentity.TileEntityBonfire;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

public class BlockBonfire extends Block implements ITileEntityProvider
{
    //private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    //This sets the hitbox for this block,
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 3, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 15, 0.0625 * 12);

    public BlockBonfire()
    {
        super(Material.ROCK);
        //It's a good idea to put the modid into the block's unlocalised name, to prevent conflicts in the en_US.lang.
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.BlockBase.BONFIRE.getUnlocalizedName());
        setRegistryName(Reference.BlockBase.BONFIRE.getRegistryName());
        setCreativeTab(CrystalFlask.ESTUSTAB);
        this.setLightLevel(5.0F);
    }

    /*
    * The following are obvious. isFullCube checks if this is a full cube (it isn't), isOpaqueCube checks if this cube is opaque (it isn't a cube, so no),
    * getBlockLayer returns this block is solid, getBoundingBox tells the game the hitbox we defined earlier(?), and addCollisionBoxToList registers the hitbox(?).
    */
    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    //WIP. Pretty much is responsible for the particle effects this block emits.
    //TODO: Make this emit special particles based on what item is contained.
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;

        if (rand.nextDouble() < 0.3D)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + d4, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1 + d4, d2, 0.0D, 0.0D, 0.0D, new int[0]);
    }

    //This pretty much tells the TileEntity class what to do based on what's right-clicking it.
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
                    else if (heldItem.getItem() == ModItems.estus_ash)
                    {
                        if(bonfire.addAsh())
                        {
                            heldItem.stackSize--;
                            return true;
                        }
                    }
                    else if (heldItem.getItem() == ModItems.estus_flask)
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

    //Basically makes a new TileEntity when this is placed.
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBonfire();
    }
}
