package com.t10a.crystalflask;

import com.t10a.crystalflask.init.ModBlocks;
import com.t10a.crystalflask.init.ModItems;
import com.t10a.crystalflask.init.ModRecipes;
import com.t10a.crystalflask.proxy.ICommonProxy;
import com.t10a.crystalflask.tileentity.TileEntityBonfire;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid= Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSION)
public class CrystalFlask
{
    @Mod.Instance
    public static CrystalFlask instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.SERVER_PROXY_CLASS)
    public static ICommonProxy proxy;

    public static final CreativeTabs ESTUSTAB = new CrystalTab();

    //Quick note: The println will go away in release versions. This is mainly so I can see when these chunks of code are loaded into the game.
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Pre Initialisation!");
        ModItems.init();
        ModItems.register();

        ModBlocks.init();
        ModBlocks.register();
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event)
    {
        System.out.println("Initialisation!");
        proxy.init();

        ModRecipes.register();

        GameRegistry.registerTileEntity(TileEntityBonfire.class, Reference.MOD_ID + "TileEntityBonfire");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        System.out.println("Post Initialisation!");
    }
}
