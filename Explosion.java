public class Explosion extends CircularObject {
    private long timeCreated;


    Explosion(double posX, double posY, double speed, double radius, long timeCreated){
        super(posX, posY, speed, radius);
        this.timeCreated = timeCreated;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
