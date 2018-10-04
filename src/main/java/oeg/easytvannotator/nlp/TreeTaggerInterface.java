/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.nlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import oeg.easytvannotator.model.ESentence;
import org.apache.commons.lang.SystemUtils;

/**
 *
 * @author pcalleja
 */
public class TreeTaggerInterface implements NLPApi{
    
    
    File TreeTaggerDir;
    
    String Path;
    
    
    public TreeTaggerInterface(String path){
        Path=path;
    
    }
    
    public void process(String Path,String Text){
    
         TreeTaggerDir= new File(Path+"resources/TreeTagger");
         
        try {
            createTreeTaggerInput( Text );
            
            
            //SystemUtils.IS_OS_LINUX  SystemUtils.IS_OS_WINDOWS
            if(SystemUtils.IS_OS_WINDOWS){
                System.out.println("sfsdf");
            sendCommandWindows() ;
            }else{
            
            }
            
            
            readOutput(Text,"el");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void createTreeTaggerInput( String Text ) throws UnsupportedEncodingException, FileNotFoundException, IOException{
    
        
        File file = new File(TreeTaggerDir.getAbsolutePath()+"/result/Input.txt");
        Writer Writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));
         Writer.append(Text);
         Writer.flush();
         Writer.close();
         
         
    }
    
    
    public void sendCommandWindows() throws IOException, InterruptedException{
      
        String bat= TreeTaggerDir.getAbsolutePath()+"/bin/tag-greek.bat";
        String Input= TreeTaggerDir.getAbsolutePath()+"/result/Input.txt";
        String Output=  TreeTaggerDir.getAbsolutePath()+"/result/Output.txt";
        
        System.out.println("cmd /c   \""+bat+"\" "+Input+" "+Output);
        Process pro = Runtime.getRuntime().exec("cmd /c   \""+bat+"\" "+Input+" "+Output);

       
        BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        String line;
        while ((line = in.readLine()) != null) {
          
            System.out.println(line);
        }
        pro.waitFor();
        //System.out.println("ok!");

        in.close();


    
    }
    
     
  
   
    public ESentence  readOutput(String Text,String Lang) throws UnsupportedEncodingException, FileNotFoundException, IOException{
    
    
       File file = new File(TreeTaggerDir.getAbsolutePath() + "/result/Output.txt");
        
        BufferedReader Buffer = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF8"));
    
        ESentence esentence=new ESentence();
        StringBuffer LemmaSentence=new StringBuffer();
        
        String line;
        while ((line = Buffer.readLine()) != null) {
          
            String [] fields= line.split("\t");
            String word=fields[0];
            String pos=fields[1];
            String lem=fields[2];
            System.out.println(word +"  "+pos+"  "+lem+"  ");
        
          
            esentence.addEToken(word, pos, lem, "",Lang);
            LemmaSentence.append(lem+" ");
            System.out.println(line);
            
        }
        esentence.OriginalText=Text;
        esentence.LematizedText= LemmaSentence.toString().trim();
        Buffer.close();
        
        return esentence;

    }
    
    

    @Override
    public ESentence parseSentence(String Sentence, String Language) {
        
        
         TreeTaggerDir= new File(Path+"resources/TreeTagger");
         
        try {
            createTreeTaggerInput( Sentence );
            
            
            //SystemUtils.IS_OS_LINUX  SystemUtils.IS_OS_WINDOWS
            if(SystemUtils.IS_OS_WINDOWS){
                System.out.println("Windows");
                sendCommandWindows() ;
            }else{
            
            }
            
            
            return readOutput(Sentence,Language);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TreeTaggerInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
        return new ESentence();
        
    }
    
       

    
}
