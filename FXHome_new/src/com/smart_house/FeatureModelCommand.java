package com.smart_house;
/**
 * This class manage the command for the feature model
 * The command have this form :
 * feature_model isActivate/activate/disactivate room/sensor/actuator/functionality  name
 */
public class FeatureModelCommand implements GeneralCommand{

    public FeatureModelCommand() {
    }

    @Override
    public void execute(String[] in_arr) {
        try{
            if (in_arr.length != 4){
                usage();
            }else{
                Param param =  Param.getInstance();
                String action = in_arr[1];
                String feature = in_arr[2];
                String name = in_arr[3];
                if(!feature.equals("room") && !feature.equals("sensor") && !feature.equals("actuator") && !feature.equals("functionality")){
                    usage();
                }

                switch (action){
                    case "isActivate":
                        boolean b = param.isActiveSubFeature(feature,name);
                        System.out.println(b);
                        break;
                    case "activate" :
                        switch (feature){
                            case "room": param.activeRoom(name); break;
                            case "sensor": param.activeSensor(name); break;
                            case "actuator": param.activeActuator(name); break;
                            case "functionality": param.activeFunctonality(name); break;
                            default:usage();break;
                        }break;
                    case "disactivate" :
                        switch (feature){
                            case "room": param.deactiveRoom(name); break;
                            case "sensor": param.deactiveSensor(name); break;
                            case "actuator": param.deactiveActuator(name); break;
                            case "functionality": param.deactiveFunctonality(name); break;
                            default:usage();break;
                        }break;
                    default: usage(); break;
                }
            }
        }catch (Exception e){
            System.out.println("Exception in  FeaturemodelCommand : " + e.toString());
            usage();
        }
    }

    public void usage(){
        System.out.println("Usage FEATURE_MODEL: featureModel isActivate/activate/disactivate   sensor/actuator/functionality  name");
    }
}
