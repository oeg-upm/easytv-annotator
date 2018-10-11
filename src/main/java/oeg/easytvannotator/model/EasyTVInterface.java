/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.nlp.NLPInterface;
import org.apache.log4j.Logger;
/**
 *
 * @author pcalleja
 */
public class EasyTVInterface {
      
   
    
    private JsonInput input;
    
    private NLPInterface Nlpinterface;
    
    
    static Logger logger = Logger.getLogger(EasyTVInterface.class);
    
    //public EasyTVInterface(){}
    
    
    public EasyTVInterface(String ResourcesPath){
        Nlpinterface =new NLPInterface(ResourcesPath);
        
    }
    
    
    public ESentence processLine(String Language, String Sentence) {

        Sentence = Sentence.trim();
        logger.info("Recieved: " + Sentence + "  Lang:" + Language);
        
        
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        //BABELNET
        BabelNetInterface.callBabelNet(Esentence, Language);
        
        return Esentence;

    }
     
      public ESentence processJson(JsonInput input) {

        this.input=input;
        String Sentence = input.getVideo().getNls().trim();
        String Language=input.getVideo().getLanguage();

        logger.info("Recieved: " + Sentence + "  Lang:" + Language);

        
        
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        //BABELNET
        BabelNetInterface.callBabelNet(Esentence, Language);

        // Associate Videos
        Esentence.associateVideos(input);
        return Esentence;

    }
  
    /*
    public ESentence processJson( JsonInput input) {
    
    this.input=input;
    String Sentence = input.getVideo().getNls().trim();
    String Language=input.getVideo().getLanguage();
    
    System.out.println("Recieved: " + Sentence + "  Lang:" + Language);
    
    // SENTENCE
    this.Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
    //BABELNET
    BabelNetInterface.callBabelNet(Esentence, Language);
    
    // Associate Videos
    this.Esentence.associateVideos(input);
    return Esentence;
    
    }*/
    
   

    
    
    /*
     public static void print(List<ESentence> sentences) {

        for (ESentence sentence : sentences) {

            System.out.println("Sentence: " + sentence.OriginalText);

            for (EToken token : sentence.ListTokens) {

                String s = token.printResultsBabel();

            }

        }

    }
     
         
     public static void printSentences(){
         print(sentences);
     
     }
*/


    public JsonInput getInput() {
        return input;
    }

    public void setInput(JsonInput input) {
        this.input = input;
    }

    public NLPInterface getNlpinterface() {
        return Nlpinterface;
    }

    public void setNlpinterface(NLPInterface Nlpinterface) {
        this.Nlpinterface = Nlpinterface;
    }
    
     
     
     
    
}
