public class Smart_Home {
/*
    private Sensor light_sensor;
    private Light living_light;

    public Smart_Home(Sensor sensor, Light light){
        this.light_sensor = sensor;
        this.living_light = light;
    }
*/
    public static void main(String[] args){

        Light living_light = new Light();
        Sensor light_sensor = new Sensor(living_light);

        light_sensor.sensor_on(); // senseur allumé: "sensor is on"
        light_sensor.start();
        light_sensor.detect_movement(); // movement = true
        light_sensor.sensor_off();

    }

}
