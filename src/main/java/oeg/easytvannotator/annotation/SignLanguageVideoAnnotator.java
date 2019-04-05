/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.annotation;

import oeg.easytvannotator.babelnet.BabelFyInterface;
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EasyTVInterface;
import oeg.easytvannotator.model.SignLanguageVideo;
import oeg.easytvannotator.model.input.JsonSignLanguageAnnotationInput;
import oeg.easytvannotator.nlp.NLPInterface;
import org.apache.log4j.Logger;

/**
 *
 * @author Pablo
 */
public class SignLanguageVideoAnnotator {
    
    
   
    
    private NLPInterface Nlpinterface;
    
    private BabelNetInterface BabelInterface;
    
    
    private BabelFyInterface  BabelFyInterface;
    
    
    static Logger logger = Logger.getLogger(EasyTVInterface.class);
    
 
    
    
    public SignLanguageVideoAnnotator(String ResourcesPath, String ContextPath, boolean Serviceweb){
        
        Nlpinterface =new NLPInterface(ResourcesPath);

        BabelInterface= new BabelNetInterface(ContextPath,Serviceweb);
        
        BabelFyInterface = new BabelFyInterface();
        
        
    }
    

    
    public ESentence annotateSignLanguageVideo(JsonSignLanguageAnnotationInput Input) {

        
        SignLanguageVideo Video= Input.getVideo();
        String Sentence = Input.getVideo().getNls().trim();
        String Language= Input.getVideo().getLanguage();

        logger.info("Recieved: " + Sentence + ". Lang:" + Language);
     
        // Create E SENTENCE
        ESentence Esentence = Nlpinterface.createESentence(Language.toLowerCase(), Sentence);
        
        //BABELNET
        BabelInterface.callBabelNet(Esentence, Language);

        // Associate Videos
        Esentence.associateVideos(Input);
        return Esentence;

    }
    
   

    public NLPInterface getNlpinterface() {
        return Nlpinterface;
    }

    public void setNlpinterface(NLPInterface Nlpinterface) {
        this.Nlpinterface = Nlpinterface;
    }
    
}
