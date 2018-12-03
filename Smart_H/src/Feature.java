import java.util.List;

public abstract class Feature{
    private boolean isActivate;
    private String name;
    private List<Feature> dependences;
    private String parentDependence; //free , mandatory, or, alt


    public Feature(boolean isActivate, String name, List<Feature> dependences, String parentDependence) {
        this.isActivate = isActivate;
        this.name = name;
        this.dependences = dependences;
        this.parentDependence = parentDependence;
    }

    public Feature(String name, String parentDependence) {
        this.isActivate = false;
        this.name = name;
        this.dependences = null;
        this.parentDependence = parentDependence;
    }

    public abstract boolean localCheck();

    public boolean checkDependeces(){
        for (Feature dep: dependences) {
            if(!dep.isActive()){
                return false;
            }
        }
        return true;
    }

    /**
     * check if the feature model is respected for the given feature
     */
    public boolean check(){

         return (!isActive()) || (isActive() && localCheck() && checkDependeces());
    }

    public boolean isActivable(){
        isActivate = true;
        boolean b = check();
        isActivate = false;
        return b;
    }
    public boolean isDeactivable(){
        isActivate = false;
        boolean b =  check();
        isActivate = true;
        return b;
    }

    /**
     * indicate if the feature is curretly activate
     */
    public boolean isActive() {
        return isActivate;
    }

    protected void active(){
        if(localCheck()){
            isActivate = true;
        }else{
            System.out.println("Error : Invalid activation for feature "+ name);
        }
    }
    protected void deactivate() {
        isActivate = false;
    }

    public String getName() {
        return name;
    }

    public List<Feature> getDependences() {
        return dependences;
    }

    public void addDependences(Feature f) {
        dependences.add(f);
    }

    public String getParentDependence() {
        return parentDependence;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Feature){
            Feature f = (Feature) obj;
            return this.getName().equals(f.getName());
        }else {
            return false;
        }

    }
}