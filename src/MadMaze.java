import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;


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
            inputfile = new File(args[0]);
            outputfile = new File("output.txt");

            scan = new Scanner(inputfile);
            outfile = new FileWriter(outputfile);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        numOfMazes = scan.nextInt();

        for ( int i = 0; i < numOfMazes; i++){

            //read in the dimensions of the maze
            int levels = scan.nextInt();
            int rows = scan.nextInt();
            int cols = scan.nextInt();
            int numOfVertices = levels * rows * cols;
            int movingNorth = cols * -1;
            int movingEast = 1;
            int movingSouth = cols;
            int movingWest = -1;
            int movingUp = rows * cols;
            int movingDown = -1 * rows * cols;


            //the start point
            int sLevel = scan.nextInt();
            int sRow = scan.nextInt();
            int sCol = scan.nextInt();
            int startPoint = (sLevel * cols * rows) + (sRow * rows) + sCol;

            //the end point
            int eLevel = scan.nextInt();
            int eRow = scan.nextInt();
            int eCol = scan.nextInt();
            int endPoint = (eLevel * cols * rows) + (eRow * rows) + eCol;

            //my weighted graph
            SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

            //init graph to add vertices
            for(int v = 0; v < numOfVertices; v++) {
                graph.addVertex(v);
            }


            //add the neighbors
            for(int j = 0; j < numOfVertices; j++) {
                String vertNeighbors = scan.next();
                DefaultWeightedEdge edge;
                for (int c = 0; c < vertNeighbors.length(); c++) {
                    if (vertNeighbors.charAt(c) == '1') {
                        switch (c) {
                            case 0:
                                edge = graph.addEdge(j, j + movingNorth);
                                graph.setEdgeWeight(edge, 1);
                                break;
                            case 1:
                                edge = graph.addEdge(j, j + movingEast);
                                graph.setEdgeWeight(edge, 1);
                                break;
                            case 2:
                                edge = graph.addEdge(j, j + movingSouth);
                                graph.setEdgeWeight(edge, 1);
                                break;
                            case 3:
                                edge = graph.addEdge(j, j + movingWest);
                                graph.setEdgeWeight(edge, 1);
                                break;
                            case 4:
                                edge = graph.addEdge(j, j + movingUp);
                                graph.setEdgeWeight(edge, 1);
                                break;
                            case 5:
                                edge = graph.addEdge(j, j + movingDown);
                                graph.setEdgeWeight(edge, 1);
                                break;
                        }
                    }
                }
            }

            //hold my directions that will print.
            ArrayList<Character> printPath = new ArrayList<Character>();

            //Dijkstra algo
            DijkstraShortestPath path = new DijkstraShortestPath(graph, startPoint, endPoint);

            //list of the edges that make the path
            List edgeList = path.getPathEdgeList();

            //parse the edges to get what direction was taken
            for(int q = 0; q < edgeList.size(); q++){
                String stringEdge = edgeList.get(q).toString();
                stringEdge = stringEdge.substring(1,stringEdge.length() - 1).replaceAll(" ","");
                String[] split = stringEdge.split(":");
                int source = Integer.parseInt(split[0]);
                int target = Integer.parseInt((split[1]));
                int result = target - source;

                if(result == movingWest){
                    printPath.add('W');
                }

                if(result == movingEast){
                    printPath.add('E');
                }

                if(result == movingNorth){
                    printPath.add('N');
                }

                if(result == movingSouth){
                    printPath.add('S');
                }

                if(result == movingUp){
                    printPath.add('U');
                }

                if(result == movingDown){
                    printPath.add('D');
                }

            }

            //print out the path
            try {
                for(char c : printPath){
                    outfile.write(c + " ");
                }
                outfile.write('\n');
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        //clean up, closing files
        try{
            outfile.close();
            scan.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
