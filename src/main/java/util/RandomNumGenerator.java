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
    
    /**
     * Constructs a new RandomNumGenerator with the specified seed.
     * 
     * @param seed the seed for the random number generator
     */
    public RandomNumGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }
    
    /**
     * Returns a random integer uniformly distributed in [0, bound).
     * 
     * @param bound the upper bound
     * @return a random integer in the range [0, bound)
     */
	public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * Returns a random double uniformly distributed in the range [0.0, 1.0).
     * 
     * @return a random double in the range [0.0, 1.0)
     */
    public double nextDouble() {
        return random.nextDouble();
    }
	
    /**
     * Returns the seed used to initialize the random number generator.
     * 
     * @return the seed used by the random number generator
     */
    public long getSeed() {
        return seed;
    }
}
