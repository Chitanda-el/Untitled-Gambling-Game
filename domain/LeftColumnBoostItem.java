package domain;

/** This is an example item that boosts the payout of the symbol in the left column. */

public class LeftColumnBoostItem extends Item {

    private final double multiplier;

    public LeftColumnBoostItem(double multiplier) {
        super(1,
              "Left Column Booster: " + multiplier + "X",
              "Boosts left column payouts by " + multiplier + "x.",
              50,
			  1);
        this.multiplier = multiplier;
    }

    @Override
    public void onPayoutCalculation(GameEventManager events) {
        events.modifyPatternPayout(Pattern.LEFT_COLUMN, multiplier);
    }
}