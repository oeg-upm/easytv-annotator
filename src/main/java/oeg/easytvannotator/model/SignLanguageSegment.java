/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import it.uniroma1.lcl.babelnet.BabelSynset;
import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.babelnet.BabelNetSynset;

/**
 *
 * @author Pablo
 */
public class SignLanguageSegment {
    
    private String order;
    private String start;
    private String end;
    private String content;
    
    
    
    public String Word;
    public String POS;
    public String Lemma;
    public String Stemm;
    public String Language;
    public String NE;
    
    public List<BabelSynset> WordSynsets;
    public List<BabelNetSynset> LemmaSynsets;
    
    public List<String> WordBabelblySemanticAnnotations;
    public List<String> LemmaBabelblySemanticAnnotations;
    
    
    public SignLanguageSegment(){
    
        WordSynsets=new ArrayList();
        LemmaSynsets=new ArrayList();
    }
    
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String Word) {
        this.Word = Word;
    }

    public String getPOS() {
        return POS;
    }

    public void setPOS(String POS) {
        this.POS = POS;
    }

    public String getLemma() {
        return Lemma;
    }

    public void setLemma(String Lemma) {
        this.Lemma = Lemma;
    }

    public String getStemm() {
        return Stemm;
    }

    public void setStemm(String Stemm) {
        this.Stemm = Stemm;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getNE() {
        return NE;
    }

    public void setNE(String NE) {
        this.NE = NE;
    }

    public List<BabelSynset> getWordSynsets() {
        return WordSynsets;
    }

    public void setWordSynsets(List<BabelSynset> WordSynsets) {
        this.WordSynsets = WordSynsets;
    }

    public List<BabelNetSynset> getLemmaSynsets() {
        return LemmaSynsets;
    }

    public void setLemmaSynsets(List<BabelNetSynset> LemmaSynsets) {
        this.LemmaSynsets = LemmaSynsets;
    }

    public List<String> getWordBabelblySemanticAnnotations() {
        return WordBabelblySemanticAnnotations;
    }

    public void setWordBabelblySemanticAnnotations(List<String> WordBabelblySemanticAnnotations) {
        this.WordBabelblySemanticAnnotations = WordBabelblySemanticAnnotations;
    }

    public List<String> getLemmaBabelblySemanticAnnotations() {
        return LemmaBabelblySemanticAnnotations;
    }

    public void setLemmaBabelblySemanticAnnotations(List<String> LemmaBabelblySemanticAnnotations) {
        this.LemmaBabelblySemanticAnnotations = LemmaBabelblySemanticAnnotations;
    }

    
    public void copyTokenInformation(EToken tok) {
        
        content=tok.Word;
        this.Word=tok.Word;
        this.POS=tok.POS;
        this.Lemma=tok.Lemma;
        this.Stemm=tok.Stemm;
        this.Language=tok.Language;
        this.NE=tok.NE;
        this.WordSynsets=tok.WordSynsets;
        this.LemmaSynsets=tok.LemmaSynsets;
        
    
    }

}
