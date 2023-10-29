package game.entities.instance;

import game.entities.general.CircularObject;

/**
 * Class Explosion
 * Contains all methods and variables that is required for a bullet instance.
 */
public class Explosion extends CircularObject {
    private long timeCreated;
    private double finalRadius;

    private final double explosionDurationInSeconds = 0.24;

    public Explosion(double posX, double posY, double speed, double radius) {
        super(posX, posY, speed, radius);
        this.finalRadius = radius;
        this.timeCreated = System.nanoTime();
    }

    /**
     * Updates the radius of the explosion dependant on the time elapsed since creation.
     */
    public void updateRadius() {
        double timeElapsedSinceCreationInSeconds = this.getTimeElapsedSinceCreation() 
            / Math.pow(10, 9);
        System.out.println(timeElapsedSinceCreationInSeconds);

        // Increasing exponential decay to simulate radius expansion rate of an
        // explosion
        double radius = finalRadius * (1 - Math.exp(-1.8 
            * Math.pow(timeElapsedSinceCreationInSeconds / explosionDurationInSeconds, 0.4)));
        System.out.println(radius);
        this.setRadius(radius);
    }

    public double getFinalRadius() {
        return finalRadius;
    }

    /**
     * Determines whether the time elapsed since the explosion
     * was created has surpassed the explosion's lifetime.
     * @return Whether the explosion should no longer be drawn.
     */
    public boolean shouldBeDeleted() {
        return System.nanoTime() - explosionDurationInSeconds
            * Math.pow(10, 9) > this.getTimeCreated();
    }

    public long getTimeCreated() {
        return this.timeCreated;
    }

    public long getTimeElapsedSinceCreation() {
        return System.nanoTime() - this.getTimeCreated();
    }
}
