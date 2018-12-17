package com.smart_house;

import java.util.ArrayList;
import java.util.List;

public class FeatureCompo extends Feature {
    private List<Feature> child;

    public FeatureCompo(boolean isActivate, String name, Feature[][] dependences, String parentDependence) {
        super(isActivate, name, dependences, parentDependence);
        this.child = new ArrayList<Feature>();
    }

    public FeatureCompo(String name, String parentDependence) {
        super(name, parentDependence);
        this.child = new ArrayList<Feature>();
    }

    public void add(Feature f){
        if(!child.contains(f)){child.add(f);}
    }

    public void remove(Feature f){
        child.remove(f);
    }

    public List<Feature> getChild() {
        return child;
    }

    public Feature getOneChild(String name){
        for(Feature c : this.getChild()){
            if(c.getName().equals(name))return c;
        }
        System.out.println("Error in parametrisation : no child with name :"+ name);
        return null;
    }


    @Override
    public boolean localCheck(){return true;}

    @Override
    public boolean check() {
        if (super.check() && child != null && !child.isEmpty()){
            //check that the child are correctly active
            boolean orActive = false;
            boolean altActive = false;
            int or = 0;
            int alt = 0;
            for(Feature c: this.getChild()){
                    switch (c.getParentDependence()){
                        case "mandatory" : if(!c.isActive() || !c.check()){return false;} break;
                        case "free":if(c.isActive() && !c.check()) return false; break;
                        case "or":
                            orActive = true ;
                            if(c.isActive()){  or ++; }
                            if(c.isActive() && !c.check()) return false;
                            break;
                        case "alt" :
                            altActive = true;
                            if(c.isActive()){ alt ++; }
                            if(c.isActive() && !c.check()) return false;
                            break;
                        default: System.out.println("Error in Feature : Invalid kind of dependence");
                    }
            }
            if (alt != 1){return !altActive;}  //TODO == 1? pour les alternative est ce qu'il faut necessairement l'une des option?
            return !orActive || or != 0;
        }
        return false;
    }

    @Override
    public void active() {
        super.active();

    }

    @Override
    public void deactivate() {
        for (Feature c : this.getChild()){
            c.deactivate();
        }
        super.deactivate();
    }

    //active un enfant et le parent si necessaire
    public void activeChild(Feature c) {
        if(!this.child.contains(c)){
            System.out.println("Error in activeChild : no child "+ c.getName() +" in the featureCompo");
        }
        if(! isActive()){this.active();}
        c.active();
    }

    public void deactivateChild(Feature child){
        child.deactivate();
        if(isActive() && !check()){
            super.deactivate();
        }
    }
}
