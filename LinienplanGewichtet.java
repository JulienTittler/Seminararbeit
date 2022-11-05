import greenfoot.*;  
import java.util.*;


public class LinienplanGewichtet extends World
{

    //Klasse existiert nur um sich den gewichteten Graphen/Linienplan anzeigen lassen zu können; und wieder zurück navigieren zu können

    Kreuz kreuz1 = new Kreuz();
    public LinienplanGewichtet()
    {          
        super(1400, 800, 1);
        addObject (kreuz1,20,20);
    }
    
    public void act(){
     if(Greenfoot.mouseClicked(kreuz1)){
                    Greenfoot.playSound("negative click.wav");
                    Greenfoot.setWorld(new Main());                    
                }
     if(Greenfoot.isKeyDown("escape")){
                    Greenfoot.playSound("negative click.wav");
                    Greenfoot.setWorld(new Main());                    
                }   
    }
}
