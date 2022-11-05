import greenfoot.*;

//Textfelder mit denen Texte angezeigt werden können
public class Textfeld extends Actor{
 
        //Variablen zum lokalen "speichern" der Inhalte
        int Schriftgröße;
        Color TextFarbe;
        int Transparenz;
        String text = "A";
        
         public Textfeld(String text,int schriftgröße,Color textFarbe,int transparenz){
    
             setImage(new GreenfootImage(text,schriftgröße ,textFarbe , new Color(0,0,0,0))); 
             getImage().setTransparency(transparenz);
         
             Schriftgröße = schriftgröße;
             TextFarbe =  textFarbe;
          
             Transparenz = transparenz;
         
       }
          public void textÄndern(String NeuerText){
            
            text = NeuerText;
           
            setImage(new GreenfootImage(text,Schriftgröße  ,TextFarbe,new Color(0,0,0,0)));
            getImage().setTransparency(Transparenz);
                           
       }  
 }