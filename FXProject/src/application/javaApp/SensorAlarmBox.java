package application.javaApp;

public class SensorAlarmBox extends AbsSensor {
    final int CODESIZE = 4;
    final int NUMBER_TRIALS = 3;
    private int code = "1234".hashCode();
    private int trials = NUMBER_TRIALS;
    private int isGoodCode;


    public SensorAlarmBox(String name) {
        super(name);
    }

    //return true if the new code is set
    public boolean setNewCode(int oldcode , int newcode){
        String old = Integer.toString(oldcode);
        if(old.hashCode() == code){
            code = Integer.toString(newcode).hashCode();
            //isGoodCode = codeValue.newCode ;
            //this.advertise();
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Enum.Sensor getType() {
        return Enum.Sensor.alarmBox;
    }

    @Override
    void reset() {
        code = "1234".hashCode();
    }

    @Override
    void detect(int value) {
        String val = Integer.toString(value);
        if(val.hashCode() == code){
            isGoodCode = 1 ;
            trials = NUMBER_TRIALS;
            this.advertise();
        }else {
            trials --;
            if(trials <= 0){
                isGoodCode = 0;
                this.advertise();
            }
        }
    }

    @Override
    Info makeinfo() {
        return new Info("alarm", isGoodCode);
    }

    @Override
    public String toString(){
        return "      Alarm Box : " + super.toString();
    }
}
