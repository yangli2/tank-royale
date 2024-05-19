package dev.robocode.tankroyale.gui.settings

import java.util.*

object ConfigSettings : PropertiesStore("Robocode Misc Settings", "config.properties") {

    const val DEFAULT_TPS = 30
    const val SOUNDS_DIR = "sounds/"

    private const val BOT_DIRECTORIES = "bot-directories"
    private const val GAME_TYPE = "game-type"
    private const val TPS = "tps"

    private const val ENABLE_SOUNDS = "enable-sounds"
    private const val ENABLE_GUNSHOT_SOUND = "enable-gunshot-sound"
    private const val ENABLE_BULLET_HIT_SOUND = "enable-bullet-hit-sound"
    private const val ENABLE_WALL_COLLISION_SOUND = "enable-wall-collision-sound"
    private const val ENABLE_BOT_COLLISION_SOUND = "enable-bot-collision-sound"
    private const val ENABLE_BULLET_COLLISION_SOUND = "enable-bullet-collision-sound"
    private const val ENABLE_DEATH_EXPLOSION_SOUND = "enable-death-explosion-sound"

    private const val BOT_DIRS_SEPARATOR = ','

    var botDirectories: List<BotDirectoryConfig>
        get() {
            load()
            return getBotDirectoryConfigs()
        }
        set(value) {
            setBotDirectoryConfigs(value)
            save()
        }


    var gameType: GameType
        get() {
            val displayName = properties.getProperty(GAME_TYPE, GameType.CLASSIC.displayName)
            return GameType.from(displayName)
        }
        set(value) {
            properties.setProperty(GAME_TYPE, value.displayName)
        }

    var tps: Int
        get() {
            load()

            val tpsStr = properties.getProperty(TPS)?.lowercase(Locale.getDefault())
            if (tpsStr in listOf("m", "ma", "max")) {
                return -1 // infinite tps
            }
            return try {
                tpsStr?.toInt() ?: DEFAULT_TPS
            } catch (e: NumberFormatException) {
                DEFAULT_TPS
            }
        }
        set(value) {
            properties.setProperty(TPS, value.toString())
            save()
        }

    var enableSounds: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_SOUNDS)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_SOUNDS, if (value) "true" else "false")
            save()
        }

    var enableGunshotSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_GUNSHOT_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_GUNSHOT_SOUND, if (value) "true" else "false")
            save()
        }

    var enableBulletHitSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_BULLET_HIT_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_BULLET_HIT_SOUND, if (value) "true" else "false")
            save()
        }

    var enableWallCollisionSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_WALL_COLLISION_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_WALL_COLLISION_SOUND, if (value) "true" else "false")
            save()
        }

    var enableBotCollisionSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_BOT_COLLISION_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_BOT_COLLISION_SOUND, if (value) "true" else "false")
            save()
        }

    var enableBulletCollisionSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_BULLET_COLLISION_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_BULLET_COLLISION_SOUND, if (value) "true" else "false")
            save()
        }

    var enableDeathExplosionSound: Boolean
        get() {
            load()
            return properties.getProperty(ENABLE_DEATH_EXPLOSION_SOUND)?.lowercase() != "false"
        }
        set(value) {
            properties.setProperty(ENABLE_DEATH_EXPLOSION_SOUND, if (value) "true" else "false")
            save()
        }

    private fun getBotDirectoryConfigs(): List<BotDirectoryConfig> {
        val botDirectoryConfigs = mutableListOf<BotDirectoryConfig>()

        var lastPath: String? = null
        properties.getProperty(BOT_DIRECTORIES, "")
            .split(BOT_DIRS_SEPARATOR)
            .filter { it.isNotBlank() }
            .forEach { path ->
                if ("true".equals(path, ignoreCase = true)) {
                    botDirectoryConfigs.add(BotDirectoryConfig(lastPath!!, true))
                } else if ("false".equals(path, ignoreCase = true)) {
                    botDirectoryConfigs.add(BotDirectoryConfig(lastPath!!, false))
                } else {
                    lastPath = path
                }
            }
        return botDirectoryConfigs
    }

    private fun setBotDirectoryConfigs(botDirectoryConfigs: List<BotDirectoryConfig>) {
        val stringBuffer = StringBuilder()
        botDirectoryConfigs.filter { it.path.isNotBlank() }.forEach() { botDirectoryConfig ->
            stringBuffer
                .append(botDirectoryConfig.path).append(BOT_DIRS_SEPARATOR)
                .append(if (botDirectoryConfig.enabled) "true" else "false").append(BOT_DIRS_SEPARATOR)
        }
        properties.setProperty(BOT_DIRECTORIES, stringBuffer.toString().trimEnd(BOT_DIRS_SEPARATOR))
    }
}