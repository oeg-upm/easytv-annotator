/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.run;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;
import oeg.easytvannotator.model.EasyTVInterface;

/**
 *
 * @author pcalleja
 */
public class ExecutionInputFile {
    
    
    
    public static List <ESentence> sentences=new ArrayList();
    
    
    
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        String InputFile = "InputFile.csv";

        
        EasyTVInterface inter= new EasyTVInterface("");
        
        BufferedReader br = null;
        File fr = new File(InputFile);

        br = new BufferedReader(new InputStreamReader(new FileInputStream(fr), "UTF8"));

        String Line;
        
        
        while ((Line = br.readLine()) != null) {

           String[] Input = Line.split(",");
           ESentence sent= inter.processLine(Input[0], Input[1]);
        }

        br.close();

        createCSV();

    }

   

    
    
   

    
    public static void createCSV(){
    
            File file = null;
        try {

            file = new File("OutputFile.csv");

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF8")); //UTF8
            String header="\"Original Sentence\";\"Stanford+Babelnet\";\"Stanford+LemmatizedBabelnet\";\"Babelfly\";\"LemmatizedBabelfly\"";
            out.append(header).append("\r\n");
            for (ESentence sentence : sentences) {

                for (EToken token : sentence.ListTokens) {

                    String init = "\"" + sentence.OriginalText + "\"";
                    String s = token.createCSVLine();
                    out.append(init + s).append("\r\n");

                }

            }

            out.flush();
            out.close();

        } catch (IOException e) {
            //logger.error(e);
            e.printStackTrace();
        }

        
        
        
    
    
    }

}
