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
            //read in the dimensions of the 3D array
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
            int startPoint = (((sLevel * cols * rows) + 1 ) * (sCol + 1) * (sRow + 1)) -1 ;

            //the end point
            int eLevel = scan.nextInt();
            int eRow = scan.nextInt();
            int eCol = scan.nextInt();
            int endPoint = (((eLevel * cols * rows) + 1 ) * (eCol + 1) * (eRow + 1)) -1;

            //my weighted graph
            SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

            //init vertices to graph
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

            ArrayList<Character> printPath = new ArrayList<Character>();

            DijkstraShortestPath path = new DijkstraShortestPath(graph, startPoint, endPoint);
            List edgeList = path.getPathEdgeList();

            for(int q = 0; q < edgeList.size(); q++){
                String stringEdge = edgeList.get(q).toString();
                stringEdge = stringEdge.substring(1,stringEdge.length() - 1).replaceAll(" ","");
                String[] split = stringEdge.split(":");
                int source = Integer.parseInt(split[0]);
                int target = Integer.parseInt((split[1]));
                int result = target - source;

                if(result == -1){
                    printPath.add('W');
                }

                if(result == 1){
                    printPath.add('E');
                }

                if(result == cols * -1){
                    printPath.add('N');
                }

                if(result == cols){
                    printPath.add('S');
                }

                if(result == rows * cols){
                    printPath.add('U');
                }

                if(result == rows * cols * -1){
                    printPath.add('D');
                }

            }

            try {
                for(char c : printPath){
                    outfile.write(c + " ");
                }
                outfile.write('\n');
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        try{
            outfile.close();
            scan.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
