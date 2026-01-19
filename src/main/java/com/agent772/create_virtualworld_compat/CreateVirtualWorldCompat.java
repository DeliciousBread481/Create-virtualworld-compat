package com.agent772.create_virtualworld_compat;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Main mod class for Create: VirtualWorld Compat.
 * 
 * <p>This mod provides a universal compatibility fix for Create 6.0.8+ that prevents crashes
 * when placing block entities on contraptions. The issue occurs because Create 6.0.8 loads
 * block entities into a VirtualRenderWorld before NBT processing, and when block entities
 * call setChanged() during initialization, it triggers chunk operations that VirtualRenderWorld
 * doesn't support.</p>
 * 
 * <p>The actual fix is implemented via Mixin in 
 * {@link com.agent772.create_virtualworld_compat.mixin.VirtualRenderWorldMixin}</p>
 * 
 * <h2>Technical Details:</h2>
 * <ul>
 *   <li><b>Root Cause:</b> Create 6.0.8's ClientContraption assigns VirtualRenderWorld to block entities 
 *       before NBT loading</li>
 *   <li><b>Trigger:</b> Block entities calling setChanged() during loadAdditional()</li>
 *   <li><b>Solution:</b> Cancel blockEntityChanged() calls when Level is VirtualRenderWorld</li>
 *   <li><b>Scope:</b> Universal fix for any mod with similar block entity behavior</li>
 * </ul>
 * 
 * @author Agent772
 * @version 1.0.0
 * @see com.agent772.create_virtualworld_compat.mixin.VirtualRenderWorldMixin
 */
@Mod(CreateVirtualWorldCompat.MOD_ID)
public class CreateVirtualWorldCompat {
    /**
     * The mod ID used for registration and identification
     */
    public static final String MOD_ID = "create_virtualworld_compat";
    
    /**
     * Logger instance for this mod
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructor called by NeoForge during mod initialization.
     * 
     * <p>The actual compatibility fix is applied via Mixin at class load time,
     * this constructor just logs successful initialization.</p>
     * 
     * @param modEventBus The mod-specific event bus for this mod
     * @param modContainer The container holding mod metadata
     */
    public CreateVirtualWorldCompat(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Create VirtualWorld Compat initialized - Universal contraption compatibility enabled!");
    }
}
