import greenfoot.*;  
import java.util.*;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

// Dijkstra Algorithmus, welcher 50 Mal für gleichen Start- und Zielknoten ausgeführt wird. Die Durchschnittswerte werden zum Schluss ausgegeben.

public class Dijkstra50Fach
{

    static int startKnoten;
    static int zielKnoten;

    static String Weg = "";
    
    static long anfangsZeitpunkt;
    static long endZeitpunkt;
    static long benötigteZeit;
    
    static String graph;
    
    public Dijkstra50Fach(int start, int ziel, String Graph)
    {
        startKnoten = start;
        zielKnoten = ziel;
        graph = Graph;
  
        
       //Auswahl des Graphen & start des Algorithmus:
                
                if(Graph == "mvv"){
                    GraphMVVErfunden graphMVVErfunden = new GraphMVVErfunden();
                    startDijkstra(graphMVVErfunden.adjazenzMatrix);
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
                        
                    startDijkstra(adjazenzMatrixGraph5000KN);
                }
                if(Graph == "100"){
                    Graph100KN graph100KN = new Graph100KN(); 
                    startDijkstra(graph100KN.adjazenzMatrix);
                }  
  
    }
   

    //reminder: arrays starten mit 0, die knotenAnzahl aber mit 1 !!!
    
    public static void startDijkstra(int[] [] adjazenzMatrix){
        
        System.out.println("Dijkstra wird 50-Mal ausgeführt...");
        
        int knotenAnzahl = adjazenzMatrix.length;   //knoten Anzahl herausfinden
            double[] benötigteZeitArray = new double[50]; //Liste mit 50 Einträgen zur benötigten Zeit
            double[] cpuAuslastungArray = new double[50]; //Liste zur Prozessorauslastung
            long[] ramArray = new long[50];   //Liste zur Arbeitsspeicherauslastung
        
             //variablen die  später für die Ausgabe von Nöten sind
             int ausgabeVorgänger[]= new int[knotenAnzahl];
            int ausgabeBesterKnoten = -5;
            int ausgabeEntfernung[]= new int[knotenAnzahl];
        
        //Dijkstra wird 50 Mal durchgegegangen
        for(int f=0; f < 50; f++){  
        
            anfangsZeitpunkt = System.nanoTime(); //Anfangspunkt Zeitmessung
        
            //vorbereiten
            
                boolean besucht[] = new boolean[knotenAnzahl]; //erstellen array "besucht": immer wenn die Entfernung eingetragen wurde/er mal bester knoten war -> true;  Standartwert: false
                int entfernung[] = new int[knotenAnzahl]; //aktuelle kürzeste Entfernung vom start aus bis zum knoten
                int vorgänger[] = new int[knotenAnzahl]; 
            
            
                //entfernung für alle Knoten auf unendlich setzten, beim startknoten auf 0  
                for(int i=0; i < knotenAnzahl; i++){       
                    entfernung[i]= Integer.MAX_VALUE;
               }
               entfernung[startKnoten] = 0;
               
           
       
            //Beginn Suche 
            for(int i=0; i < knotenAnzahl ; i++){
                //besten Knoten finden
                //--> Knoten mit insgesamt kürzesten Weg vom startKnoten aus
                int besterKnoten = findeBestenKnoten(entfernung, besucht);
                besucht[besterKnoten] = true;
                
                //schauen ob der besteKnoten der Zielknoten ist
                 if (besterKnoten == zielKnoten){
                    // ja -->fertig
                    endZeitpunkt = System.nanoTime(); //Endpunkt der Zeitmessung
                    benötigteZeit = (endZeitpunkt - anfangsZeitpunkt); 
                    double benötigteZeitKomma = benötigteZeit; //int zu double (für Umrechung)
                    benötigteZeitKomma = benötigteZeitKomma/1000000; //+Umrechnung von nano- in millisekunden
                    benötigteZeitArray[f] = benötigteZeitKomma; //Zeit wird in Liste eingetragen
                    
                     
                     
                     
                  
                     
                        OperatingSystemMXBean cpu = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); //casten des OperatingSystemMXBean (java.lang.management.OperatingSystemMXBean) zu OperatingSystemMXBean (com.sun.management.OperatingSystemMXBean)                
                       double cpuProzent = cpu.getProcessCpuLoad()*100; //Null-Komma Wert zu Prozentzahl machen
                       cpuAuslastungArray[f] = cpuProzent; //CPU-Wert wird in Liste eingetragen
                      
                       long ram = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed(); //in bytes
                       ram = ram/1024/1024; // bytes -> kilobytes -> megabytes
                       ramArray[f] = ram; //RAM-Wert wird in Liste eingetragen
    
    
                     
                    
                 }
                else{ //nein? -> weiter
    
                 //Nachbarn des bestenKnoten finden und Entfernungen bei den Nachbarn eintragen
                   for(int j=0; j < knotenAnzahl; j++){
                    //Falls Kante zu [j] existiert, [j] noch nicht besucht UND Entfernung des bestenKnoten schon eingetragen(<-überflüssig?)
                    if(adjazenzMatrix[besterKnoten][j] !=0 && !besucht[j] && entfernung[besterKnoten] != Integer.MAX_VALUE){
                        //neueEntfernung =  Entfernung zum bestenKnoten + Entfernung zu [j] 
                        int neueEntfernung = entfernung[besterKnoten] + adjazenzMatrix[besterKnoten][j];
                    
                     //Falls die Neue Entfernung kleiner ist als die bisherige bei [j] eingetragene Entfernung --> Ersetzen   
                    if(neueEntfernung < entfernung[j]){                    
                        entfernung[j] = neueEntfernung;         
                        vorgänger[j] = besterKnoten;
                    }
                  }
                 }
                
            }
             ausgabeBesterKnoten = besterKnoten;
          }
            
            ausgabeVorgänger = vorgänger;
            ausgabeEntfernung = entfernung;
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
                System.out.println("Die geringste Entfernung ist: "+ ausgabeEntfernung[ausgabeBesterKnoten]);
                
                Weg = "";
                     wegAusgeben(ausgabeVorgänger, ausgabeBesterKnoten);
                     System.out.println("");
         
                     
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
            cpuAuslastung = cpuAuslastung/50; //Summe wird durch 50 geteilt -> für Durchschnittswert
            cpuAuslastung = round(cpuAuslastung,2);
            System.out.println("Durchschnittliche Prozessorauslastung in %: "+cpuAuslastung);
            
            long ramAuslastung = 0;
            for(int a=0; a < 50; a++){ 
               ramAuslastung = ramAuslastung+ramArray[a]; //addieren aller RAM-Wert-Einträge
            }
            ramAuslastung = ramAuslastung/50; //Summe wird durch 50 geteilt -> für Durchschnittswert
            System.out.println("Durchschnittliche Auslastung des Arbeitsspeichers: "+ramAuslastung);
        
         
                System.out.println(""); //nächste Zeile
                 System.out.println("---------------------------------------------------------");
                 Greenfoot.playSound("start.wav");
                 
     
   }
    private static int findeBestenKnoten(int[] entfernung, boolean[] besucht){
        
        int besterKnoten = -1;      //Wert der nicht rauskommen kann (Array fängt bei 0 an)
        int knotenAnzahl = entfernung.length;
     
        for(int i=0; i < knotenAnzahl; i++){
            //Falls noch nicht besucht und (noch kein Wert für bestenKnoten ODER entfernung kleiner als vorheriger(-> in diesem Durchlauf dieser Methode) besterKnoten)
            if(!besucht[i] &&(besterKnoten == -1 || entfernung[i] < entfernung[besterKnoten])){
                //neuen bestenKnoten Speichern
                besterKnoten = i;
            }
        }
       
        return besterKnoten;
   }
    private static void wegAusgeben(int[] vorgänger,int aktuellerKnoten){
        
        //Rekursiv: solange zum Vorgänger bis beim startKnoten angekommen. Dann "Knotenreihenfolge des Weges:" ausgeben und dann ausgabe der KnotenNummern (abarbeiten des Stapels von der Rekursion)
        if (aktuellerKnoten != startKnoten){
        wegAusgeben(vorgänger,vorgänger[aktuellerKnoten]);
      }
      else{
        System.out.print("Knotenreihenfolge des Weges: ");        
        }

        System.out.print(aktuellerKnoten+" ");
        Weg = Weg+" "+aktuellerKnoten;
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
