import greenfoot.*;


public class Schalter extends Actor
{
    //Bild Quelle: https://findicons.com/search/toggle-switch/2
    
    
    
    String zustand = "links"; //Standartzustand
    
    public void umschalten(){
        //Schalterposition verändern 
        if(zustand == "links"){
            zustand = "rechts";
            this.setImage("rechts.png");
            }
        else{
            zustand = "links";
            this.setImage("links.png");
        }
        
        }
}
