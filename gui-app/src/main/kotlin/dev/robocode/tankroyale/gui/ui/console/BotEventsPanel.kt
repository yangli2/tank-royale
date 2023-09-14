package dev.robocode.tankroyale.gui.ui.console

import dev.robocode.tankroyale.gui.ansi.AnsiEscapeCode
import dev.robocode.tankroyale.gui.ansi.AnsiTextBuilder
import dev.robocode.tankroyale.gui.client.Client
import dev.robocode.tankroyale.gui.client.ClientEvents
import dev.robocode.tankroyale.gui.model.*

class BotEventsPanel(val bot: Participant) : ConsolePanel() {

    val INDENT_CHARS = 2

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        ClientEvents.apply {
            onTickEvent.subscribe(this@BotEventsPanel) {
                dump(it.events)
            }
            onGameStarted.subscribe(this@BotEventsPanel) { gameStartedEvent ->
                if (gameStartedEvent.participants.any { it.displayName == bot.displayName }) {
                    subscribeToEvents()
                }
            }
            onGameEnded.subscribe(this@BotEventsPanel) {
                unsubscribeEvents()
            }
            onGameAborted.subscribe(this@BotEventsPanel) {
                unsubscribeEvents()
            }
        }
    }

    private fun unsubscribeEvents() {
        ClientEvents.apply {
            onRoundStarted.unsubscribe(this@BotEventsPanel)
            onTickEvent.unsubscribe(this@BotEventsPanel)
            onGameAborted.unsubscribe(this@BotEventsPanel)
            onGameEnded.unsubscribe(this@BotEventsPanel)
        }
    }

    private fun dump(events: Set<Event>) {
        events.forEach { event ->
            when (event) {
                is BotDeathEvent -> dumpBotDeathEvent(event)
                is BotHitWallEvent -> dumpBotHitWallEvent(event)
                is BotHitBotEvent -> dumpBotHitBotEvent(event)
                is BulletFiredEvent -> dumpBulletFiredEvent(event)
                is BulletHitBotEvent -> dumpBulletHitBotEvent(event)
                is BulletHitBulletEvent -> dumpBulletHitBulletEvent(event)
                is BulletHitWallEvent -> dumpBulletHitWallEvent(event)
                is ScannedBotEvent -> dumpScannedBotEvent(event)
                else -> dumpUnknownEvent(event)
            }
        }
    }

    private fun dumpUnknownEvent(event: Event) {
        append("Unknown event: ${event.javaClass.simpleName}", event.turnNumber, CssClass.ERROR)
    }

    private fun createEventAndTurnNumberBuilder(event: Event) =
        createEventNameBuilder(event).text(':')
            .fieldValue("turnNumber", event.turnNumber)

    private fun createEventNameBuilder(event: Event) =
        AnsiTextBuilder().newline().space(INDENT_CHARS).esc(AnsiEscapeCode.CYAN).text(
            when (event) {
                is BotDeathEvent -> if (bot.id == event.victimId) "DeathEvent" else "BotDeathEvent"
                is BulletHitBotEvent -> if (bot.id == event.victimId) "HitByBulletEvent" else "BulletHitBotEvent"
                else -> event.javaClass.simpleName
            }
        )

    private fun createVictimIdBuilder(event: Event, victimId: Int): AnsiTextBuilder {
        val ansi = createEventAndTurnNumberBuilder(event)
        if (bot.id != victimId) {
            ansi.fieldValue("victimId", botIdAndName(victimId))
        }
        return ansi
    }

    private fun AnsiTextBuilder.fieldValue(fieldName: String, value: Any?, indention: Int = 2): AnsiTextBuilder {
        newline().space(indention * INDENT_CHARS).green().text(fieldName).text(": ").default().bold().text(value).reset()
        return this
    }

    private fun AnsiTextBuilder.bulletValues(fieldName: String, bullet: BulletState, indention: Int = 2): AnsiTextBuilder {
        val indent = indention + 1

        val bulletAnsi = AnsiTextBuilder()
            .fieldValue("bulletId", bullet.bulletId, indent)
            if (bot.id != bullet.ownerId) {
                fieldValue("ownerId", botIdAndName(bullet.ownerId), indent)
            }
            fieldValue("power", bullet.power, indent)
                .fieldValue("x", bullet.x, indent)
                .fieldValue("y", bullet.y, indent)
                .fieldValue("direction", bullet.direction, indent)
                .fieldValue("color", bullet.color, indent)

        fieldValue(fieldName, bulletAnsi.toString(), indention)
        return this
    }

    private fun dumpBotDeathEvent(botDeathEvent: BotDeathEvent) {
        dumpVictimIdOnly(botDeathEvent, botDeathEvent.victimId)
    }

    private fun dumpBotHitWallEvent(botHitWallEvent: BotHitWallEvent) {
        if (bot.id == botHitWallEvent.victimId) { // -> no need to dump victimId
            val ansi = createEventAndTurnNumberBuilder(botHitWallEvent)
            append(ansi.toString(), botHitWallEvent.turnNumber)
        }
    }

    private fun dumpVictimIdOnly(event: Event, victimId: Int) {
        if (bot.id != victimId) {
            val ansi = createVictimIdBuilder(event, victimId)
            append(ansi.toString(), event.turnNumber)
        }
    }

    private fun dumpBotHitBotEvent(botHitBotEvent: BotHitBotEvent) {
        if (botHitBotEvent.botId == bot.id) {
            val ansi = createVictimIdBuilder(botHitBotEvent, botHitBotEvent.victimId)
                .fieldValue("energy", botHitBotEvent.energy)
                .fieldValue("x", botHitBotEvent.x)
                .fieldValue("y", botHitBotEvent.y)
                .fieldValue("rammed", botHitBotEvent.rammed)
            append(ansi.toString(), botHitBotEvent.turnNumber)
        }
    }

    private fun dumpBulletFiredEvent(bulletFiredEvent: BulletFiredEvent) {
        dumpBulletOnly(bulletFiredEvent, bulletFiredEvent.bullet)
    }

    private fun dumpBulletHitBotEvent(bulletHitBotEvent: BulletHitBotEvent) {
        if (bulletHitBotEvent.bullet.ownerId == bot.id || bulletHitBotEvent.victimId == bot.id) {
            val ansi = createVictimIdBuilder(bulletHitBotEvent, bulletHitBotEvent.victimId)
                .bulletValues("bullet", bulletHitBotEvent.bullet)
                .fieldValue("damage", bulletHitBotEvent.damage)
                .fieldValue("energy", bulletHitBotEvent.energy)
            append(ansi.toString(), bulletHitBotEvent.turnNumber)
        }
    }

    private fun dumpBulletHitBulletEvent(bulletHitBulletEvent: BulletHitBulletEvent) {
        val bullet = bulletHitBulletEvent.bullet
        val hitBullet = bulletHitBulletEvent.hitBullet

        if (bullet.ownerId == bot.id || hitBullet.ownerId == bot.id) {
            val ansi = createEventAndTurnNumberBuilder(bulletHitBulletEvent)
                .bulletValues("bullet", bullet)
                .bulletValues("hitBullet", hitBullet)
            append(ansi.toString(), bulletHitBulletEvent.turnNumber)
        }
    }

    private fun dumpBulletHitWallEvent(bulletHitWallEvent: BulletHitWallEvent) {
        dumpBulletOnly(bulletHitWallEvent, bulletHitWallEvent.bullet)
    }

    private fun dumpBulletOnly(event: Event, bullet: BulletState) {
        if (bullet.ownerId == bot.id) {
            val ansi = createEventAndTurnNumberBuilder(event)
                .bulletValues("bullet", bullet)
            append(ansi.toString(), event.turnNumber)
        }
    }

    private fun dumpScannedBotEvent(scannedBotEvent: ScannedBotEvent) {
        if (scannedBotEvent.scannedByBotId == bot.id) {
            val ansi = createEventAndTurnNumberBuilder(scannedBotEvent)
                .fieldValue("scannedBotId", botIdAndName(scannedBotEvent.scannedBotId))
                .fieldValue("energy", scannedBotEvent.energy)
                .fieldValue("x", scannedBotEvent.x)
                .fieldValue("y", scannedBotEvent.y)
                .fieldValue("direction", scannedBotEvent.direction)
                .fieldValue("speed", scannedBotEvent.speed)
            append(ansi.toString(), scannedBotEvent.turnNumber)
        }
    }

    private fun botIdAndName(botId: Int): String {
        val bot = Client.getParticipant(botId)
        return "$botId (${bot.name} ${bot.version})"
    }
}