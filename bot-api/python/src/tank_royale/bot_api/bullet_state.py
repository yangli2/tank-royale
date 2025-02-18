from dataclasses import dataclass

from tank_royale.bot_api.color import Color

@dataclass(frozen=True)
class BulletState:
    """Represents the state of a bullet that has been fired by a bot."""

    bullet_id: int      # Unique id of the bullet.
    owner_id: int       # Id of the bot that fired the bullet.
    power: float        # Bullet firepower level.
    x: float            # X coordinate.
    y: float            # Y coordinate.
    direction: float    # Direction in degrees.
    color: Color        # Color of the bullet.

    def get_speed(self) -> float:
        """Returns the speed of the bullet measured in units per turn.

        Returns:
            The speed of the bullet measured in units per turn.
        """
        return 20 - 3 * self.power
