# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.1] - 2026-08-18

### Added
- Added several utility methods to `InputAction` for checks and consuming.

### Fixed

- Removed render shader overrides when drawing from several functions in `ScreenUtilities`
- Removed render shader overrides when drawing from several functions in `FontUtilities`


## [1.2.0] - 2026-08-09

### Added

- Completely rewritten for multi loader, for compatible release on forge, neoforge, and fabric.
- **Added `IPacketContext` passed when handling ``LazrPacket.handleClientside(ctx)`` and ``LazrPacket.handleServerside(ctx)``**
- Added `TimeUtilities` with commonly used functions for getting Unix timestamps and other time related utilities.
- Added additional commonly used math functions.
  - `lerp(min, max, time)`
  - `lerp01(time)`
- Added additional component utility functions for converting components to/from json.
- `InteractableOverlay` now receives ticks through the `InteractableOverlay.tick()` function.
- Added some cube drawing functions in `RenderUtilities`
- Added several alternates existing functions in `FontUtilities` to allow for using `String` and `Component` instead of `List<Componet>`
- Added some entity utilities to ``EntityUtilities``

### Changed

- **Renamed `ParameterizedLazrPacket` to `LazrPacket`**
- Moved several text-related functions from `ScreenUtilities` to `FontUtilies`
- Renamed `BlitCoordinates` to `ScreenCoordinate`
- Moved `lazrslib.client.font.ComponentUtilities` to `lazrslib.common.component.ComponentUtilities`
- Renamed `lazrslib.client.ui.OnClickFunction` to `lazrslib.client.IOnClickFunction`

### Fixed

- Fixed several typos in function and class names.

### Removed

- **Removed `ThreadsafeLazrPacket`**
- **Removed several redundant packet registering functions in ``LazrNetwork`` they've now been unified into `register(packet, decoder)`**


## [1.1.1] - 2024-08-22

### Added

- Added `TagUtilities` with commonly used functions for creating/modifying nbt.

### Fixed

- Fixed an occasional crash with `LazrNetwork` on servers.

## [1.1.0] - 2024-06-26

### Added

- Added simple config API for creating custom configs.
- Added `LevelUtilities` which adds some commonly used functions for modifying the world.

### Fixed

- Fixed some inconsistencies between Minecraft versions.

## [1.0.0] - 2024-06-24

### Added

- Everything