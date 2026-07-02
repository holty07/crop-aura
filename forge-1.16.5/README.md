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
- Forge `1.16.5-36.2.39` or later 36.2.x

## Mappings

This module uses classic MCP mappings (`snapshot`/`20210309-1.16.5`), not Mojang's official
mappings — ForgeGradle 4.1's official-mapping code path has a reproducible bug for 1.16.5
(`NoSuchFileException` while generating `mcp_config-1.16.5-obf_to_srg.tsrg`). Source uses MCP
naming accordingly: `World`, `PlayerEntity`, `getUniqueID()`, `player.world`, etc., rather than
the `Level`/`Player`/`getUUID()` names used in the 1.21.1 NeoForge module.
