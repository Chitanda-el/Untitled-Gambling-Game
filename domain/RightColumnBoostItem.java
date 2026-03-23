package domain;

/** This is an example item that boosts the payout of the symbol in the right column. */

public class RightColumnBoostItem extends Item {

    private final double multiplier;

    public RightColumnBoostItem(double multiplier) {
        super(3,
              "Right Column Booster: " + multiplier + "X",
              "Boosts right column payouts by " + multiplier + "x.",
              50);
        this.multiplier = multiplier;
    }

    @Override
    public void onPayoutCalculation(GameEventManager events) {
        events.modifyPatternPayout(Pattern.RIGHT_COLUMN, multiplier);
    }
}