public class Script {

    public static void waitt(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        Light living_light = new Light();
        Sensor light_sensor = new Sensor(living_light);
        Smart_Home sh = new Smart_Home(light_sensor,living_light);

        light_sensor.sensor_on(); // senseur allumé: "sensor is on" (run en boucle jusqu'au sensor_off)

        light_sensor.detect(1); // movement = true

        waitt();

        light_sensor.sensor_off();

    }

}
