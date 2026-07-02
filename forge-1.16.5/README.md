# Crop Aura (Minecraft 1.16.5 / Forge)

This is a Minecraft 1.16.5 port of Crop Aura, built against Forge (not NeoForge — NeoForge
doesn't exist for this Minecraft version) using ForgeGradle 4.1.

It's a self-contained Gradle build with its own wrapper, separate from the root project. The
two can't share a single Gradle invocation: the root `1.21.1` module needs Gradle 8.x for the
NeoForge ModDev plugin, while ForgeGradle for 1.16.5 needs an older Gradle — FG3 only supports
Gradle below 6.0, FG4 supports 6.8.1+, so this module uses FG4 with the bundled Gradle 6.9.4
wrapper.

## Building

```
cd forge-1.16.5
./gradlew build
```

## Requirements

- Java 8 (Minecraft 1.16.5 / Forge do not support newer JDKs)
- Forge `1.16.5-36.1.0` or later

## Mappings

This module uses Mojang's official mappings channel (`official`/`1.16.5`). At this Minecraft
version Mojang's own internal names still matched MCP's class names (the `World`→`Level`,
`PlayerEntity`→`Player` renames only landed in Mojang's mappings starting with 1.17), so class
names read the same as classic MCP tutorials — only some method/field names are Mojang-specific:
`getUUID()`, `blockPosition()`, `player.level`, `.random`, `isClientSide`.

`org.gradle.workers.max=1` is set in `gradle.properties` to serialize Gradle's worker execution;
without it, ForgeGradle's mapping-generation step hit a reproducible `NoSuchFileException` in CI
(writing a generated `.tsrg` file into a directory that wasn't created yet), which looked like a
concurrency race specific to multi-core runners.
