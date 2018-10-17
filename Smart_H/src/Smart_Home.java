public class Smart_Home {

    private MotionDetector light_sensor;
    private Light living_light;

    public Smart_Home(MotionDetector sensor, Light light){
        this.light_sensor = sensor;
        this.living_light = light;

    }
}
