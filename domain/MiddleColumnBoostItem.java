package domain;

/** This is an example item that boosts the payout of the symbol in the middle column. */

public class MiddleColumnBoostItem extends Item {

    private final double multiplier;

    public MiddleColumnBoostItem(double multiplier) {
        super(2,
              "Middle Column Booster: " + multiplier + "X",
              "Boosts middle column payouts by " + multiplier + "x.",
              50);
        this.multiplier = multiplier;
    }

    @Override
    public void onPayoutCalculation(GameEventManager events) {
        events.modifyPatternPayout(Pattern.MIDDLE_COLUMN, multiplier);
    }
}