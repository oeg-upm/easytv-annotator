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
    
    private BabelNetInterface BabelInterface;
    
    
    static Logger logger = Logger.getLogger(EasyTVInterface.class);
    
    //public EasyTVInterface(){}
    
    
    public EasyTVInterface(String ResourcesPath, String ContextPath, boolean Serviceweb){
        
        Nlpinterface =new NLPInterface(ResourcesPath);

        BabelInterface= new BabelNetInterface(ContextPath,Serviceweb);
        
    }
    
    
    public ESentence procesSentence(String Language, String Sentence) {

        Sentence = Sentence.trim();
        logger.info("Recieved: " + Sentence + "  Lang:" + Language);

        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        logger.info("ESentence created: NºTokens " + Esentence.ListTokens.size());
        //BABELNET
        BabelInterface.callBabelNet(Esentence, Language);
        
        return Esentence;
    }
    
    
    public ESentence processTranslation(String Language1, String Language2, String Sentence) {

        Sentence = Sentence.trim();
        logger.info("Recieved: " + Sentence + "  Lang:" + Language1+ " To Lang:  "+Language2);
       
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language1.toLowerCase(), Sentence);
        
        logger.info("ESentence created: NºTokens " + Esentence.ListTokens.size());
        //BABELNET
        BabelInterface.callTranslationBabelNet(Esentence, Language1,Language2);
        
        return Esentence;
    }
     
    public ESentence processJson(JsonInput input) {

        this.input=input;
        String Sentence = input.getVideo().getNls().trim();
        String Language=input.getVideo().getLanguage();

        logger.info("Recieved: " + Sentence + "  Lang:" + Language);
     
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        logger.info("ESentence created: NºTokens " + Esentence.ListTokens.size());
        //BABELNET
        BabelInterface.callBabelNet(Esentence, Language);

        // Associate Videos
        Esentence.associateVideos(input);
        return Esentence;

    }
      
    
    public ESentence annotateVideo(JsonInput input) {

        this.input=input;
        String Sentence = input.getVideo().getNls().trim();
        String Language=input.getVideo().getLanguage();

        logger.info("Recieved: " + Sentence + "  Lang:" + Language);
     
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        //BABELNET
        BabelInterface.callBabelNet(Esentence, Language);

        // Associate Videos
        Esentence.associateVideos(input);
        return Esentence;

    }
    
    public ESentence annotateTranslateVideo(JsonInput input,String translationlang) {

        this.input=input;
        String Sentence = input.getVideo().getNls().trim();
        String Language=input.getVideo().getLanguage();

        logger.info("Recieved: " + Sentence + "  Lang:" + Language +" Translation: "+translationlang);
     
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        //BABELNET
        BabelInterface.callTranslationBabelNet(Esentence, Language,translationlang);

        // Associate Videos
        Esentence.associateVideos(input);
        return Esentence;

    }
    
  

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
