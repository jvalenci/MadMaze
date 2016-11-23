import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by jonat on 11/23/2016.
 */
public class MadMaze {

    public static void main(String[] args){
        Scanner scan = null;
        File inputfile = null;
        File outputfile = null;
        FileWriter outfile = null;
        int numOfMazes;


        //init file for reading and writing
        try {
            inputfile = new File("input.txt");
            outputfile = new File("output.txt");

            scan = new Scanner(inputfile);
            outfile = new FileWriter(outputfile);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        numOfMazes = scan.nextInt();

        for ( int i = 0; i < numOfMazes; i++){
            //read in the dimensions of the 3D array
            int levels = scan.nextInt();
            int rows = scan.nextInt();
            int cols = scan.nextInt();

            //the start point
            int sLevel = scan.nextInt();
            int sRow = scan.nextInt();
            int sCol = scan.nextInt();

            //the end point
            int eLevel = scan.nextInt();
            int eRow = scan.nextInt();
            int eCol = scan.nextInt();

            //my graph
            SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

            //init the 3D array and add vertices to graph
            int [][][] maze = new int[levels][rows][cols];
            int vertex = 0;
            for(int j = 0; j < levels; j++){
                for(int k = 0; k < rows; k++){
                    for (int l = 0 ; l < cols; l++){
                        ++vertex;
                        maze[j][k][l] = vertex;
                        graph.addVertex(maze[j][k][l]);
                    }
                }
            }

            //add the neighbors
            for(int j = 0; j < levels; j++){
                for(int k = 0; k < rows; k++){
                    for (int l = 0 ; l < cols; l++){
                        String vertNeighbors = scan.next();
                        DefaultWeightedEdge edge;
                        for(int c = 0; c < vertNeighbors.length(); c++){
                            if( vertNeighbors.charAt(c) == '1'){
                                switch(c){
                                    case 0:
                                        edge = graph.addEdge(maze[j][k][l], maze[j][k-1][l]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                    case 1:
                                        edge = graph.addEdge(maze[j][k][l], maze[j][k][l+1]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                    case 2:
                                        edge = graph.addEdge(maze[j][k][l], maze[j][k+1][l]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                    case 3:
                                        edge = graph.addEdge(maze[j][k][l], maze[j][k][l-1]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                    case 4:
                                        edge = graph.addEdge(maze[j][k][l], maze[j+1][k][l]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                    case 5:
                                        edge = graph.addEdge(maze[j][k][l], maze[j-1][k][l]);
                                        graph.setEdgeWeight(edge, 1);
                                        break;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(DijkstraShortestPath.findPathBetween(graph, maze[sLevel][sRow][sCol], maze[eLevel][eRow][eCol]));
        }
    }
}
