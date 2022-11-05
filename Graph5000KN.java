import greenfoot.*; 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;


public class Graph5000KN  
{
    //Graph mit 5.000 Knoten -> Adjazenzmatrix mit 25.000.000 Einträgen
    
    
    //Graph bzw. Adjazenzmatrix muss aus Textdatei importiert werden, weil sie zu groß ist (zu viele Zeichen hat) um hier rein geschrieben zu werden    
  public static int[][] textdateiZuArray() throws IOException
    {
        int[][] adjazenzMatrix = new int[5000][5000];

        Path path = Paths.get("graph3.txt"); // graph1: 6Kanten pro Knoten 
                                             // graph2: 2-3 Kanten pro Knoten 
                                             // graph3: mindestens 1 Kante pro Knoten durchschnittlich aber 3
                                             
        BufferedReader br = Files.newBufferedReader(path);

        
        //es wird jede Zeile der Textdatei abgegangen und dann jeweils jedes einzele Zeichen(Ziffer) in der Zeile  -> Eintrag in das entsprechende Feld des Arrays<
        for(int i = 0; i < 5000; i++) {
            char[] chars = br.readLine().toCharArray();
            for(int j = 0; j < 5000; j++) {
                adjazenzMatrix[i][j] = (int)chars[j] - 48; //Um von Char auf die tatsächliche Zahl zu kommen muss man immer 48 abziehen
            }
        }

        return adjazenzMatrix;
    }
}
