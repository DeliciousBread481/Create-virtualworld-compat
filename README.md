# Create: VirtualWorld Compat

A universal compatibility mod that fixes crashes when placing block entities from various mods on Create contraptions.

## The Problem

Starting with **Create 6.0.8**, placing certain block entities on contraptions causes the game to crash with:
```
java.lang.UnsupportedOperationException: VirtualRenderWorld doesn't maintain a chunk array.
    at com.simibubi.create.content.contraptions.render.VirtualRenderWorld.getChunk(VirtualRenderWorld.java:69)
```

This occurs because Create 6.0.8 introduced a change where block entities are loaded into a `VirtualRenderWorld` before their NBT data is processed. When block entities call `setChanged()` during initialization, it triggers `Level.blockEntityChanged()` which tries to mark chunks as unsaved - but `VirtualRenderWorld` is a lightweight render-only world that doesn't support chunk operations.

## The Solution

This mod uses a Mixin to intercept calls to `Level.blockEntityChanged()` and cancels them when the level is a `VirtualRenderWorld`. Since `VirtualRenderWorld` is temporary and never persists, marking chunks as unsaved is unnecessary and can be safely skipped.

```java
@Mixin(value = Level.class)
public class VirtualRenderWorldMixin {
    @Inject(method = "blockEntityChanged", at = @At("HEAD"), cancellable = true)
    public void preventBlockEntityChangedInVirtualWorld(BlockPos pos, CallbackInfo ci) {
        if ((Object) this instanceof VirtualRenderWorld) {
            ci.cancel();
        }
    }
}
```

## Compatibility

### Affected Mods
This fix is **universal** and works with any mod where block entities call `setChanged()` during loading:

- ✅ **MrCrayfish's Refurbished Furniture Mod** (Generators, electrical blocks)
- ✅ **Any mod with redstone/power systems** that update during initialization
- ✅ **Any mod with inventory/fluid systems** that mark chunks dirty during loading
- ✅ **Any custom block entities** that trigger chunk updates in their constructor or `loadAdditional()`

### Requirements

- **Minecraft**: 1.21.1
- **NeoForge**: 21.1.209+
- **Create**: 6.0.8+ (the issue doesn't exist in 6.0.6 and earlier)
- **Flywheel**: 1.0.4+

## Installation

### For Players

1. Download the latest release from [Releases](../../releases)
2. Place the JAR file in your `mods` folder alongside Create and any affected mods
3. Launch Minecraft

### For Developers

Add to your `build.gradle`:

```gradle
repositories {
    maven {
        name = "Create VirtualWorld Compat"
        url = "https://example.com/maven" // Replace with actual maven repository
    }
}

dependencies {
    implementation "com.agent772.create_virtualworld_compat:create_virtualworld_compat:${project.create_vw_compat_version}"
}
```

## Building from Source

### Prerequisites

- Java 21 JDK
- Git

### Build Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/create-virtualworld-compat.git
   cd create-virtualworld-compat
   ```

2. Build the mod:
   ```bash
   ./gradlew build
   ```
   On Windows:
   ```cmd
   gradlew.bat build
   ```

3. The compiled JAR will be in `build/libs/`

## Testing

Run the mod in development:
```bash
./gradlew runClient
```

To test the fix:
1. Place a generator (or other affected block entity) on a Create contraption
2. Assemble the contraption
3. The game should not crash, and the block entity should function normally

## Technical Details

### Root Cause Analysis

**Create 6.0.6** (working):
- `ContraptionBlockEntityBase.startMoving()` → Loads block entities with **real world** reference
- Block entities call `setChanged()` during NBT loading
- No crash because real world supports chunk operations

**Create 6.0.8** (broken):
- New `ClientContraption` class introduced
- Block entities loaded with `VirtualRenderWorld` **before** NBT processing
- Block entities call `setChanged()` during `loadAdditional()`
- `VirtualRenderWorld.getChunk()` throws `UnsupportedOperationException`

### Why This Fix Works

1. `VirtualRenderWorld` is **temporary and render-only** - it never persists to disk
2. Marking chunks as unsaved in a `VirtualRenderWorld` is meaningless
3. Canceling `blockEntityChanged()` in this context is safe and has no side effects
4. The fix applies universally to all block entities, not just specific mods

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Credits

- **Create Team** - For the amazing Create mod
- **MrCrayfish** - For the Refurbished Furniture Mod
- **NeoForge Team** - For the modding framework

## Support

If you encounter issues:
1. Check that you have the correct versions installed
2. Look for similar crash logs in the Issues section
3. Create a new issue with your crash log and mod list

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Submit a pull request with a clear description of your changes

---

**Note**: This mod provides a universal fix for any mod experiencing VirtualRenderWorld-related crashes with Create 6.0.8+. While it was initially created to fix compatibility with MrCrayfish's Furniture Mod, the solution works for all mods with similar block entity behavior.