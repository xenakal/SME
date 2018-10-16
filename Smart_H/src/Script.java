public class Script {

    public static void waitt(int time){
        try {
            Thread.sleep(time);
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

        waitt(10002);
        light_sensor.detect(1);


        waitt(900);
        light_sensor.sensor_off();

        light_sensor.sensor_on();

        light_sensor.detect(1);
        waitt(1000);
        light_sensor.sensor_off();


        /* Sould print:
                sensor is on
                light on
                time out
                light off
                light on
                sensor is off
                light off
                sensor is on
                light is on
                sensor is off
                light is off
         */
    }

}
