/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.babelnet;



import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetComparator;
import it.uniroma1.lcl.jlt.util.Language;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;
import com.babelscape.util.UniversalPOS;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import java.util.Arrays;
import java.util.HashSet;



/**
 *
 * @author pcalleja
 */
public class BabelNetInterface {
    
    
    
    
    
    
    public static BabelNet bnInstance;
    
  
    
    public static List<BabelSynset> callBabelNetWord(String word, Language lang)  {

        List<BabelSynset> synsets =new ArrayList();
        try {
            if (bnInstance == null) {
                bnInstance = BabelNet.getInstance();
            }

            
        BabelNetQuery query = new BabelNetQuery.Builder(word)
	
	.from(lang)
        .sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN,BabelSenseSource.WIKIDATA))
			//.to(Arrays.asList(Language.EN))
                       
			.build();
                
        synsets =  bnInstance.getSynsets(query);
        
       Collections.sort(synsets, new BabelSynsetComparator(word));
           
           
        } catch (Exception ex) {
            Logger.getLogger(BabelNetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
         if(synsets.size()>4){
            return synsets.subList(0, 4);
        }
        
        return synsets;
        
    }
    
    
    
    
    
    public static List<BabelSynset> callBabelNetWordPOS(String word, Language lang, UniversalPOS pos)  {

        System.out.println("Babelnet Call: Word-" + word + " Lang-" + lang + " POS-" + pos);

        List<BabelSynset> synsets;

        synsets = BabelDatabase.getConcept(word, pos.toString(), lang.toString());

        if (synsets != null) {
            return synsets;
        }

        synsets = new ArrayList();

        try {
            if (bnInstance == null) {
                bnInstance = BabelNet.getInstance();
            }
            

            System.out.println("change");
            HashSet <BabelSenseSource> sets=new HashSet();
            sets.addAll(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN, BabelSenseSource.MCR_ES ,BabelSenseSource.MCR_CA,BabelSenseSource.OMWN_EL,BabelSenseSource.OMWN_IT));

            BabelNetQuery query = new BabelNetQuery.Builder(word)
                    .POS(pos)
                    .from(lang)
                   // .sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN, BabelSenseSource.MCR_ES ,BabelSenseSource.MCR_CA,BabelSenseSource.OMWN_EL,BabelSenseSource.OMWN_IT)) //
                    .to(Arrays.asList(Language.EN,Language.CA, Language.EL,Language.IT))
                    .build();

            synsets = bnInstance.getSynsets(query);

            System.out.println("Results:" + synsets.size());
            
          
            Collections.sort(synsets, new BabelSynsetComparator(word,lang));
            
             if (synsets.size() > 7) {
            synsets= synsets.subList(0, 7);
            }
        
          
             
        } catch (Exception ex) {
            Logger.getLogger(BabelNetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        BabelDatabase.addConcept(word, pos.toString(), lang.toString(),synsets);
        
        return synsets;
    }

    
    public static List<BabelSynset> callBabelNetWordPOSAmplified(String word, Language lang, UniversalPOS pos)  {

        System.out.println("Babelnet Call: Word-" + word + " Lang-" + lang + " POS-" + pos);

        List<BabelSynset> synsets;

        synsets = BabelDatabase.getConcept(word, pos.toString(), lang.toString());

        if (synsets != null) {
            return synsets;
        }

        synsets = new ArrayList();

        try {
            if (bnInstance == null) {
                bnInstance = BabelNet.getInstance();
            }

            BabelNetQuery query = new BabelNetQuery.Builder(word)
                    .POS(pos)
                    .from(lang)
                    .sources(Arrays.asList(BabelSenseSource.BABELNET)) // BabelSenseSource.WN, BabelSenseSource.MCR_ES, BabelSenseSource.WIKIDATA
                    //.to(Arrays.asList(Language.EN))
                    .build();

            synsets = bnInstance.getSynsets(query);

            System.out.println("Results:" + synsets.size());
            
            
            if(synsets.isEmpty()){
                
                System.out.println("new query withoutpos");
                query = new BabelNetQuery.Builder(word)
                   // .POS(pos)
                    .from(lang)
                    .sources(Arrays.asList(BabelSenseSource.BABELNET, BabelSenseSource.WN, BabelSenseSource.MCR_ES, BabelSenseSource.WIKIDATA))
                    //.to(Arrays.asList(Language.EN))
                    .build();

                synsets = bnInstance.getSynsets(query);

                System.out.println("Results:" + synsets.size());
                
            }

            Collections.sort(synsets, new BabelSynsetComparator(word));
            
        } catch (Exception ex) {
            Logger.getLogger(BabelNetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (synsets.size() > 7) {
            synsets= synsets.subList(0, 7);
        }
        
        BabelDatabase.addConcept(word, pos.toString(), lang.toString(),synsets);
        
        return synsets;
    }

  
    
    public static void callBabelNet(ESentence Sentence, String Lang) {

        Language Langua = BabelLangInterface.getLangType(Lang);

        for (EToken token : Sentence.ListTokens) {

            String StanPos = token.POS.toUpperCase();
            UniversalPOS Babelpos = BabelPosInterface.getBabelPOS(StanPos, Lang);

            if (Babelpos != null) {
                token.WordSynsets = callBabelNetWordPOS(token.Word, Langua, Babelpos);
                token.LemmaSynsets = callBabelNetWordPOS(token.Lemma, Langua, Babelpos);
                token.cleanSynsets();

            } else {
                System.out.println("No BabelPOS for :" + token.Word + "  " + token.POS);
            }

        }

    }

    public static void callBabelNet(BabelNet bnInstance, ESentence Sentence, String Lang) {

        BabelNetInterface.bnInstance = bnInstance;
        Language Langua = BabelLangInterface.getLangType(Lang);

        for (EToken token : Sentence.ListTokens) {

            String StanPos = token.POS.toUpperCase();
            UniversalPOS Babelpos = BabelPosInterface.getBabelPOS(StanPos, Lang);

            if (Babelpos != null) {
                token.WordSynsets = callBabelNetWordPOS(token.Word, Langua, Babelpos);
                token.LemmaSynsets = callBabelNetWordPOS(token.Lemma, Langua, Babelpos);

            } else {
                System.out.println("No BabelPOS for :" + token.Word + "  " + token.POS);
            }

        }

    }



}
