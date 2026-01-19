package com.agent772.create_virtualworld_compat.mixin;

import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to prevent crashes when block entities call {@code setChanged()} during contraption assembly in Create 6.0.8+.
 * 
 * <h2>The Problem</h2>
 * <p>Starting with Create 6.0.8, the {@code ClientContraption} class assigns {@link VirtualRenderWorld} 
 * to block entities before their NBT data is loaded. When block entities call {@code setChanged()} during 
 * {@code loadAdditional()}, it triggers the following call chain:</p>
 * 
 * <pre>
 * BlockEntity.setChanged()
 *   → Level.blockEntityChanged()
 *     → Level.getChunkAt()
 *       → VirtualRenderWorld.getChunk()
 *         → throws UnsupportedOperationException
 * </pre>
 * 
 * <p>{@link VirtualRenderWorld} is a lightweight, temporary world used only for rendering contraptions.
 * It intentionally throws {@code UnsupportedOperationException} in {@code getChunk()} because it doesn't
 * maintain a chunk array.</p>
 * 
 * <h2>Why This Didn't Happen in Create 6.0.6</h2>
 * <p>In Create 6.0.6, block entities were loaded with a reference to the real world, so chunk operations
 * worked normally. Create 6.0.8 changed this behavior to use {@link VirtualRenderWorld} during loading.</p>
 * 
 * <h2>The Solution</h2>
 * <p>This mixin cancels {@code Level.blockEntityChanged()} when called on a {@link VirtualRenderWorld}.
 * Since {@link VirtualRenderWorld} is temporary and never persists to disk, marking chunks as unsaved
 * is unnecessary and can be safely skipped.</p>
 * 
 * <h2>Compatibility</h2>
 * <p>This fix is universal and works with any mod where block entities call {@code setChanged()} during
 * initialization, including:</p>
 * <ul>
 *   <li>MrCrayfish's Refurbished Furniture Mod (generators, electrical blocks)</li>
 *   <li>Any mod with redstone/power systems that update during initialization</li>
 *   <li>Any mod with inventory/fluid systems that mark chunks dirty during loading</li>
 * </ul>
 * 
 * @author Agent772
 * @version 1.0.0
 * @see VirtualRenderWorld
 * @see Level#blockEntityChanged(BlockPos)
 */
@Mixin(value = Level.class)
public class VirtualRenderWorldMixin {
    
    /**
     * Cancels {@code blockEntityChanged()} when executed on a {@link VirtualRenderWorld}.
     * 
     * <p>This prevents the crash that occurs when block entities try to mark chunks as unsaved
     * in a world that doesn't support chunk operations.</p>
     * 
     * <p><b>Why this is safe:</b> {@link VirtualRenderWorld} is temporary and render-only.
     * It never persists to disk, so marking chunks as unsaved has no effect and can be skipped.</p>
     * 
     * <p><b>Performance impact:</b> Minimal - adds a single {@code instanceof} check per
     * {@code blockEntityChanged()} call.</p>
     * 
     * @param pos The block position (unused in this implementation)
     * @param ci Mixin callback info used to cancel the method execution
     */
    @Inject(method = "blockEntityChanged", at = @At("HEAD"), cancellable = true)
    public void preventBlockEntityChangedInVirtualWorld(BlockPos pos, CallbackInfo ci) {
        // Cancel if this Level is actually a VirtualRenderWorld
        // VirtualRenderWorld doesn't support chunk operations and doesn't need persistence
        if ((Object) this instanceof VirtualRenderWorld) {
            ci.cancel();
        }
    }
}
