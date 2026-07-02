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
- Forge `1.16.5-36.2.39` or later

## Mappings

This module uses Mojang's official mappings channel (`official`/`1.16.5`). At this Minecraft
version Mojang's own internal names mostly matched MCP's names for classes (the `World`→`Level`,
`PlayerEntity`→`Player` renames only landed starting with 1.17), but some *methods* already used
Mojang's own naming even at 1.16.5, verified against real 1.16.5 official-mapped mod source:
`PlayerEntity#displayClientMessage` (not `sendStatusMessage`),
`IGrowable#isValidBonemealTarget`/`isBonemealSuccess`/`performBonemeal` (not
`canGrow`/`canUseBonemeal`/`grow`), and `KeyBinding#consumeClick()` (not `isPressed()`) — plus
`getUUID()`, `blockPosition()`, `player.level`, `.random`, `isClientSide`.

## Known ForgeGradle 4 bug and the priming step

ForgeGradle 4 has an unresolved upstream bug generating mapping files for Minecraft 1.16.5
(`MinecraftForge/ForgeGradle#740`): it throws `NoSuchFileException` trying to write a generated
`.tsrg` mapping file because it never creates the file's parent directory itself. This reproduces
100% of the time on a clean Gradle cache, regardless of mapping channel (`official` or MCP
`snapshot`/`stable`) or Forge version.

The documented workaround: ForgeGradle 3 (older, requires Gradle below 6.0) generates the same
mapping files correctly. Once they exist in the shared Gradle cache, ForgeGradle 4 finds them and
works fine. `forge-1.16.5-priming/` (at the repo root, alongside this module) is a throwaway FG3
sub-project whose only job is to force that generation — the CI workflow runs it first, sharing
the same `GRADLE_USER_HOME`, before running the real FG4 build in this module. If you're building
locally and hit the same `NoSuchFileException`, run
`cd forge-1.16.5-priming && ./gradlew dependencies --configuration compileClasspath` once first.
