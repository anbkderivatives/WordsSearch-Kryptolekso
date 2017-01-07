
package kryptolekso;

import java.util.ArrayList;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import javax.swing.JOptionPane;

/*
Klasi i opoia diaxeirizetai tin sullogi corpus, 
sto paradeigma mas to corpus einai to arxeio Words.txt, opou periexontai oi lekseis.
*/
public class ControlCorpusList implements FileCorpus {
    
    //lista pou periexei ws dedomena tis lekseis kai tis perigrafes tous
    private ArrayList listOfWords;//the List
    
    
    //constructor
    public ControlCorpusList(){
    listOfWords = new ArrayList();
    }
    
    //Methodos pou prosthetei stin lista listOfWords ta dedomena pou antlei apo to corpus (sullogi sto .txt arxeio)
    public void AddCorpusToList(String path){
        
        try
        {
            //Antloume to path pou douleuei to project
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            //Entopismos arxeiou
            //FileInputStream file = new FileInputStream(currentPath+"\\src\\kryptolekso\\Words.txt");//default
            FileInputStream file = new FileInputStream(path);
            //Xrisimopoieitai InputStreamReader gia encoding wste na anagnwristoun ellinikoi xaraktires (Windows-1253)
            InputStreamReader inStr = new InputStreamReader(file,"Windows-1253");
            //Prosbasi sto arxeio (.txt) gia diabasma 
            BufferedReader in = new BufferedReader( inStr );
            
            //diabazei oli tin trexousa grammi tou arxeiou .txt mexri na entopistei xaraktiras "\n" (enter)
            //ki kataxwrisi stin temp metabliti str
            String str=in.readLine();
            listOfWords.add(str.toUpperCase());
            
            
            if(str == null)//se periptwsi pou to corpus einai teleiw adeios xwris kamia kataxwrisi
            {
                listOfWords.remove(0);
            }
            
            while ((str = in.readLine()) != null) {
                //System.out.println(str);
                listOfWords.add(str.toUpperCase());
            }
            file.close();
            inStr.close();
            in.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Το αρχείο .txt, δεν βρέθηκε.", "error message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //Methodos prosthesis stoixeiwn se mia sigkekrimeni thesi stin listOfWords
    public void AddElementToList(int position, String p1){
        listOfWords.add(position,p1);
    }
    
    //Methodos afairesis stoixeiwn apo mia sigkekrimeni thesi stin listOfWords 
    public void RemoveElementFromList(int position){
        listOfWords.remove(position);
    }
    
    //Methodos epistrofis tis listas me to corpus .txt
    public ArrayList GetList(){
        return listOfWords;
    }
    //Anakateuei tin listOfWords xrisimopoiwntas tin sunartisi random
    public void ShuffleList(){
        Collections.shuffle(listOfWords);
    }
    
    public void PrintList(){
        System.out.println("\n============================================");
        System.out.println("=== Tupwsi stoixeiwn Listas: listOfWords ===\n");
        int i;
        for(i=0;i<listOfWords.size();i++){
            System.out.println(listOfWords.get(i));
        }
        System.out.println("\n====== Telos Tupwsis =======");
        System.out.println("============================");
    }    
}