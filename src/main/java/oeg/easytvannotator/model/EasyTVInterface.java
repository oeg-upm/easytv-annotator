/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.nlpinterfaces.NLPInterface;

/**
 *
 * @author pcalleja
 */
public class EasyTVInterface {
    
    
    
    public static List <ESentence> sentences=new ArrayList();
    
    public EasyTVInterface(){
        
        
    
    }
    
     public static void processLine(String Path,String Language, String Sentence) {

        Sentence = Sentence.trim();

        System.out.println("Recieved: " + Sentence + "  Lang:" + Language);
        
        
        NLPInterface inter= new NLPInterface(Path);

        // SENTENCE
        ESentence ESentence = inter.createESentence(Language.toLowerCase(), Sentence); 
        //BABELNET
        BabelNetInterface.callBabelNet(ESentence, Language);

        // BABELFLY
        //BabelFlyInterface.callBabelFly(ESentence, Language);

        sentences.add(ESentence);

    }
    
    
    
    
}
