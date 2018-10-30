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
    
    
    public BabelNet bnInstance;
    
    
    public boolean serviceweb=false;
    
    public String ContextPath;
    
    
    public BabelNetInterface(String ContextPath, boolean service){
    
        this.serviceweb= service;
        this.ContextPath= ContextPath;
        initInstance();
    
    }
    
    
    public void initInstance() {

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

    
    public List<BabelNetSynset> callBabelNetWordPOS(String word, Language lang, UniversalPOS pos) {

        logger.info("Babelnet Call: Word-" + word + " Lang-" + lang + " POS-" + pos);

        List<BabelSynset> synsets=null;

        //synsets = BabelDatabase.getConcept(word, pos.toString(), lang.toString());
        List<BabelNetSynset> synseList= null;
        if (synsets != null) {
            logger.info("synsets already identified");
            return synseList;
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

            synseList= transformToBabelNetSynsets(synsets, word,lang);//Collections.sort(synsets, new BabelSynsetComparator(word, lang));

            if (synseList.size() > 5) {
                synseList = synseList.subList(0, 5);
            }
            
            
            
            //BabelDatabase.addConcept(word, pos.toString(), lang.toString(), synsets);
        } catch (Exception ex) {
            logger.error("Error in BabelNet Communication",ex);
            logger.error(ex.getCause().toString());
            //initInstance();
            
        }

        

        return synseList;
    }

    
    
    public void callBabelNet(ESentence Sentence, String Lang) {

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

    

    
    public List <BabelNetSynset> transformToBabelNetSynsets(List<BabelSynset>list, String word, Language lang){
    
        List <BabelNetSynset> lis= new ArrayList();
        
        for(BabelSynset sin: list){
            
            lis.add(new BabelNetSynset(sin,word,lang));
            
        
        }
        
        Collections.sort(lis);
       return lis;
        
    
    }
 

}
