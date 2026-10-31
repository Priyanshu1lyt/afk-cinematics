# AFK Cinematics Reimagined

**Author: NotHeartYT**

An independently written Fabric client mod project inspired by the general idea of starting a cinematic camera drift after inactivity. This is **not** the original mod's source code and does not claim exact feature parity.

## Target
- Minecraft 1.21.11
- Java 21
- Fabric Loader 0.18.4+
- Fabric API 0.141.1+1.21.11

## Current implementation
- Detects idle time by tracking player position and view rotation.
- Starts a gentle animated yaw/pitch drift after the configured delay.
- `O` opens the settings screen.
- `P` toggles the feature.
- Settings screen toggles the feature and cycles the AFK delay.
- Stores settings in `config/afk-cinematics-reimagined.properties`.

## Build
Install JDK 21 and Gradle, then run:

```bash
gradle build
```

The output JAR will be in `build/libs/`.

## Important compatibility note
This is a first-pass source project. The Minecraft 1.21.11 Yarn/Mixin signatures may need small adjustments against the exact mappings and Fabric API build installed on your machine. The project has not been compiled or runtime-tested in this environment. Cinematic shot libraries, world-aware camera paths, soundtracks, and any undocumented behaviour of the reference mod are not represented here.

## Build on GitHub (no PC needed)
Upload this whole folder to a GitHub repo, open the Actions tab, run **Build Mod**, then download the `afk-cinematics-jar` artifact.
