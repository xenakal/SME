package com.smart_house;

import java.util.List;

/**
 * This class manage the command for the feature model
 * The command have this form :
 * config add/remove room/sensor/actuator/connexion roomName [type] [name]
 * no type  for room
 * no name for manager
 */
public class ConfigCommand implements GeneralCommand{

    public ConfigCommand() {
    }

    @Override
    public void execute(String[] in_arr) {
        try{
            if (in_arr.length != 4 && in_arr.length != 6){
                usage();
            }else{
                SmartHome sh =  SmartHome.getSmartHome();
                String action = in_arr[1];
                String goal = in_arr[2];
                String room = in_arr[3];
                if(!goal.equals("room") && !goal.equals("sensor") && !goal.equals("actuator") && !goal.equals("connexion") ){
                    System.out.println("wrong type : second arg should be room/sensor/actuator/connexion");
                    usage();
                    return;
                }

                switch (action){
                    case "add":
                        switch (goal){
                            case "room": sh.getRoomsMap().put(room, new Rooms(room)); break;
                            case "sensor":
                                AbsSensor sens = Factory.getInstance().makeSensor(in_arr[4], in_arr[5]);
                                sh.getRoomsMap().get(room).addSensor(sens);
                                sh.getSensorMap().put(in_arr[5], sens);
                                if(Param.getInstance().isActiveSubFeature("sensor",in_arr[4])){
                                    Param.getInstance().activeSensor(in_arr[4]);
                                }

                                //sh.getRoomsMap().get(room).makeManagerForUsedDevices();
                                break;
                            case "actuator":
                                Actuator act = Factory.getInstance().makeActuator(in_arr[4], in_arr[5]);
                                sh.getRoomsMap().get(room).addDevice(act);
                                if(Param.getInstance().isActiveSubFeature("actuator",in_arr[4])){
                                    Param.getInstance().activeActuator(in_arr[4]);
                                }
                                String managerType = Enum.convertToFunctionnality(in_arr[4]);
                                if(Param.getInstance().isActiveSubFeature("functionality",managerType)){
                                    Param.getInstance().activeFunctonality(managerType);
                                }
                                break;
                            case "connexion":
                                if(in_arr.length != 6){
                                    System.out.println("no enought arguments");
                                    System.out.println("Usage connexion : config add/remove connexion managerRoomName actuatorType SensorName");
                                    return;
                                }
                                Enum.Manager type = Enum.convertToManager(in_arr[4]);
                                String sensorName = in_arr[5];
                                AbsSensor sensor = sh.getSensorMap().get(sensorName);
                                if(type == null || sensor == null){
                                    System.out.println("Usage connexion : config add/remove connexion managerRoomName actuatorType SensorName");
                                    return;
                                }
                                ManagerFeature man = sh.getRoomsMap().get(room).getManagerOfType(type);
                                sensor.attach(man);
                                break;
                            default:usage();break;
                        }
                        break;
                    case "remove" :
                        switch (goal){
                            case "room": sh.getRoomsMap().remove(room, new Rooms(room)); break;
                            case "sensor":
                                String name = in_arr[5];
                                List<AbsSensor> sensList = sh.getRoomsMap().get(room).getSensorOfType(Enum.convertToSensor(in_arr[4]));
                                for (AbsSensor s : sensList){
                                    if(s.getName().equals(name)){
                                        sh.getRoomsMap().get(room).removeSensor(s);
                                        //check if the feature model will still be valid
                                        if(!Param.getInstance().isActivailbleSubFeature("sensor",in_arr[4])){
                                            System.out.println("Error, the suppresion of this sensordon't fit with the feature model constraints. Disactive the appropriete feature before removing.");
                                            sh.getRoomsMap().get(room).addSensor(s);
                                            Param.getInstance().activeSensor(in_arr[4]);
                                        }
                                    }
                                    break;
                                }
                                break;
                            case "actuator":
                                name = in_arr[5];
                                List<Actuator> list = sh.getRoomsMap().get(room).getActuatorOfType(Enum.convertToActu(in_arr[4]));
                                for (Actuator a : list){
                                    if(a.getName().equals(name)){
                                        sh.getRoomsMap().get(room).removeDevice(a);
                                        //check if the feature model will still be valid
                                        if(!Param.getInstance().isActivailbleSubFeature("actuator",in_arr[4])){
                                            System.out.println("Error, the suppresion of this device don't fit with the feature model constraints. Disactive the appropriete feature before removing.");
                                            sh.getRoomsMap().get(room).addDevice(a);
                                            Param.getInstance().activeActuator(in_arr[4]);
                                        }
                                    }
                                    break;
                                }
                                break;
                            case "connexion":
                                if(in_arr.length != 6){
                                    System.out.println("no enought arguments");
                                    System.out.println("Usage connexion : config add/remove connexion managerRoomName actuatorType SensorName");
                                    return;
                                }
                                Enum.Manager type = Enum.convertToManager(in_arr[4]);
                                String sensorName = in_arr[5];
                                AbsSensor sensor = sh.getSensorMap().get(sensorName);
                                if(type == null || sensor == null){
                                    System.out.println("Usage connexion : config add/remove connexion managerRoomName actuatorType SensorName");
                                    return;
                                }
                                ManagerFeature man = sh.getRoomsMap().get(room).getManagerOfType(type);
                                sensor.detach(man);
                                break;

                            default:usage();break;
                        }
                        break;
                    default: usage(); break;
                }
            }
        }catch (Exception e){
            System.out.println("Exception in configcommmand : " + e.toString());
            usage();
        }
    }

    public void usage(){
        System.out.println("Usage CONFIG : config add/remove room/sensor/actuator/connexion roomName [type] [name]");
    }
}
