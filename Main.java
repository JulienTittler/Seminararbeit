import greenfoot.*;  
import java.util.*;

public class Main extends World
{

    //Klasse startet das ganze Programm/Interface
    
    int start = 0;
    int ziel = 0;
    
    Color transparent = new Color(0,0,0,0);
    
    Textfeld startKnoten = new Textfeld(" 0 ", 85,Color.BLACK,190);
    Schaltfläche plusStartKnoten = new Schaltfläche( "+",60,Color.GRAY,transparent,100);
    Schaltfläche minusStartKnoten = new Schaltfläche( "-",60,Color.GRAY,transparent,100);
    
       
    Textfeld zielKnoten = new Textfeld(" 0 ", 85,Color.BLACK,190);
    Schaltfläche plusZielKnoten = new Schaltfläche( "+",60,Color.GRAY,transparent,100);
    Schaltfläche minusZielKnoten = new Schaltfläche( "-",60,Color.GRAY,transparent,100);
    
    Knopf startKnopf = new Knopf();
    Schalter schalterAlgorithmus = new Schalter();
    Schalter schalterGraph = new Schalter();
    
    public Main()
    {            
        super(1400, 800, 1);
        mainAnzeigen();
    }
    
    public void mainAnzeigen(){

        addObject(startKnoten, 1184,305);
        addObject(zielKnoten, 1184,475);
        
        addObject(plusStartKnoten, 1244,305);
        addObject(minusStartKnoten, 1126,302);
        
        addObject(plusZielKnoten, 1244,475);
        addObject(minusZielKnoten, 1126,472);
        
         addObject(startKnopf, 1184,625); 
         addObject(schalterAlgorithmus, 440,355);  
         addObject(schalterGraph, 440,600); 
    }
    
    public void act(){
        //Schalter Funktion
        if(Greenfoot.mouseClicked(schalterAlgorithmus)){
            Greenfoot.playSound("positive click.wav");
            schalterAlgorithmus.umschalten();            
        }
        if(Greenfoot.mouseClicked(schalterGraph)){
            Greenfoot.playSound("positive click.wav");
            schalterGraph.umschalten();            
        }
        
        //Startknoten Veränderbar machen
        if(Greenfoot.mouseClicked(plusStartKnoten)){
            Greenfoot.playSound("positive click.wav");
            start = start +1;
            startKnoten.textÄndern(" "+start+" ");
        
        }
        if(Greenfoot.mouseClicked(minusStartKnoten)){
            Greenfoot.playSound("negative click.wav"); 
            start = start -1;
            if(start < 0){ start = 0;}
            startKnoten.textÄndern(" "+start+" ");
        }
        
        //Zielknoten veränderbar machen
        if(Greenfoot.mouseClicked(plusZielKnoten)){
            Greenfoot.playSound("positive click.wav");
            ziel = ziel +1;
            zielKnoten.textÄndern(" "+ziel+" ");
        
        }
        if(Greenfoot.mouseClicked(minusZielKnoten)){
            Greenfoot.playSound("negative click.wav"); 
            ziel = ziel -1;
            if(ziel < 0){ ziel = 0;}
            zielKnoten.textÄndern(" "+ziel+" ");
        }
        
        
        //Algorithmus starten
        if(Greenfoot.mouseClicked(startKnopf)){
            Greenfoot.playSound("positive click.wav");
        
            
            if(schalterGraph.zustand == "links"){
                if(start > 17 || ziel > 17){
                    System.out.println("Fehler: Diesen Startknoten oder Zielknoten gibt es in diesem Graphen nicht. (Knoten Nummern muss zwischen 0 und 17 liegen)");     
                    System.out.println("---------------------------------------------------------");
                }
                else{
                    if(schalterAlgorithmus.zustand == "links"){
                        Greenfoot.setWorld(new LinienplanGewichtet());
                        Dijkstra los = new Dijkstra(start, ziel,"mvv");                       
                    }
                    else{
                        Greenfoot.setWorld(new LinienplanGewichtet());
                        FloydWarshall go = new FloydWarshall(start, ziel,"mvv");                       
                    }
                }    
            }
            else{
                if(schalterAlgorithmus.zustand == "links"){
                        Dijkstra los = new Dijkstra(start, ziel,"5k");
                    }
                    else{
                        FloydWarshall go = new FloydWarshall(start, ziel,"5k");
                    }

            }
        }
     
 
        // if(Greenfoot.isKeyDown("d")){
            // Dijkstra50Fach test = new Dijkstra50Fach(0, 12,"5k");

                // }        
     
        // if(Greenfoot.isKeyDown("f")){
            // FloydWarshall50Fach test = new FloydWarshall50Fach(0, 12,"5k");
                // }     
         
   }
}