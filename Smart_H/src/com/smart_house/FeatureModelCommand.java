package com.smart_house;
/**
 * This class manage the command for the feature model
 * The command have this form :
 * feature_model isActivate/activate/disactivate room/sensor/actuator/functionality  name
 */
public class FeatureModelCommand implements GeneralCommand{
    @Override
    public void execute(String[] in_arr) {
        if (in_arr.length != 4){
           usage();
        }else{
            Param param =  Param.getInstance();
            String action = in_arr[1];
            String feature = in_arr[2];
            String name = in_arr[3];
            if(!feature.equals("Room") && !feature.equals("Sensor") && !feature.equals("Actuator") && !feature.equals("Functionality")){
                usage();
            }

            switch (action){
                case "isActivate":
                    boolean b = param.isActiveSubFeature(feature,name);
                    System.out.println(b);
                    break;
                case "activate" :
                    switch (feature){
                        case "Room": param.activeRoom(name); break;
                        case "Sensor": param.activeSensor(name); break;
                        case "Actuator": param.activeActuator(name); break;
                        case "Functionality": param.activeFunctonality(name); break;
                        default:usage();break;
                    }break;
                case "disactivate" :
                    switch (feature){
                        case "Room": param.deactiveRoom(name); break;
                        case "Sensor": param.deactiveSensor(name); break;
                        case "Actuator": param.deactiveActuator(name); break;
                        case "Functionality": param.deactiveFunctonality(name); break;
                        default:usage();break;
                    }break;
                default: usage(); break;
            }
        }
    }

    private void usage(){
        System.out.println("Usage : feature_model isActivate/activate/disactivate   Room/Sensor/Actuator/Functionality  name");
    }
}
