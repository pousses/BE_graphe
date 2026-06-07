package org.insa.graphs.algorithm.shortestpath;


public class Label_star extends Label_dijsktra{


    private double cout_estimer;
    
    public Label_star(double cout_estimer) {
        super();
        this.cout_estimer = cout_estimer;
    }

    @Override
    public double getcout_estimer(){
        return this.cout_estimer;
    }


    @Override
    public double getcost(){
        return this.getcout_realiser()+cout_estimer;
    }


}
 
  