/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.demos;


import java.io.IOException;
import static oeg.easytvannotator.demos.IxaDemo.readFile;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EasyTVInterface;
/**
 *
 * @author Pablo
 */
public class Service {
    
    
    
     public static void main (String [] args) throws IOException{
    
        
        EasyTVInterface interf = new EasyTVInterface("","",false);
        String string=""; String Lang="";String Lang2="";
        ESentence es= null;
       //String InputFile = "Ο δάσκαλος εξήγησε το μάθημα στην τάξη";   String Lang = "EL";

         
        
        string = "película";    Lang = "ES"; Lang2="EL";
        es= interf.processTranslation(Lang, Lang2, string);
        es.print();
        
        
        /*
                MappingTranslator translator = new AutomaticTranslator();
		long startTime1 = System.currentTimeMillis();
		String rawMapping1 = readFile("/Users/cimmino/workspace/oeg/semantic-engine/mappings/plugins-mapping.json");
		
		
		Mapping mapping1 = translator.translate(rawMapping1);
				
	
		long estimatedTime1 = System.currentTimeMillis() - startTime1;
		System.out.println("Mapping generation in memory took: "+estimatedTime1+"ns  ");
		EngineImp engine = new EngineImp(mapping1);
                engine.getMapping().
		engine.initialize();
		System.out.println(engine.publishRDF());
		engine.close();
        */
        /*
         
       string = "Σε αυτή τη σπηλιά ζει μια αρκούδα";    Lang = "EL";
        es= interf.procesSentence(Lang, string);
        es.print();
        
        string = "un ós viu en aquesta cova";    Lang = "CA";
        es= interf.procesSentence(Lang, string);
        es.print();
       
       
        string = "Un orso vive in questa grotta";    Lang = "IT";
        es= interf.procesSentence(Lang, string);
        es.print();
        
        string = "Σε αυτή τη σπηλιά ζει μια αρκούδα";    Lang = "EL";
        es= interf.procesSentence(Lang, string);
        es.print();
        
       
        string = "En esta cueva vive un oso";    Lang = "ES";
        es= interf.procesSentence(Lang, string);
        es.print();
        
        
        string = "In this cave lives a bear";    Lang = "EN";
        es= interf.procesSentence(Lang, string);
        es.print();
        
        
        
         */
       
        
        
        
                
                
    }
    
     
    
}
