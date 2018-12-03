public interface Actuator{

    public Enum.Actuator getType();
    public boolean isActive();
    public void active();
    public void deactive();

}
