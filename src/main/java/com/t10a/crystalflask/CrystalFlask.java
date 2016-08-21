package com.t10a.crystalflask;

import com.t10a.crystalflask.proxy.ICommonProxy;
import com.t10a.crystalflask.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid= Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSION)
public class CrystalFlask
{
    @Mod.Instance
    public static CrystalFlask instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,serverSide = Reference.SERVER_PROXY_CLASS)
    public static ICommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Pre Initialisation!");
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event)
    {
        System.out.println("Initialisation!");
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        System.out.println("Post Initialisation!");
    }
}
