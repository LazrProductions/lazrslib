# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.0] - 2026-08-09

### Added

- Completely rewritten for multi loader, for compatible release on forge, neoforge, and fabric.
- **Added `IPacketContext` passed when handling ``LazrPacket.handleClientside(ctx)`` and ``LazrPacket.handleServerside(ctx)``**
- Added `TimeUtilities` with commonly used functions for getting Unix timestamps and other time related utilities.
- Added additional commonly used math functions to `MathUtilities`.
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