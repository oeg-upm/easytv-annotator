/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import com.babelscape.util.UniversalPOS;

/**
 *
 * @author pcalleja
 */
public class BabelPosInterface {
      
    public static UniversalPOS getBabelPOS(String StanfordPOS, String Lang){
     
        if (StanfordPOS.startsWith("V")) {
            return UniversalPOS.VERB;
        }

        if (StanfordPOS.startsWith("N")) {
            return UniversalPOS.NOUN;
        }
        
       

        if (Lang.equals("EN")) {

            if (StanfordPOS.equals("JJ")) {
                return UniversalPOS.ADJ;

            }

            if (StanfordPOS.equals("DT")) {
                //return UniversalPOS.DET;

            }

        }

        if (Lang.equals("ES")) {
            
            if (StanfordPOS.startsWith("A")) {
                return UniversalPOS.ADJ;

            }

            if (StanfordPOS.startsWith("R")) {
                return UniversalPOS.ADV;

            }
            
            if (StanfordPOS.startsWith("D")) {
                //return UniversalPOS.DET;

            }

            if (StanfordPOS.startsWith("T")) {
                //return UniversalPOS.ADP;

            }
            
            if (StanfordPOS.startsWith("PP")) {
                //return UniversalPOS.PRON;

            }
            
           
            
            
        }
        
        if (Lang.equals("EL")) {
            
            if (StanfordPOS.startsWith("A")) {
                return UniversalPOS.ADJ;

            }

            if (StanfordPOS.startsWith("R")) {
                return UniversalPOS.ADV;

            }
            
            if (StanfordPOS.startsWith("D")) {
                //return UniversalPOS.DET;

            }

            if (StanfordPOS.startsWith("T")) {
                //return UniversalPOS.ADP;

            }
            
            if (StanfordPOS.startsWith("PP")) {
                //return UniversalPOS.PRON;

            }
            
           
            
            
        }

        return null;

    }
}
