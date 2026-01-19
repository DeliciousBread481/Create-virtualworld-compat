# Project Structure

This document describes the organization and purpose of files in this project.

## Root Directory

```
create-virtualworld-compat/
├── src/                          # Source code
├── build/                        # Compiled output (git-ignored)
├── gradle/                       # Gradle wrapper files
├── .gradle/                      # Gradle cache (git-ignored)
├── run/                          # Minecraft development environment (git-ignored)
│
├── build.gradle                  # Gradle build configuration
├── gradle.properties             # Project properties and dependency versions
├── settings.gradle               # Gradle settings
├── gradlew / gradlew.bat        # Gradle wrapper scripts
│
├── README.md                     # Main project documentation
├── LICENSE                       # MIT License
├── CHANGELOG.md                  # Version history and changes
├── CONTRIBUTING.md               # Contribution guidelines
├── PROJECT_STRUCTURE.md          # This file
│
├── .gitignore                    # Git ignore patterns
└── .gitattributes               # Git attributes
```

## Source Code Structure

```
src/main/
├── java/
│   └── com/agent772/create_virtualworld_compat/
│       ├── CreateVirtualWorldCompat.java         # Main mod class
│       └── mixin/
│           └── VirtualRenderWorldMixin.java      # Core compatibility fix
│
├── resources/
│   ├── create_virtualworld_compat.mixins.json    # Mixin configuration
│   └── assets/
│       └── examplemod/                           # Placeholder assets (can be removed)
│
└── templates/
    └── META-INF/
        └── neoforge.mods.toml                    # Mod metadata (templated)
```

## Key Files

### Main Code Files

- **`CreateVirtualWorldCompat.java`**
  - Main mod entry point
  - Initializes the mod and logs startup message
  - Actual fix is in the mixin class

- **`VirtualRenderWorldMixin.java`**
  - **THE CORE FIX** - Prevents crashes by canceling `blockEntityChanged()` in VirtualRenderWorld
  - Targets: `Level.blockEntityChanged()`
  - Uses Mixin `@Inject` with cancellation

### Configuration Files

- **`build.gradle`**
  - Defines project dependencies (Create, Flywheel, MrCrayfish Furniture)
  - Configures NeoForge MDK
  - Sets up Mixin configuration

- **`gradle.properties`**
  - Version numbers for all dependencies
  - Mod metadata (name, version, author, description)
  - Minecraft and NeoForge versions

- **`neoforge.mods.toml`** (template)
  - Mod metadata for NeoForge
  - Dependency declarations (Create, Flywheel, MrCrayfish Furniture)
  - Mixin configuration reference

- **`cfm_create_compat.mixins.json`**
  - Mixin configuration file
  - Registers VirtualRenderWorldMixin
  - Sets compatibility level to Java 21

## Build Output

When you run `./gradlew build`, the following are generated:

```
build/
├── libs/
│   └── create_virtualworld_compat-1.0.0.jar    # The mod JAR file
├── classes/                                     # Compiled Java classes
├── resources/                                   # Processed resources
└── tmp/                                         # Temporary build files
```

## Development Workflow

### Building
```bash
./gradlew build
```

### Running in Development
```bash
./gradlew runClient    # Launch Minecraft client
./gradlew runServer    # Launch dedicated server
```

### Cleaning
```bash
./gradlew clean
```

### Debugging
- Set breakpoints in your IDE
- Run the Gradle task `runClient` in debug mode
- The mixin will be applied at class load time

## Mixin System

This mod uses SpongePowered Mixin to modify Minecraft/Create code at runtime:

1. **Mixin Config**: `src/main/resources/create_virtualworld_compat.mixins.json`
2. **Mixin Classes**: `src/main/java/com/agent772/create_virtualworld_compat/mixin/`
3. **Registration**: Declared in `neoforge.mods.toml` under `[[mixins]]`

The mixin targets `Level.class` and injects at the `HEAD` of `blockEntityChanged()`, canceling execution when the level is a `VirtualRenderWorld`.

## Dependencies

### Runtime Dependencies (Required)
- **Minecraft**: 1.21.1
- **NeoForge**: 21.1.209+
- **Create**: 6.0.8+
- **Flywheel**: 1.0.4+ (Create dependency)

### Optional Dependencies
- **MrCrayfish's Refurbished Furniture Mod**: The mod that inspired this fix (but fix works universally)
- **JEI** (Just Enough Items): Development only
- **Jade**: Development only

### Build Dependencies
- **Mixin**: 0.8.5+ (provided by NeoForge)
- **Parchment Mappings**: For better parameter names

## Version Management

Version numbers are managed in `gradle.properties`:

```properties
mod_version=1.0.0              # This mod's version
create_version=6.0.8-168       # Create version
flywheel_version=1.0.5         # Flywheel version
minecraft_version=1.21.1       # Minecraft version
```

Update these when upgrading dependencies.

## Git Workflow

### Ignored Files
- `build/` - Compiled output
- `.gradle/` - Gradle cache
- `run/` - Development environment
- `temp_decompile/` - Temporary decompilation
- `.vscode/` - Editor settings

### Tracked Files
- All source code (`src/`)
- Build configuration files
- Documentation (`.md` files)
- License files

## Testing Checklist

Before releasing a new version:

1. ✅ Build succeeds: `./gradlew build`
2. ✅ Game launches: `./gradlew runClient`
3. ✅ Place affected block entity on contraption
4. ✅ Assemble contraption - should not crash
5. ✅ Block entity functions normally on contraption
6. ✅ Test with multiple mods if possible
7. ✅ Check logs for errors/warnings
8. ✅ Update CHANGELOG.md with changes

## Further Reading

- [NeoForge Documentation](https://docs.neoforged.net/)
- [Mixin Documentation](https://github.com/SpongePowered/Mixin/wiki)
- [Create Wiki](https://github.com/Creators-of-Create/Create/wiki)
- [Gradle Documentation](https://docs.gradle.org/)
