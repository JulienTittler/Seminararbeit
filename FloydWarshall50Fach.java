import greenfoot.*;  
import java.util.*;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode; 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

// Floyd-Warshall, welcher 50 Mal für gleichen Start- und Zielknoten ausgeführt wird. Die Durchschnittswerte werden zum Schluss ausgegeben.


    public class FloydWarshall50Fach  
    {
        static int startKnoten;
        static int zielKnoten;

        static long anfangsZeitpunkt;
        static long endZeitpunkt;
        static long benötigteZeit;
        
        static int entfernungAusgabe[][];
        static int nachfolgerAusgabe[][];
        
        static String graph;
        
        public FloydWarshall50Fach(int start, int ziel, String Graph)
        {
            startKnoten = start;
            zielKnoten = ziel;
            graph = Graph;
            
            

           //Auswahl des Graphen & start des Algorithmus:
            
            if(Graph == "mvv"){
                GraphMVVErfunden graphMVVErfunden = new GraphMVVErfunden();
                startFloydWarshall(graphMVVErfunden.adjazenzMatrix);
            }
            if(Graph == "5k"){
                
                    //Umwandeln des (riesigen) Graphen aus Textdatei zu einem Array:
                    int[][] adjazenzMatrixGraph5000KN = {};
                    try {
                        adjazenzMatrixGraph5000KN = Graph5000KN.textdateiZuArray(); //Methodenaufruf in Graph5000KN Klasse
                    } catch(IOException error) {
                        System.out.println("Matrix konnte nicht geladen werden. Fehler:"); //Falls Fehler
                        System.out.println(error);
                        System.exit(0);
                    }
                    
                startFloydWarshall(adjazenzMatrixGraph5000KN);
            }
            if(Graph == "100"){
                Graph100KN graph100KN = new Graph100KN(); 
                startFloydWarshall(graph100KN.adjazenzMatrix);
            } 
        }
    
        
        public static void startFloydWarshall(int[] [] adjazenzMatrix){
            
            System.out.println("Floyd-Warshall wird 50-Mal ausgeführt...");
            
            double[] benötigteZeitArray = new double[50]; //Liste mit 50 Einträgen zur benötigten Zeit
            double[] cpuAuslastungArray = new double[50]; //Liste zur Prozessorauslastung
            long[] ramArray = new long[50];    //Liste zur Arbeitsspeicherauslastung
            
            
            //Floyd-Warshall wird 50 Mal durchgegegangen
            for(int f=0; f < 50; f++){
                           
                anfangsZeitpunkt = System.nanoTime(); //Anfangspunkt Zeitmessung
               
               //Vorbereitungen: 
                    int knotenAnzahl = adjazenzMatrix.length;   //knoten Anzahl herausfinden                   
                    
                   int nachfolger[][] = new int[knotenAnzahl][knotenAnzahl]; //Adjazenzmatrix, in jener Für jeden Knoten gespeichert ist wer der Nachfolger sein müsste um zu einem beliebigen anderen Knoten zu gelangen
                   for(int i=0; i < knotenAnzahl; i++){   
                      for(int j=0; j < knotenAnzahl; j++){  
                           nachfolger[i][j] = 111111111; //für keinen nachfolger           
                          //Wenn es eine Kante von i nach j gibt, dann wird bei der Zeile von i und der Spalte von j eingetragen, dass j der Nachfolger (von i) ist.   -> Standartnachfolger werden eingetragen
                          if (adjazenzMatrix[i][j] != 0){                          
                              nachfolger[i][j] = j;                          
                          }
                        }
                    }
                        
    
                    
                  int entfernung[][] = new int[knotenAnzahl][knotenAnzahl]; //Adjazenzmatrix, in der die kürzesten bekannten Entfernungen zwischen allen KnotenPaaren stehen
                  //Einträge der Grundmatrix werden in die Entfernungsmatrix übernommen. Damit vorhandene Kanten(mit gewichten) schonmal gleich eingetragen werden                
                  for(int i=0; i < knotenAnzahl; i++){   
                      for(int j=0; j < knotenAnzahl; j++){   
                                    
                            entfernung[i][j]= adjazenzMatrix[i][j];
                        
                    }
                  }
                  //Jetzt müssen aber alle Nullen (keine Kante) durch unedlich ersetzt werden -> denn man weiß die Entfernung ja noch nicht
                  for(int i=0; i < knotenAnzahl; i++){   
                      for(int j=0; j < knotenAnzahl; j++){   
                      if (entfernung[i][j] == 0){                       
                            entfernung[i][j]= 99999; // -> unendlich
                        }
                    }
                  }            
                  //Nun muss noch bei allen i=j Einträgen (Felder bei denen Zeile & Spalte gleicher Knoten) eine Null eingetragen werden (da es dort logischerweise keine Kante gibt) 
                  for(int i=0; i < knotenAnzahl; i++){   
                      for(int j=0; j < knotenAnzahl; j++){                        
                      if (i == j){
                            entfernung[i][j]= 0; 
                        }
                    }
                  }

              
            //Algorithmus beginnt:
            
            
                //für jeden Knoten k wird geprüft
                for(int k=0; k < knotenAnzahl; k++){ 
                    
                  //Zeile der Entfernungs-Matrix  
                  for(int i=0; i < knotenAnzahl; i++){   
                         
                    //Spalteneintrag der Entfernungs-Matrix       
                      for(int j=0; j < knotenAnzahl; j++){                        
                        
                          
                          //Bisheriger Entfernungseintrag zwischen i und j  (Zahl oder unendlich)
                          int x1 = entfernung[i][j];
                          
                          // Summe aus Entfernungseintrag von i-k und Entfernungseintrag von k-j  (Zahl oder unendlich)
                          int x2 = entfernung[i][k] + entfernung[k][j];
                          
                          //Das Minimum von beiden ist der neue kürzeste Entfernungseintrag
                          if(x2 < x1){
                            entfernung[i][j] = x2;
                            nachfolger[i][j] =nachfolger[i][k]; // Nachfolger um von i zu j zugelangen ist nun der gleiche wie der um von i nach k zu gelangen (weil der Weg ja erst über k führt)
                            }
                            else{
                             //Entfernung bleibt gleich
                             //Nachfolger bleit somit auch unverändert
                            }
                          
     
                    }
                  } 
                }
                            
                  endZeitpunkt = System.nanoTime(); //Endpunkt der Zeitmessung
                  benötigteZeit = (endZeitpunkt - anfangsZeitpunkt); 
                  double benötigteZeitKomma = benötigteZeit; //int zu double (für Umrechung)
                  benötigteZeitKomma = benötigteZeitKomma/1000000; //+Umrechnung von nano- in millisekunden
                  benötigteZeitArray[f] = benötigteZeitKomma;   //Zeit wird in Liste eingetragen
                  
                   OperatingSystemMXBean cpu = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); //casten des OperatingSystemMXBean (java.lang.management.OperatingSystemMXBean) zu OperatingSystemMXBean (com.sun.management.OperatingSystemMXBean)                
                   double cpuProzent = cpu.getProcessCpuLoad()*100; //Null-Komma Wert zu Prozentzahl machen
                   cpuAuslastungArray[f] = cpuProzent; //Wert wird in Liste eingetragen
                  
                   long ram = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(); //in bytes
                   ram = ram/1024/1024; // bytes -> kilobytes -> megabytes
                   ramArray[f] = ram; //Wert wird in Liste eingetragen
                  
    
                   entfernungAusgabe = entfernung; //speichern der entgültigen entfernug außerhalb der for-schleife  -> für Ausgabe
                   nachfolgerAusgabe = nachfolger; //speichern der entgültigen Nachfolger-Liste außerhalb der for-schleife  -> für Ausgabe
                   
           }
       
       
       
            //Ausgabe:
                
                System.out.println("Suche gestartet für:");
                  if(graph == "mvv"){
                        System.out.println("Graph: MVV-Modell Graph");
                    }
                  if(graph == "5k"){
                        System.out.println("Graph: 5000 Knoten Graph");
                    }
                System.out.println("Start: "+startKnoten);
                System.out.println("Ziel: "+zielKnoten);
                System.out.println("Die geringste Entfernung beträgt: "+entfernungAusgabe[startKnoten][zielKnoten]);
                System.out.print("Knotenreihenfolge des Weges: ");             
                      //es wird mit dem start Knoten begonnen und immer wieder der Nachfolger des Nachfolgers ausgegeben, der in der Zielknoten Spalte steht, bis der Zielknoten erreicht wird
                      int t = startKnoten;
                     while (t != zielKnoten){
                        System.out.print(t+" ");            
                        t =nachfolgerAusgabe[t][zielKnoten];
                        }
                      System.out.println(zielKnoten);
              
                      
            double zeit = 0;
            for(int a=0; a < 50; a++){ 
               zeit = zeit+benötigteZeitArray[a]; //addieren aller Zeiteinträge
            }
             zeit = round(zeit,3);
            System.out.println("Insgesamt benötigte Zeit in ms: "+zeit);
            zeit = zeit/50; //Summe wird durch 50 geteilt -> für Durchschnittswert
            zeit = round(zeit,3);
            System.out.println("Durchschnittlich benötigte Zeit in ms: "+zeit);
        
            double cpuAuslastung = 0;
            for(int a=0; a < 50; a++){ 
               cpuAuslastung = cpuAuslastung+cpuAuslastungArray[a]; //addieren aller CPU-Wert-Einträge
            }
            cpuAuslastung = cpuAuslastung/50;  //Summe wird durch 50 geteilt -> für Durchschnittswert
            cpuAuslastung = round(cpuAuslastung,2);
            System.out.println("Durchschnittliche Prozessorauslastung in %: "+cpuAuslastung);
            
            long ramAuslastung = 0;
            for(int a=0; a < 50; a++){ 
               ramAuslastung = ramAuslastung+ramArray[a]; //addieren aller RAM-Wert-Einträge
            }
            ramAuslastung = ramAuslastung/50;  //Summe wird durch 50 geteilt -> für Durchschnittswert
            System.out.println("Durchschnittliche Auslastung des Arbeitsspeichers: "+ramAuslastung);
        
        
        
              System.out.println();
              System.out.println("---------------------------------------------------------");
              Greenfoot.playSound("start.wav");
     }
        public static double round(double value, int places) {
            //methode zum runden von Zahlen
            //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places   [Aufruf am 04.10.2022, 21h]
            
            if(places < 0) throw new IllegalArgumentException();
    
                BigDecimal bd = BigDecimal.valueOf(value);
                bd = bd.setScale(places, RoundingMode.HALF_UP);
                return bd.doubleValue();
      }
} 