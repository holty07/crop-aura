# Crop Aura (Minecraft 1.16.5 / Forge)

This is a Minecraft 1.16.5 port of Crop Aura, built against Forge (not NeoForge — NeoForge
doesn't exist for this Minecraft version) using ForgeGradle 3.

It's a self-contained Gradle build with its own wrapper, separate from the root project. The
two can't share a single Gradle invocation: the root `1.21.1` module needs Gradle 8.x for the
NeoForge ModDev plugin, while ForgeGradle 3.x (required for 1.16.5) only works on Gradle 6.x.

## Building

```
cd forge-1.16.5
./gradlew build
```

## Requirements

- Java 8 (Minecraft 1.16.5 / Forge do not support newer JDKs)
- Forge `1.16.5-36.2.39` or later 36.2.x
