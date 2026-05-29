package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected ShortestPathSolution calcul(Label_dijsktra[] association){
        // TODO: implement the Dijkstra algorithm
        BinaryHeap<Label_dijsktra> tas = new BinaryHeap<Label_dijsktra>();
        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;
         // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        boolean pastrouver = true;


        //init
        tas.insert(association[data.getOrigin().getId()]);
        

        while (pastrouver && !tas.isEmpty()){
            Label_dijsktra label_noeud_actuel = tas.deleteMin();
            Node noeud_actuelle = label_noeud_actuel.getsommet_courant();
            label_noeud_actuel.marquer_node();
            notifyNodeMarked(noeud_actuelle);
            //System.out.println(noeud_actuelle.getId()+"   "+label_noeud_actuel.getcout_realiser());

            if (noeud_actuelle == data.getDestination()){
                pastrouver = false;
            }
            else{
                for (Arc a : noeud_actuelle.getSuccessors()){
                    if (data.isAllowed(a)){
                        //si le label est vide, on ne la encore jamais rencontrer, donc on init et on met dans le tas
                        if (association[a.getDestination().getId()].isEmpty()){
                            notifyNodeReached(a.getDestination());
                            association[a.getDestination().getId()].setNode(a.getDestination(),label_noeud_actuel.getcout_realiser()+data.getCost(a),a);
                            tas.insert(association[a.getDestination().getId()]);
                        }//si le label existe mais n'est pas marqué, on verifier sa distance connue, et on le retire puis remet dans le tas si on la modifie
                        else if (!association[a.getDestination().getId()].getmarque() && association[a.getDestination().getId()].getcout_realiser()>label_noeud_actuel.getcout_realiser()+data.getCost(a)){
                            tas.remove(association[a.getDestination().getId()]);
                            association[a.getDestination().getId()].setNode(a.getDestination(),label_noeud_actuel.getcout_realiser()+data.getCost(a),a);
                            tas.insert(association[a.getDestination().getId()]);
                        }
                    }
                }
            }
        }

        
        // si arret par tas vide, pas de chemin trouver
        if (pastrouver) {
            solution = new ShortestPathSolution(data,  AbstractSolution.Status.INFEASIBLE);
        }
        else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = association[data.getDestination().getId()].getpere();
            while (arc != null) {
                arcs.add(arc);
                arc = association[arc.getOrigin().getId()].getpere();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL,new Path(graph, arcs));
        } 

        return solution;


    }

    @Override
    protected ShortestPathSolution doRun() {
        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        //creer et initialise un tableau qui associe des labels avec des id de nodes. Il sont iniatialement vide (.isEmpty()), sauffe le premier
        Graph graph = data.getGraph();
        Label_dijsktra[] association = new Label_dijsktra[graph.size()];
        for (int i =0;i<graph.size();i++){
            Label_dijsktra l = new Label_dijsktra();
            association[i] = l;
        }
        association[data.getOrigin().getId()].setNode(data.getOrigin(),0,null);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        

        return calcul(association);
    }

}
