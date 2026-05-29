package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label_dijsktra implements Comparable<Label_dijsktra>{
 
    
    public Label_dijsktra() {
        vide = true;
    }


    private Boolean vide = true;
    private Node sommet_courant;
    private boolean  marque;
    private double cout_realiser;
    private Arc pere;

    public Boolean isEmpty (){
        return vide;
    }

    public void setNode(Node sommet_courant,double cout_realiser,Arc pere){
        vide = false;  
        this.sommet_courant = sommet_courant;
        this.cout_realiser = cout_realiser;
        this.pere = pere;
    }

    public void marquer_node(){
        this.marque = true;
    }

    public Node getsommet_courant(){
        return sommet_courant;
    }

    public boolean getmarque(){
        return marque;
    }

    public double getcout_realiser(){
        return cout_realiser;
    }

    public Arc getpere(){
        return pere;
    }
    
    public double getcost(){
        return cout_realiser;
    }



    public int compareTo(Label_dijsktra autre){
        if (this.getcost()>autre.getcost()){
            return 1;
        }
        else{
            return -1;
        } 
    }

    public String toString(){
        return this.vide.toString();
    }
}
