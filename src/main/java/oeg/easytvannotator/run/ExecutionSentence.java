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
<<<<<<< HEAD
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.nlp.IxaInterface;
import oeg.easytvannotator.nlp.StanfordInterface;
import oeg.easytvannotator.nlp.TreeTaggerInterface;
=======
>>>>>>> 3bd457b194fd6c3c99bde7ad8689725e64c28a8d
import oeg.easytvannotator.model.ESentence;
import oeg.easytvannotator.model.EToken;
import oeg.easytvannotator.model.EasyTVInterface;

/**
 *
 * @author pcalleja
 */
public class ExecutionSentence {
    
   

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        //String InputFile = "Ο δάσκαλος εξήγησε το μάθημα στην τάξη";   String Lang = "EL";

<<<<<<< HEAD
        

        //String string = "he visto esta película tres veces";   String Lang = "ES";
        String string = "Vi una película subtitulada anoche";   String Lang = "ES";
       // String string = "Ho visto questo film tre volte";   String Lang = "IT";
=======
        String string = "he visto esta película tres veces";   String Lang = "ES";
        //String string = "Ho visto questo film tre volte";   String Lang = "IT";
>>>>>>> 3bd457b194fd6c3c99bde7ad8689725e64c28a8d
        //String InputFile = "The teacher explained the lesson";
        //String Lang = "EN";
        
        EasyTVInterface.processLine("",Lang, string);
        EasyTVInterface.printSentences();
        
        

    }

   

   

    


   

}
