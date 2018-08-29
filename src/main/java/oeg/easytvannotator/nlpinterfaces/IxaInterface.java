/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlpinterfaces;

import com.google.common.io.Files;
import eus.ixa.ixa.pipe.ml.utils.Flags;
import eus.ixa.ixa.pipe.parse.ConstituentParsing;
import eus.ixa.ixa.pipe.pos.Annotate;
import eus.ixa.ixa.pipe.pos.CLI;
import ixa.kaflib.Entity;
import ixa.kaflib.KAFDocument;
import ixa.kaflib.Term;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.easytvannotator.demos.IxaDemo;
import oeg.easytvannotator.model.ESentence;

/**
 *
 * @author pcalleja
 */
public class IxaInterface implements NLPApi{
    
    

    private static String PosModel;
    private static String lemmatizerModel;
    private static String NERModel;
    private static String ParserModel;
   
    private static  String language;
    private static  String kafVersion;

    
    
    static eus.ixa.ixa.pipe.nerc.Annotate neAnnotator;
    static eus.ixa.ixa.pipe.pos.Annotate posAnnotator;
    static eus.ixa.ixa.pipe.parse.Annotate parserAnnotator;
    static ConstituentParsing parserConstituent;
    
    private static Properties annotatePosProperties;
    private static Properties annotateNEProperties;
    private static Properties annotateParserProperties;

  
  
  // Parse properties
    private static final String parseVersion = eus.ixa.ixa.pipe.parse.CLI.class.getPackage().getImplementationVersion();
    private static final String parseCommit = eus.ixa.ixa.pipe.parse.CLI.class.getPackage().getSpecificationVersion();
    
    private boolean Init=false;
    
    
    public IxaInterface(String RootPath, String Lang){
    
        if(Lang.equals("es")){
        
            initProperties(RootPath);
        }
        if(Lang.equals("it")){
        
            initProperties2(RootPath);
        }
    
    
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
                
                System.out.println("IXA: "+ term.getStr()+"(word)  "+term.getLemma()+"(lemma) "+term.getMorphofeat()+"(POS)  ");
                esentence.addEToken(term.getStr(), term.getMorphofeat(), term.getLemma(), term.getStr(), Lang);
                tokenSentence.append(term.getStr()+" ");
                LemmaSentence.append(term.getLemma()+" ");
                
            }
            
            
            
            esentence.OriginalText=Sentence;
            esentence.LematizedText= LemmaSentence.toString().trim();
            
            //kaf.save("ixadoc.xml");
            return esentence;
            
        } catch (IOException ex) {
            Logger.getLogger(IxaInterface.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error"+ex.toString());
        }
            return new ESentence();
     }



    
    
 

    private void initProperties(String Path) {
       
                    
            if(Init){return;}
            
            Init=true;
            
            String multiwords;
            String dictag;
            String noseg;
            
            String normalize; //Set normalization method according to corpus; the default option does not escape "brackets or forward slashes.
            String untokenizable; //Print untokenizable characters.
            String hardParagraph; //Do not segment paragraphs. Ever.
            
            
            PosModel              = new File(Path+"resources/models/morph-models-1.5.0/es/es-pos-perceptron-autodict01-ancora-2.0.bin").getAbsolutePath();
            lemmatizerModel    = new File(Path+"resources/models/morph-models-1.5.0/es/es-lemma-perceptron-ancora-2.0.bin").getAbsolutePath();
            language           = "es";
            kafVersion         = "1.5.0";
            
            multiwords         = "false"; //true
            dictag             = new File(Path+"resources/models/tag").getAbsolutePath();
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
                
            }
            
            // NER
            
            
            NERModel = new File(Path+"resources/models/morph-models-1.5.0/es/es-4-class-clusters-dictlbj-ancora.bin").getAbsolutePath();
            language = "es";
            dictag = new File("resources/models/tag").getAbsolutePath();
            
            
            
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
                 //throw new RuntimeException("Error init",e);
               }
             
             
         //   Thread.sleep(5000);
            
            
            
            // PARSER
            
            /*
            ParserModel = new File(Path+"resources/models/parse-models/es-parser-chunking.bin").getAbsolutePath();
            language = "es";
            String header=  eus.ixa.ixa.pipe.parse.Flags.DEFAULT_HEADFINDER;
            
             String out=  eus.ixa.ixa.pipe.parse.Flags.DEFAULT_OUTPUT_FORMAT;
            System.out.println(header+out);
            
            annotateParserProperties = new Properties();
            annotateParserProperties.setProperty("model", NERModel);
            annotateParserProperties.setProperty("language", language);
            annotateParserProperties.setProperty("headFinder", header);
           // annotateParserProperties.setProperty("outputFormat", out);
            //annotateNEProperties.setProperty("headFinder",);
            
            
           // parserConstituent=            new ConstituentParsing(annotateParserProperties);
            
            
            //final eus.ixa.ixa.pipe.parse.Annotate annotator = new eus.ixa.ixa.pipe.parse.Annotate(annotateParserProperties);
            
            */
            
        
        
 
            
        
        
        
    }
    
    
    private void initProperties2(String Path) {
       
                    
            if(Init){return;}
            
            Init=true;
            
            String multiwords;
            String dictag;
            String noseg;
            
            String normalize; //Set normalization method according to corpus; the default option does not escape "brackets or forward slashes.
            String untokenizable; //Print untokenizable characters.
            String hardParagraph; //Do not segment paragraphs. Ever.
            
            
            PosModel              = new File(Path+"resources/models/ud-morph-models-1.5.0/it/it-pos-perceptron-autodict01-ud.bin").getAbsolutePath();
            lemmatizerModel    = new File(Path+"resources/models/ud-morph-models-1.5.0/it/it-lemma-perceptron-ud.bin").getAbsolutePath();
            language           = "it";
            kafVersion         = "1.5.0";
            
            multiwords         = "false"; //true
            dictag             = new File(Path+"resources/models/tag").getAbsolutePath();
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
                
            }
            
            // NER
            
            
            NERModel = new File(Path+"resources/models/nerc-models-1.5.4/it/it-clusters-evalita09.bin").getAbsolutePath();
            language = "it";
            dictag = new File("resources/models/tag").getAbsolutePath();
            
            
            
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
                 //throw new RuntimeException("Error init",e);
               }
             
             
    
        
    }

}
