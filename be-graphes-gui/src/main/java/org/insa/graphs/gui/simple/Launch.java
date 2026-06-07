package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.time.Clock;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.tools.ant.taskdefs.Pack;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;


public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     *
     * @return The created drawing.
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {
        
        List<ArcInspector> a = ArcInspectorFactory.getAllFilters();
        final ArcInspector[] arctype = {a.get(0),a.get(1),a.get(2),a.get(3)}; 
       
        Graph graph;
        Path path;
        ShortestPathSolution path_teste;
        DijkstraAlgorithm algo_teste;


        //teste 0: facile
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr"))))) {
            graph = reader.read();
        }

         try (PathReader pathReader = new BinaryPathReader(new DataInputStream(
            new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path"))))) {
            path = pathReader.readPath(graph);
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, path.getOrigin(), path.getDestination(), arctype[0]));
        path_teste = algo_teste.run();
        System.out.print("teste0 : ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (Double.compare(path.getLength(), path_teste.getPath().getLength())==0){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: pas la bonne distance, "+path_teste.getPath().getLength()+"au lieu de "+path.getLength());
        }

        //teste 1: longueur
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr"))))) {
            graph = reader.read();
        }

         try (PathReader pathReader = new BinaryPathReader(new DataInputStream(
            new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31_insa_aeroport_length.path"))))) {
            path = pathReader.readPath(graph);
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, path.getOrigin(), path.getDestination(), arctype[1]));
        path_teste = algo_teste.run();
        System.out.print("teste1 : ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (Double.compare(path.getLength(), path_teste.getPath().getLength())==0){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: pas la bonne distance, "+path_teste.getPath().getLength()+"au lieu de "+path.getLength());
        }

        //teste 2: temps
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr"))))) {
            graph = reader.read();
        }

         try (PathReader pathReader = new BinaryPathReader(new DataInputStream(
            new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31_insa_aeroport_time.path"))))) {
            path = pathReader.readPath(graph);
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, path.getOrigin(), path.getDestination(), arctype[2]));
        path_teste = algo_teste.run();
        System.out.print("teste2 : ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (Double.compare(path.getMinimumTravelTime(), path_teste.getPath().getMinimumTravelTime())==0){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: pas le bon temps de parcours, "+path_teste.getPath().getMinimumTravelTime()+"au lieu de "+path.getMinimumTravelTime());
        }

        //teste 3: impossible
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr"))))) {
            graph = reader.read();
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, graph.get(423945),graph.get(120622), arctype[0]));
        path_teste = algo_teste.run();
        System.out.print("teste3 : ");
        if(!path_teste.isFeasible()){
            System.out.println("validé!");
        }
        else {
            System.out.println("echoué: chemin null");
        }

        //teste 4: origine=dest
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr"))))) {
            graph = reader.read();
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, graph.get(1), graph.get(1), arctype[0]));
        path_teste = algo_teste.run();
        System.out.print("teste4 : ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (path_teste.getPath().isEmpty()){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: le chemin n'est pas le chemin vide");
        }


        //teste 5: A* contre Dijkstra longueur
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr"))))) {
            graph = reader.read();
        }

        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, graph.get(756), graph.get(155), arctype[0]));
        path_teste = algo_teste.run();

        ShortestPathAlgorithm algo_teste_2 = new AStarAlgorithm(new ShortestPathData(graph, graph.get(756), graph.get(155), arctype[0]));
        ShortestPathSolution path_teste_2 = algo_teste_2.run();


        System.out.print("\nteste5, Dijkstra /A*: ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (Double.compare(path_teste_2.getPath().getLength(), path_teste.getPath().getLength())==0){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: pas la bonne distance, A* = "+path_teste.getPath().getLength()+"au lieu de Dijkstra = "+path_teste_2.getPath().getLength());
        }

        //teste 6: A* contre Dijkstra temps + teste de 
        try (GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bretagne.mapgr"))))) {
            graph = reader.read();
        }

        System.out.println("debut\n ");
        algo_teste = new AStarAlgorithm(new ShortestPathData(graph, graph.get(36415), graph.get(15448), arctype[2]));
      
        path_teste = algo_teste.run();
        System.out.println("fin A*");

        
        algo_teste_2 = new AStarAlgorithm(new ShortestPathData(graph, graph.get(36415), graph.get(15448), arctype[2]));

        path_teste_2 = algo_teste_2.run();
        
        System.out.println("fin Dijkstra = ");

        System.out.print("teste5, Dijkstra /A*: ");
        if(!path_teste.isFeasible()){
            System.out.println("echoué: chemin nom fesable");
        }
        else if (path_teste.getPath() == null) {
            System.out.println("echoué: chemin null");
        }
        else if (!path_teste.getPath().isValid()) {
            System.out.println("echoué: chemin non valide");
        }
        else if (Double.compare(path_teste_2.getPath().getMinimumTravelTime(), path_teste.getPath().getMinimumTravelTime())==0){
            System.out.println("validé!");
        }
        else{
            System.out.println("echoué: pas le bon temps, A* = "+path_teste.getPath().getMinimumTravelTime()+"au lieu de Dijkstra = "+path_teste_2.getPath().getMinimumTravelTime());
        }


        
    }

}
