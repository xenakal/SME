package com.smart_house;

public abstract class Feature{
    private boolean isActivate;
    private String name;
    private Feature[][] dependences; //condition FNC
    private String parentDependence; //free , mandatory, or, alt


    public Feature(boolean isActivate, String name, Feature[][] dependences, String parentDependence) {
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

    public boolean checkDependences(){
        if (dependences == null) return true;
        for (Feature[] depAnd: dependences) {
            boolean ok = false;
            for(Feature depOr : depAnd){
                if(depOr.isActive()){
                    ok = true;
                    break;
                }
            }
            if(!ok)return false;
        }
        return true;
    }

    /**
     * check if the feature model is respected for the given feature
     */
    public boolean check(){
         return ((!isActive()) || (isActive() && localCheck() && checkDependences()));
    }

    public boolean isActivable(){
        isActivate = true;
        boolean bo = check(); //&& Param.getInstance().getMainFeature().checkDependences();
        isActivate = false;
        return bo;
    }
    public boolean isDeactivable(){
        isActivate = false;
        boolean b =  check() && Param.getInstance().getMainFeature().checkDependences();
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
        if(isActivate || isActivable()){  //isActivable ?
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

    public Feature[][] getDependences() {
        return dependences;
    }

    public void setDependences(Feature[][] f) {
        dependences = f;
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