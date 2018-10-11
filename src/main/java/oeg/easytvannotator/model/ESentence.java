/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pcalleja
 */
public class ESentence {
    
    
    public List <EToken> ListTokens;
    
    public String OriginalText;
    public String LematizedText;
    
    public ESentence(){
        ListTokens=new ArrayList();
    }
    
    
    public void addEToken(String word,String POS, String Lemma,String Stemm,String Language){
    
        this.ListTokens.add(new EToken(word,POS,Lemma,Stemm,Language));
    
    }

    public void associateVideos(JsonInput input) {
        
        for(EToken tok: ListTokens){
            
            for(SignLanguageSegment seg:input.getVideo().getSegments()){
                
                if(tok.Word.equals(seg.getContent())){
                   // tok.copyVideoInforamtion(seg);
                    seg.copyTokenInformation(tok);
                    break;
                }
            }
            
        }
        
     }
    
    
     public void print(){
     
     
          System.out.println("Sentence: " + this.OriginalText);

            for (EToken token : this.ListTokens) {

                String s = token.printResultsBabel();

            }
     }
    
}
