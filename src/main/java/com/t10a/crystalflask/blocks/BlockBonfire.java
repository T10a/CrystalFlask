package com.t10a.crystalflask.blocks;

import com.t10a.crystalflask.CrystalFlask;
import com.t10a.crystalflask.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockBonfire extends Block
{
    //private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 3, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 15, 0.0625 * 12);

    public BlockBonfire()
    {
        super(Material.ROCK);
        setUnlocalizedName(Reference.MOD_ID + "." + Reference.BlockBase.BONFIRE.getUnlocalizedName());
        setRegistryName(Reference.BlockBase.BONFIRE.getRegistryName());
        setCreativeTab(CrystalFlask.ESTUSTAB);
    }

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

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }
}
