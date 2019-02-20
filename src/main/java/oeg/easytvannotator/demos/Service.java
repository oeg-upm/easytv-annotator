/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;

import java.io.IOException;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EasyTVInterface;
/**
 *
 * @author Pablo
 */
public class Service {
    
    
    
     public static void main (String [] args) throws IOException{
    
        
        EasyTVInterface interf = new EasyTVInterface("","",false);
        String string=""; String Lang="";
        ESentence es= null;
       //String InputFile = "Ο δάσκαλος εξήγησε το μάθημα στην τάξη";   String Lang = "EL";

         
        
        string = "σε αυτό το σπήλαιο ζει μια λευκή αρκούδα χαλαρή";    Lang = "EL";
        es= interf.processLine(Lang, string);
        es.print();
        
        
        /*
        string = "Un orso vive in questa grotta";    Lang = "IT";
        es= interf.processLine(Lang, string);
        es.print();
        
        string = "Σε αυτή τη σπηλιά ζει μια αρκούδα";    Lang = "EL";
        es= interf.processLine(Lang, string);
        es.print();
       
       
        string = "En esta cueva vive un oso";    Lang = "ES";
        es= interf.processLine(Lang, string);
        es.print();
        
        
        string = "In this cave lives a bear";    Lang = "EN";
        es= interf.processLine(Lang, string);
        es.print();
        
        
        
         */
       
        
        
        
                
                
    }
    
     
    
}
