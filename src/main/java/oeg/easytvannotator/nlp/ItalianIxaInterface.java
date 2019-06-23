/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import com.google.common.io.Files;
import eus.ixa.ixa.pipe.ml.utils.Flags;
import eus.ixa.ixa.pipe.parse.ConstituentParsing;
import eus.ixa.ixa.pipe.pos.Annotate;
import eus.ixa.ixa.pipe.pos.CLI;
import ixa.kaflib.KAFDocument;
import ixa.kaflib.Term;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EasyTVInterface;
import org.apache.log4j.Logger;

/**
 *
 * @author pcalleja
 */
public class ItalianIxaInterface implements NLPApi{
    
    

    private  String PosModel;
    private  String lemmatizerModel;
    private  String NERModel;
    private  String ParserModel;
   
    private   String language;
    private   String kafVersion;

    
    
    private eus.ixa.ixa.pipe.nerc.Annotate neAnnotator;
    private eus.ixa.ixa.pipe.pos.Annotate posAnnotator;
    private eus.ixa.ixa.pipe.parse.Annotate parserAnnotator;
    private ConstituentParsing parserConstituent;
    
    private  Properties annotatePosProperties;
    private  Properties annotateNEProperties;
    private  Properties annotateParserProperties;

  
  
  // Parse properties
    private  final String parseVersion = eus.ixa.ixa.pipe.parse.CLI.class.getPackage().getImplementationVersion();
    private  final String parseCommit = eus.ixa.ixa.pipe.parse.CLI.class.getPackage().getSpecificationVersion();
    //  both were static
    
    static Logger logger = Logger.getLogger(ItalianIxaInterface.class);
    
    private boolean Init=false;
    
    
    public ItalianIxaInterface(String RootPath){
    
      initProperties(RootPath);
      
    }
  
    
    @Override
     public ESentence parseSentence(String Sentence, String Lang)  {
         
         ///initProperties(Path);
     
        try {
            ESentence esentence=new ESentence();
            StringBuffer tokenSentence=new StringBuffer();
            StringBuffer LemmaSentence=new StringBuffer();
            
            
            /// call
            KAFDocument kaf;
            
            // CREATES DE DOCUMENT
            InputStream is = new ByteArrayInputStream(Sentence.getBytes());
            BufferedReader breader = new BufferedReader(new InputStreamReader(is));
            kaf = new KAFDocument(language, kafVersion);
            
            String version = CLI.class.getPackage().getImplementationVersion();
            String commit = CLI.class.getPackage().getSpecificationVersion();
            
            eus.ixa.ixa.pipe.tok.Annotate tokAnnotator = new eus.ixa.ixa.pipe.tok.Annotate(breader, annotatePosProperties);
            
            // Tokenize
            tokAnnotator.tokenizeToKAF(kaf);
            
            // PosTag
            KAFDocument.LinguisticProcessor newLp = kaf.addLinguisticProcessor("terms", "ixa-pipe-pos-" + Files.getNameWithoutExtension(PosModel), version + "-" + commit);
            newLp.setBeginTimestamp();
            posAnnotator.annotatePOSToKAF(kaf);
            newLp.setEndTimestamp();
            
            // NER
            KAFDocument.LinguisticProcessor newLp2 = kaf.addLinguisticProcessor("entities", "ixa-pipe-nerc-" + Files.getNameWithoutExtension(NERModel), version + "-" + commit);
            newLp2.setBeginTimestamp();
            neAnnotator.annotateNEs(kaf);
            newLp2.setEndTimestamp();
            
            
            // PARSER STILL IN PROGRESS
            /*
            
            // Parse properties
             KAFDocument.LinguisticProcessor newLp3= kaf.addLinguisticProcessor("constituency", "ixa-pipe-parse-" + Files.getNameWithoutExtension(ParserModel), parseVersion + "-" + parseCommit);
            newLp3.setBeginTimestamp();
            final eus.ixa.ixa.pipe.parse.Annotate annotator = new eus.ixa.ixa.pipe.parse.Annotate(annotateParserProperties);
            annotator.parseToKAF(kaf);
            newLp3.setEndTimestamp();
            
           
            */
            
            
             for (Term term : kaf.getTerms()) {
                
                logger.info("IXA: "+ term.getStr()+"(word)  "+term.getLemma()+"(lemma) "+term.getMorphofeat()+"(POS)  ");
                esentence.addEToken(term.getStr(), term.getMorphofeat(), term.getLemma(), term.getStr(), Lang);
                tokenSentence.append(term.getStr()+" ");
                LemmaSentence.append(term.getLemma()+" ");
                
            }
            
            
            
            esentence.OriginalText=Sentence;
            esentence.LematizedText= LemmaSentence.toString().trim();
            
            //kaf.save("ixadoc.xml");
            return esentence;
            
        } catch (IOException ex) {
            logger.error( ex);
        }
            return new ESentence();
     }



    
  
    
    private void initProperties(String Path) {
       
            logger.info("init properties for italian");        
            if(Init){return;}
            
            Init=true;
            
            String multiwords;
            String dictag;
            String noseg;
            
            String normalize; //Set normalization method according to corpus; the default option does not escape "brackets or forward slashes.
            String untokenizable; //Print untokenizable characters.
            String hardParagraph; //Do not segment paragraphs. Ever.
            
            
            PosModel              = new File(Path+"Ixa/models/ud-morph-models-1.5.0/it/it-pos-perceptron-autodict01-ud.bin").getAbsolutePath();
            lemmatizerModel    = new File(Path+"Ixa/models/ud-morph-models-1.5.0/it/it-lemma-perceptron-ud.bin").getAbsolutePath();
            language           = "it";
            kafVersion         = "1.5.0";
            
            multiwords         = "false"; //true
            dictag             = new File(Path+"Ixa/models/tag").getAbsolutePath();
            normalize          = "true";
            untokenizable      = "false"; // false
            hardParagraph      = "false";
            noseg              = "false";
            
            
            annotatePosProperties = new Properties();
            
            annotatePosProperties.setProperty("normalize", normalize);
            annotatePosProperties.setProperty("untokenizable", untokenizable);
            annotatePosProperties.setProperty("hardParagraph", hardParagraph);
            annotatePosProperties.setProperty("noseg",noseg);
            annotatePosProperties.setProperty("model", PosModel);
            annotatePosProperties.setProperty("lemmatizerModel", lemmatizerModel);
            annotatePosProperties.setProperty("language", language);
            annotatePosProperties.setProperty("multiwords", multiwords);
            annotatePosProperties.setProperty("dictTag", dictag);
            annotatePosProperties.setProperty("dictPath", dictag);
            annotatePosProperties.setProperty("ruleBasedOption", dictag);
            
            try {
                posAnnotator    = new Annotate(annotatePosProperties);
            } catch (IOException e) {
                logger.error(e);
            }
            
            // NER
            
            
            NERModel = new File(Path+"Ixa/models/nerc-models-1.5.4/it/it-clusters-evalita09.bin").getAbsolutePath();
            language = "it";
            dictag = new File("Ixa/models/tag").getAbsolutePath();
            
            
            
            annotateNEProperties = new Properties();
            annotateNEProperties.setProperty("model", NERModel);
            annotateNEProperties.setProperty("language", language);
            annotateNEProperties.setProperty("ruleBasedOption", Flags.DEFAULT_LEXER);
            annotateNEProperties.setProperty("dictTag", Flags.DEFAULT_DICT_OPTION);
            annotateNEProperties.setProperty("dictPath", Flags.DEFAULT_DICT_PATH);
            annotateNEProperties.setProperty("clearFeatures", Flags.DEFAULT_FEATURE_FLAG);
            
            
                    try {
                   neAnnotator    = new eus.ixa.ixa.pipe.nerc.Annotate(annotateNEProperties);
               } catch (IOException e) {
                 logger.error(e);
               }
             
             
    
        
    }

}
