
/**
 * Interface for classes how manage the activation/disactivation of devises
 * according to information send by the sensors
 */

public interface FeatureManager {
    public void react(Info info);
    public void add(Rooms r);
}
