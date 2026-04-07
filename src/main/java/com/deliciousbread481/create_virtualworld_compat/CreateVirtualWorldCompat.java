package com.deliciousbread481.create_virtualworld_compat;  
  
import com.mojang.logging.LogUtils;  
import net.minecraftforge.fml.common.Mod;  
import org.slf4j.Logger;  
  
@Mod(CreateVirtualWorldCompat.MOD_ID)  
public class CreateVirtualWorldCompat {  
  
    public static final String MOD_ID = "create_virtualworld_compat";  
    public static final Logger LOGGER = LogUtils.getLogger();  
  
    public CreateVirtualWorldCompat() {  
        LOGGER.info("Create VirtualWorld Compat initialized - Universal contraption compatibility enabled!");  
    }  
}