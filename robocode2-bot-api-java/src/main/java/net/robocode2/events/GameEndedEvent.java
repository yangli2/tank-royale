package net.robocode2.events;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.robocode2.BotResults;

import java.util.List;

/** Event occurring when game has just ended */
@Value
@EqualsAndHashCode(callSuper = true)
@Builder
public class GameEndedEvent extends GameEvent {

  /** Number of rounds played */
  int numberOfRounds;

  /** Results of the battle */
  List<BotResults> results;
}
