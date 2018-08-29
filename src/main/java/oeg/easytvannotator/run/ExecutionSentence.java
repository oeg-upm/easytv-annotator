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
import oeg.easytvannotator.babelnet.BabelNetInterface;
import oeg.easytvannotator.nlpinterfaces.IxaInterface;
import oeg.easytvannotator.nlpinterfaces.StanfordInterface;
import oeg.easytvannotator.nlpinterfaces.TreeTaggerInterface;
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

       // String string = "he visto esta película tres veces";   String Lang = "ES";
        String string = "Ho visto questo film tre volte";   String Lang = "IT";
        //String InputFile = "The teacher explained the lesson";
        //String Lang = "EN";
        
        EasyTVInterface.processLine("",Lang, string);
        print(EasyTVInterface.sentences);

    }

   

   

    public static void print(List<ESentence> sentences) {

        for (ESentence sentence : sentences) {

            System.out.println("Sentence: " + sentence.OriginalText);

            for (EToken token : sentence.ListTokens) {

                String s = token.printResultsBabel();

            }

        }

    }


   

}