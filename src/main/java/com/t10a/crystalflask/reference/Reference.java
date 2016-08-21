package com.t10a.crystalflask.reference;

public class Reference
{
    public static final String MOD_ID = "crystalflask";
    public static final String NAME = "Crystal Flask";
    public static final String VERSION = "1.10.2-1.0";
    public static final String ACCEPTED_VERSION = "[1.10.2]";

    public static final String CLIENT_PROXY_CLASS = "com.t10a.crystalflask.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.t10a.crystalflask.proxy.ServerProxy";

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
}
