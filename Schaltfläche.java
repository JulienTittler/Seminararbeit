import greenfoot.*; 

      //interaktive Schaltflächen (dass man gewünschte Start- und Zielknoten mit Mausklicks einstellen kann) 
    public class Schaltfläche extends Actor
    {
    
        //Variablen zum lokalen "speichern" der Inhalte
        int Schriftgröße;
        Color TextFarbe;
        Color HintergrundFarbe;
        int Transparenz;
        String text = "A";
     
        public Schaltfläche(String text, int schriftgröße,Color textFarbe,Color hintergrundFarbe,int transparenz) 
        {
            setImage(new GreenfootImage(text,schriftgröße + 20 ,textFarbe, hintergrundFarbe));
            getImage().setTransparency(transparenz);
            
                
            Schriftgröße = schriftgröße;
            TextFarbe =  textFarbe;
            HintergrundFarbe = hintergrundFarbe;
            Transparenz = transparenz;
        }    
        
       
       
        //wenn interagiert wurde dann:
        public void textÄndern(String NeuerText){
            
            text = NeuerText;
           
            setImage(new GreenfootImage(text,Schriftgröße + 20 ,TextFarbe, HintergrundFarbe));
            getImage().setTransparency(Transparenz);
                        
        }   
}
