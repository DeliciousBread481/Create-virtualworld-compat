package com.deliciousbread481.create_virtualworld_compat.mixin;  
  
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;  
import net.minecraft.core.BlockPos;  
import net.minecraft.world.level.Level;  
import org.spongepowered.asm.mixin.Mixin;  
import org.spongepowered.asm.mixin.injection.At;  
import org.spongepowered.asm.mixin.injection.Inject;  
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;  
  
@Mixin(value = Level.class)  
public class VirtualRenderWorldMixin {  
  
    @Inject(method = "blockEntityChanged", at = @At("HEAD"), cancellable = true)  
    private void preventBlockEntityChangedInVirtualWorld(BlockPos pos, CallbackInfo ci) {  
        if ((Object) this instanceof VirtualRenderWorld) {  
            ci.cancel();  
        }  
    }  
}