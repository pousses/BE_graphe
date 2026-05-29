package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Graph;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }


    @Override
    protected ShortestPathSolution doRun() {
        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();


        //creer et initialise un tableau qui associe des labels avec des id de nodes. Il sont iniatialement vide (.isEmpty()), sauf le premier
        Graph graph = data.getGraph();
        Label_dijsktra[] association = new Label_dijsktra[graph.size()];
        for (int i =0;i<graph.size();i++){
            Label_star l;
            if (data.getMode() == AbstractInputData.Mode.LENGTH){
                l = new Label_star(graph.get(i).getPoint().distanceTo(data.getDestination().getPoint()));
            }
            else{
                l = new Label_star(graph.get(i).getPoint().distanceTo(data.getDestination().getPoint())/(data.getMaximumSpeed()*1000/3600));
            }
            association[i] = l;

        }
        association[data.getOrigin().getId()].setNode(data.getOrigin(),0,null);
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());

        return calcul(association);
    }

}
