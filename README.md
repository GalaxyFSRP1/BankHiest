# Minecraft Bank Heist Plugin

## Overview
Yankton Bank Heist minigame for Spigot/Paper 1.20.1+.
- /bankheist start: Leader + nearby players (max 4) teleported to arena.
- Pick up keycard at door, throw at guard to unlock vault.
- Fight guards (15 kills each), collect money, equip Juggernaut in vault.
- Exfil when all ready, payout split.

## Setup
1. Install Spigot/Paper server.
2. Copy BankHeist.jar to plugins/.
3. Restart server.
4. Set up arena map at coords in config.yml (world).
5. /bankheist start near team.

## Build .jar
```bash
mvn clean package
```
JAR in target/BankHeist-1.0-SNAPSHOT.jar

## GitHub
Push to GitHub, .github/workflows/build.yml auto-builds .jar on push.

## Features
- Team 1-4 players.
- Keycard throw mechanic.
- Per-player kill track.
- Juggernaut netherite + effects.
- Money payout items.
- Fail on team wipe.

Enjoy the heist!

