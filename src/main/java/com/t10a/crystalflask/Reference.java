package com.t10a.crystalflask;

public class Reference
{
    //Basic info that the game needs when the mod itsef loads.
    public static final String MOD_ID = "crystalflask";
    public static final String NAME = "Crystal Flask";
    public static final String VERSION = "1.10.2-1.0";
    public static final String ACCEPTED_VERSION = "[1.10.2]";

    public static final String CLIENT_PROXY_CLASS = "com.t10a.crystalflask.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.t10a.crystalflask.proxy.ServerProxy";

    //Enum that's for conveniently making new items.
    public enum ItemBase
    {
        ESTUS("estusflask","ItemEstusFlask"),
        ESTUSSHARD("estus_shard","ItemEstusShard"),
        ESTUSASH("estus_ash","ItemEstusAsh");

        private String unlocalizedName;
        private String registryName;

        ItemBase(String unlocalizedName, String registryName)
        {
            this.unlocalizedName = unlocalizedName;
            this.registryName = registryName;
        }

        public String getRegistryName()
        {
            return registryName;
        }

        public String getUnlocalizedName()
        {
            return unlocalizedName;
        }
    }
    //Same as ItemBase, but for blocks rather than items.
    public enum BlockBase
    {
        BONFIRE("bonfire","BlockBonfire");

        private String unlocalizedName;
        private String registryName;

        BlockBase(String unlocalizedName, String registryName)
        {
            this.unlocalizedName = unlocalizedName;
            this.registryName = registryName;
        }

        public String getRegistryName()
        {
            return registryName;
        }

        public String getUnlocalizedName()
        {
            return unlocalizedName;
        }
    }
}
