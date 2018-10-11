/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.run;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import oeg.easytvannotator.model.ESentence;

import oeg.easytvannotator.model.EasyTVInterface;

/**
 *
 * @author pcalleja
 */
public class ExecutionSentence {
    
   

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        
        EasyTVInterface interf = new EasyTVInterface("");
        String string=""; String Lang="";
        ESentence es= null;
       //String InputFile = "Ο δάσκαλος εξήγησε το μάθημα στην τάξη";   String Lang = "EL";

      
        
        
        string = "Ho visto questo film tre volte";    Lang = "IT";
        es= interf.processLine(Lang, string);
        es.print();
        
        
        string = "he visto esta película tres veces";    Lang = "ES";
        es= interf.processLine(Lang, string);
        es.print();
        
       // string = "I have watched this movie three times";    Lang = "EN";
       //  string = "Vi una película subtitulada anoche";    Lang = "ES";
       //  string = "Ho visto questo film tre volte";    Lang = "IT";
       // string = "do you like to watch television?.";    Lang = "EN";
       //
    

        //String InputFile = "The teacher explained the lesson";
        //String Lang = "EN";
        
        
        
       
        

    }

   

   

    


   

}
