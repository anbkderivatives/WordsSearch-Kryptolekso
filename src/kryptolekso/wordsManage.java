
package kryptolekso;
import java.util.ArrayList;

//H kalsi dilwnetai ws abstract dioti den ulopoihei oles tis methodous tou interface WordsReference.
//Anagkastika tha prepei na ylopoihthoun apo tis upoklseis ths wordsManage.
public abstract class wordsManage implements WordsReference {
    
    public ArrayList listOfWords;
    
    //to elliniko alfabito
    //new Object[24];
    public Object[] tableChar = {'Á','Â','Ã','Ä','Å','Æ','Ç','È','É','Ê','Ë','Ì','Í','Î','Ï','Ð','Ñ','Ó','Ô','Õ','Ö','×','Ø','Ù'};
    
    public wordsManage(){}
    //constuctor
    public wordsManage(String path)
    {
        ControlCorpusList wordsCorpus = new ControlCorpusList();
        wordsCorpus.AddCorpusToList(path);
        wordsCorpus.ShuffleList();
        listOfWords = wordsCorpus.GetList();
    }
    
    //briskei tis lekseis pou tairiazoun sto megethos tou tablou,
    //oi upoloipomenes kenes theseis sumplirwnontai tuxaia apo to ellinko alfabito
    public Object[][] findFittedWordsOnPannel(int tableLetters)
    {
        int sumLetters=0;
        int n = (int) Math.sqrt(tableLetters);
        Object[][] tableboard = new Object[n][n];
        String tempWord="";
        
        int i,t;
        int k=0,l=0;//deiktes tou pinaka tableboard
        for(i=0;i<listOfWords.size();i++)
        {
            tempWord = listOfWords.get(i).toString();
            
            if( (sumLetters+tempWord.length()) <= tableLetters )
            {
                sumLetters += tempWord.length();
                //Gemizoume to tamplo me tous xaraktires apo tis lekseis pou epilegontai
                for(t=0;t<tempWord.length();t++)
                {
                    //System.out.println(k+" "+l);
                    tableboard[k][l]= tempWord.charAt(t);
                    //auksanontai antistoixa oi doiktes wste na prosdiorisoun tin thesi pou prepei na gemistei
                    l++;
                    if(l>n-1) {l=0;k++;}
                    //den ekteleitai pote auti i entoli dioti elegxetai pio prin to plithos twn grammatwn sto tamplo
                    //opote de tha exoume uperbasi tou oriou sto tamplo
                    if(k>n-1) break;
                }
            }
        }
        
        //System.out.println("k="+k+" l="+l+ " n="+n);
        
        //prosthetwntas ta upoloipomena tuxaia grammata tou alfabitou sto tamplo mexri na oloklirwthei i sumplirwsi tou
        if(k<=n-1)
        {
            //Gia na sumplirwsoume to tamplo me tuxaia grammata, dialegoume xaraktires apo tin alpfabito
            //To diastima einai 1 ews 24 opou antistoixei ston synoliko arithmo tou ellinikou alfabitou
            int range = (23 - 0) + 1; //range = (max - min) + 1; 
            //(int)(Math.random() * range) + min;
            
            while(k<=n-1)
            {
                tableboard[k][l]= tableChar[(int)(Math.random() * range)];
                l++;
                if(l>n-1) {l=0;k++;}
                //if(k>n) break;
            }
        }
        
        return tableboard;
    }
    
    //Epistrefei true i false gia to an mia leksi word uparxei stin lista me tis lekseis
    public boolean wordExists(String word)
    {
        boolean find=false;
        int i;
        for(i=0;i<listOfWords.size();i++){
            if(listOfWords.get(i).toString().compareTo(word) == 0)
            {
                find=true;
                break;
            }
        }
        
        if(find == true) return true;
        else return false;
    }
    
    public Object getRandomCharacter ()
    {
        int range=24;
        return tableChar[(int)(Math.random() * range)];
    }
}
