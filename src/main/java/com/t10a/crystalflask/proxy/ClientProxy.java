package com.t10a.crystalflask.proxy;

import com.t10a.crystalflask.init.ModBlocks;
import com.t10a.crystalflask.init.ModItems;

public class ClientProxy implements ICommonProxy
{
    @Override
    public void init()
    {
        ModItems.registerRenders();
        ModBlocks.registerRenders();
    }
}
