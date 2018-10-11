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
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;
import com.babelscape.util.UniversalPOS;
import it.uniroma1.lcl.babelnet.BabelNetConfiguration;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.Configuration;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.log4j.Logger;



/**
 *
 * @author pcalleja
 */
public class BabelNetInterface {
    
    
    static Logger logger = Logger.getLogger(BabelNetInterface.class);
    
    
    public static BabelNet bnInstance;
    
    
    public static boolean serviceweb=false;
    
    public static String ContextPath;
    
    public static void initInstance() {

        if (serviceweb) {
            // context.getRealPath("/") 
            Configuration jltConfiguration = Configuration.getInstance();
            jltConfiguration.setConfigurationFile(new File(ContextPath + "WEB-INF/config/jlt.properties"));

            BabelNetConfiguration bnconf = BabelNetConfiguration.getInstance();
            bnconf.setConfigurationFile(new File(ContextPath + "/WEB-INF/config/babelnet.properties"));
            bnconf.setBasePath(ContextPath + "/WEB-INF/");
            //BabelNet bn =  BabelNet.getInstance();
            bnInstance = BabelNet.getInstance();
            return;

        }

        bnInstance = BabelNet.getInstance();
        

    }

    
    public static List<BabelSynset> callBabelNetWordPOS(String word, Language lang, UniversalPOS pos) {

        logger.info("Babelnet Call: Word-" + word + " Lang-" + lang + " POS-" + pos);

        List<BabelSynset> synsets;

        synsets = BabelDatabase.getConcept(word, pos.toString(), lang.toString());

        if (synsets != null) {
            logger.info("synsets already identified");
            return synsets;
        }

        synsets = new ArrayList();

        try {
            if (bnInstance == null) {
                initInstance();//bnInstance = BabelNet.getInstance();
            }

            HashSet<BabelSenseSource> sets = new HashSet();
            sets.addAll(Arrays.asList(BabelSenseSource.BABELNET, BabelSenseSource.WN, BabelSenseSource.MCR_ES, BabelSenseSource.MCR_CA, BabelSenseSource.OMWN_EL, BabelSenseSource.OMWN_IT));

            BabelNetQuery query = new BabelNetQuery.Builder(word)
                    .POS(pos)
                    .from(lang)
                    // .sources(Arrays.asList(BabelSenseSource.BABELNET,BabelSenseSource.WN, BabelSenseSource.MCR_ES ,BabelSenseSource.MCR_CA,BabelSenseSource.OMWN_EL,BabelSenseSource.OMWN_IT)) //
                    .to(Arrays.asList(Language.EN, Language.CA, Language.EL, Language.IT))
                    .build();

            synsets = bnInstance.getSynsets(query);

            logger.info("BabelNet Results:" + synsets.size());

            Collections.sort(synsets, new BabelSynsetComparator(word, lang));

            if (synsets.size() > 7) {
                synsets = synsets.subList(0, 7);
            }
            
            
            BabelDatabase.addConcept(word, pos.toString(), lang.toString(), synsets);
        } catch (Exception ex) {
            logger.error(ex);
            logger.error(ex.toString());
            initInstance();
            
        }

        

        return synsets;
    }

    
    
    public static void callBabelNet(ESentence Sentence, String Lang) {

        Language Langua = BabelLangInterface.getLangType(Lang);

        for (EToken token : Sentence.ListTokens) {

            String StanPos = token.POS.toUpperCase();
            UniversalPOS Babelpos = BabelPosInterface.getBabelPOS(StanPos, Lang);

            if (Babelpos != null) {
                //token.WordSynsets = callBabelNetWordPOS(token.Word, Langua, Babelpos);
                token.LemmaSynsets = callBabelNetWordPOS(token.Lemma, Langua, Babelpos);
                token.cleanSynsets();

            } else {
                logger.info("No BabelNet search for :" + token.Word + "  " + token.POS);
            }

        }

    }

    

    /*
    public static void callBabelNet(BabelNet bnInstance, ESentence Sentence, String Lang) {

        BabelNetInterface.bnInstance = bnInstance;
        Language Langua = BabelLangInterface.getLangType(Lang);

        for (EToken token : Sentence.ListTokens) {

            String StanPos = token.POS.toUpperCase();
            UniversalPOS Babelpos = BabelPosInterface.getBabelPOS(StanPos, Lang);

            if (Babelpos != null) {
                //token.WordSynsets = callBabelNetWordPOS(token.Word, Langua, Babelpos);
                token.LemmaSynsets = callBabelNetWordPOS(token.Lemma, Langua, Babelpos);

            } else {
                logger.info("No BabelPOS for :" + token.Word + "  " + token.POS);
            }

        }

    }
    
    @Deprecated
    public static List<BabelSynset> callBabelNetWordPOSAmplified(String word, Language lang, UniversalPOS pos) {

        logger.info("BabelNet Call: Word-" + word + " Lang-" + lang + " POS-" + pos);

        List<BabelSynset> synsets;

        synsets = BabelDatabase.getConcept(word, pos.toString(), lang.toString());

        if (synsets != null) {
            return synsets;
        }

        synsets = new ArrayList();

        try {
            if (bnInstance == null) {
                initInstance();//bnInstance = BabelNet.getInstance();
            }

            BabelNetQuery query = new BabelNetQuery.Builder(word)
                    .POS(pos)
                    .from(lang)
                    .sources(Arrays.asList(BabelSenseSource.BABELNET)) // BabelSenseSource.WN, BabelSenseSource.MCR_ES, BabelSenseSource.WIKIDATA
                    //.to(Arrays.asList(Language.EN))
                    .build();

            synsets = bnInstance.getSynsets(query);

            logger.info("BabelNet Results:" + synsets.size());

            if (synsets.isEmpty()) {

                System.out.println("new query withoutpos");
                query = new BabelNetQuery.Builder(word)
                        // .POS(pos)
                        .from(lang)
                        .sources(Arrays.asList(BabelSenseSource.BABELNET, BabelSenseSource.WN, BabelSenseSource.MCR_ES, BabelSenseSource.WIKIDATA))
                        //.to(Arrays.asList(Language.EN))
                        .build();

                synsets = bnInstance.getSynsets(query);

                logger.info("Results:" + synsets.size());

            }

            Collections.sort(synsets, new BabelSynsetComparator(word));

        } catch (Exception ex) {
            logger.error(ex);
        }

        if (synsets.size() > 7) {
            synsets = synsets.subList(0, 7);
        }

        BabelDatabase.addConcept(word, pos.toString(), lang.toString(), synsets);

        return synsets;
    }

    
    @Deprecated
    public static List<BabelSynset> callBabelNetWord(String word, Language lang) {

        List<BabelSynset> synsets = new ArrayList();
        try {
            if (bnInstance == null) {
                //bnInstance = BabelNet.getInstance();
                initInstance();
            }

            BabelNetQuery query = new BabelNetQuery.Builder(word)
                    .from(lang)
                    .sources(Arrays.asList(BabelSenseSource.BABELNET, BabelSenseSource.WN, BabelSenseSource.WIKIDATA))
                    //.to(Arrays.asList(Language.EN))

                    .build();

            synsets = bnInstance.getSynsets(query);

            Collections.sort(synsets, new BabelSynsetComparator(word));

        } catch (Exception ex) {
            logger.error(ex);
        }
        if (synsets.size() > 4) {
            return synsets.subList(0, 4);
        }

        return synsets;

    }

*/

}
