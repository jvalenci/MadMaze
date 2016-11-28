import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
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


            //the start point
            int sLevel = scan.nextInt();
            int sRow = scan.nextInt();
            int sCol = scan.nextInt();

            //the end point
            int eLevel = scan.nextInt();
            int eRow = scan.nextInt();
            int eCol = scan.nextInt();

            //my weighted graph
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

            ArrayList<Character> printPath = new ArrayList<>();

            DijkstraShortestPath path = new DijkstraShortestPath(graph, maze[sLevel][sRow][sCol], maze[eLevel][eRow][eCol]);
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
