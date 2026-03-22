package util;

import java.util.Random;

/**
 * Seeded random number generator
 * 
 * All random decisions UGG must go through this class rather than
 * constructing their own instances so that seeding allows events to be
 * deterministic and reproducible.
 * 
 * It produces a random but deterministic output in the form of a int in the
 * range [0, 100]
 */
public class RandomNumGenerator {
    private final long seed;
    private final Random random;
    
    public RandomNumGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }
    
    // Accessor: returns the seed used
    public long getSeed() {
        return seed;
    }
}
