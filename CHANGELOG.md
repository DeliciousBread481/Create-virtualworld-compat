# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-01-19

### Added
- Initial release
- Universal fix for VirtualRenderWorld crashes in Create 6.0.8+
- Mixin targeting Level.blockEntityChanged() to prevent crashes when block entities call setChanged() during contraption assembly
- Full compatibility with MrCrayfish's Refurbished Furniture Mod
- Support for any mod with block entities that trigger chunk updates during initialization

### Fixed
- Crash when placing block entities on Create contraptions in Create 6.0.8+
- UnsupportedOperationException in VirtualRenderWorld.getChunk()
- Contraption assembly failures with furniture generators and other powered block entities

### Technical Details
- Targets Minecraft 1.21.1 with NeoForge 21.1.209+
- Compatible with Create 6.0.8-168 and later
- Uses Mixin 0.8.5+ for bytecode modification
- Minimal performance impact (single instanceof check per blockEntityChanged call)
