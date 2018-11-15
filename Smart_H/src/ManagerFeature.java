
/**
 * Interface for classes how manage the activation/disactivation of devises
 * according to information send by the sensors
 */

public interface ManagerFeature {
    public void react(Info info);
    public void add(Rooms r);
    public void remove(Rooms r);
    public String ToString();

    /**
     * When the content of the rooms are modified, update the reaction of the manager
     * @modify
     */
    public void update();
}
