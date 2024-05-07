package top.lanscarlos.vulpecula.applicative

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer

/**
 * Vulpecula
 * top.lanscarlos.vulpecula.applicative
 *
 * @author Lanscarlos
 * @since 2023-08-21 14:26
 */
class PlayerApplicative(source: Any) : AbstractApplicative<Player>(source) {

    override fun transfer(source: Any, def: Player?): Player? {
        return when (source) {
            is Player -> source
            is OfflinePlayer -> source.player
            is ProxyPlayer -> source.castSafely()
            is String -> Bukkit.getPlayerExact(source)
            else -> def
        }
    }

    companion object {
        fun Any.applicativePlayer() = PlayerApplicative(this)
    }
}