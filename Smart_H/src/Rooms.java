public class Rooms {


    private MotionDetector[] light_sensor;
    private Light[] living_light;

    public Rooms(MotionDetector[] sensor, Light[] light){
        this.light_sensor = sensor;
        this.living_light = light;

    }

}
