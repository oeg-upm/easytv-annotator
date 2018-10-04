/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;

/**
 *
 * @author pcalleja
 */
public class NLPInterface {
    
    
    
    public String RootPath="";
    
    
    public IxaInterface SpanishLib;
    public IxaInterface ItalianLib;
    public StanfordInterface EnglishLib;
    public TreeTaggerInterface GreekLib;
    
    
    public NLPInterface(String RootPath){
    
        this.RootPath=RootPath;
        SpanishLib = new IxaInterface(RootPath,"es");
        
        
        ItalianLib = new IxaInterface(RootPath,"it");
        
        EnglishLib= new StanfordInterface();
        
        GreekLib= new TreeTaggerInterface(RootPath);
    
    }
    
    
    
    public ESentence createESentence(String Language, String Sentence) {

        if (Language.equals("es")) {
            return SpanishLib.parseSentence(Sentence, Language);
        }
        if (Language.equals("en")) {
            return EnglishLib.parseSentence(Sentence, Language);
        }

        if (Language.equals("it")) {
            return ItalianLib.parseSentence(Sentence, Language);
        }

        if (Language.equals("cat")) {
        }

        if (Language.equals("el")) {
      
            return GreekLib.parseSentence(Sentence, Language);
        }
        return null;

    }

   
}
