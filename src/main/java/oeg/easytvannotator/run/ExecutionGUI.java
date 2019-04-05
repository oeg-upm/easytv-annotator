/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.run;

import java.util.Scanner;
import oeg.easytvannotator.demos.BabelNetDemo;
import oeg.easytvannotator.demos.BabelfyDemo;

/**
 *
 * @author pcalleja
 */
public class ExecutionGUI {
    
    
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        
        
        String InputString="";
        String Language="";
        boolean cont=true;
        

        //scanner.nextLine(); //This is needed to pick up the new line
        while (cont) {

            System.out.println("WELCOME TO EASYTV-BABEL TESTER. Lets try some sentences");
            System.out.println("> write something like: 'EN: Hello World'");
            System.out.println("> first specify the lang as 'LANG:' . Default language is English");
            System.out.println("> write 'exit' to exit :) ");
            System.out.println("\n");
            System.out.println("Enter String:");
            InputString = scanner.nextLine();

            
            
            if(InputString.equals("exit")){break;}
            
            String [] Input = InputString.split("\\:");
            
            if (Input.length==2){
                InputString= Input[1];
                Language=Input[0];
            } else{
                //InputString= Input[0];
                Language="EN";
            }
            
             InputString= InputString.trim();
             System.out.println("Recieved: " + InputString);
             System.out.println("Lang:"+Language);
            
            
            /// STANFORD + BABELNET
            BabelNetDemo.callBabelNetWithStanfordParser(InputString, Language);
            
            // BABELFLY
            BabelfyDemo.example(InputString, Language);
            
            
            System.out.println("\n\n");

        }

    }
    
    
}
