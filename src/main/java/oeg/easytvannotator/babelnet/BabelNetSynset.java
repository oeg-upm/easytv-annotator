/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;

import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.jlt.util.Language;
import java.util.Comparator;

/**
 *
 * @author Pablo
 */
public class BabelNetSynset implements Comparable<BabelNetSynset>  {
    
    
    public boolean isKey;
    public String ID;
    public String Type;
    
    //public String MainSense;
    
    public String OriginalWord;
    public String SimpleLemma;
    public String Language;
    
    public BabelSynset syn;
    
    public BabelSense MainSense;
    
    public BabelNetSynset(BabelSynset synset,String OriginalWord,Language lang){
    
        syn=synset;
        this.Language=lang.toString();
        this.OriginalWord = OriginalWord;
        isKey= synset.isKeyConcept();
        ID   = synset.getID().toString();
        Type = synset.getType().toString();
        MainSense= synset.getMainSense(BabelLangInterface.getLangType(Language)).get();
        SimpleLemma = parseLemma(MainSense.getSimpleLemma());
        
        
        
      
        // NUMERO DE SENSES synset.
         
           //System.out.println("Synset ID: " + synset.getID()+  "; TYPE: " + synset.getType()+  "  MAIN SENSE ES: " + synset.getMainSense(lang) + synset.getMainSense(Language.ES) +"  MAIN SENSE EN:"+synset.getMainSense(Language.EN) );
        
    
    
    }
    
    public BabelSense getMainSense(){
    
         return  MainSense;
    
    }
    
    public String parseLemma(String lemma){

        return lemma.replaceAll("Ã¡", "á").replaceAll("Ã©", "é").replaceAll("Ã­", "í").replaceAll("Ã³", "ó").replaceAll("Ãº", "ú");

    }

    @Override
    public int compareTo(BabelNetSynset o) {
        

        if(Type.toLowerCase().equals("concept")){
        
            if(SimpleLemma.toLowerCase().equals(OriginalWord.toLowerCase())){
            
                if(isKey){
                    return -3;
                
                }else{
                   
                    return -1;
                }
                   
                
            
            }else{
            
                return 1;
            
            }
        
            
            
        }else{
        
            return 3;
        
        }
        
        
    }
    
}
