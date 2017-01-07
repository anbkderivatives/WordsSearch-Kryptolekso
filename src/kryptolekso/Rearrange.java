
package kryptolekso;
import java.util.ArrayList;
import java.util.Collections;

//Klasi anadiataksis pinaka
public class Rearrange implements BoardModify {
    
    ArrayList<Object> tempList = new ArrayList<Object>();
    //parametroi o pinakas pou tha anadiataxtei, 
    //f1,f2 oi deiktes ston pinaka pou deixnoun to prwto (first) gramma (letter)
    //s1,s2 oi deiktes ston pinaka pou deixnoun to deutero (second) gramma (letter)
    public Object[][] switchTwoLetters(Object[][] table,int f1,int f2,int s1,int s2)
    {
        Object temp = table[f1][f2];
        table[f1][f2] = table[s1][s2];
        table[s1][s2] = temp;
        return table;
    }
    
    //diagrafei tin epilogi grammis, kai sumplirwnei me tyxaia nea grammata tin idia grammi
    //to antikeimeno wManage epilegetai gia na exoume prosbasi sto pinaka ellinikou alfabitou
    public Object[][] newRowLetters(Object[][] table,int row, int n, wordsManage wManage)
    {
        int range = 24;
        int i;
        for(i=0;i<n;i++)
            table[row][i]= wManage.tableChar[(int)(Math.random() * range) ];
        return table;
    }
    
    //diagrafei tin epilogi stilis, kai sumplirwnei me tyxaia nea grammata tin idia stili
    //to antikeimeno wManage epilegetai gia na exoume prosbasi sto pinaka ellinikou alfabitou
    public Object[][] newColumnLetters(Object[][] table,int col, int n, wordsManage wManage)
    {
        int range = (23 - 0) + 1;
        int i;
        for(i=0;i<n;i++)
            table[i][col]= wManage.tableChar[(int)(Math.random() * range)];
        return table;
    }
    
    //Anadiataksi grammatwn tou pinaka/tamplo
    public Object[][] rearrangeLetters(Object[][] table,int n)
    {
        tempList.clear();//adeia lista, diagrafi olwn twn stoixeiwn
        
        int i,j;
        for(i=0;i<n;i++)
            for(j=0;j<n;j++)
                tempList.add(table[i][j]);
        
        Collections.shuffle(tempList);
        //System.out.println(tempList.size());
        for(i=0;i<n;i++)
           for(j=0;j<n;j++)
           {
               table[i][j] = tempList.get(0);
               tempList.remove(0);
           }
        //System.out.println(tempList.size()); //=> panta epistrefetai 0
        return table;
    }
    
    //Anadiataksi grammatwn epilegmenis grammis ston pinaka/tamplo
    public Object[][] rearrangeRow(Object[][] table, int row, int n)
    {
        tempList.clear();//adeia lista, diagrafi olwn twn stoixeiwn
        
        int i;
        for(i=0;i<n;i++)
            tempList.add(table[row][i]);
        
        Collections.shuffle(tempList);
        
        for(i=0;i<n;i++)
        {
            table[row][i] = tempList.get(0);
            tempList.remove(0);
        }
        
        return table;
    }
    
    //Anadiataksi grammatwn epilegmenis stilis ston pinaka/tamplo
    public Object[][] rearrangeCol(Object[][] table, int col, int n)
    {
        tempList.clear();//adeia lista, diagrafi olwn twn stoixeiwn
        
        int i;
        for(i=0;i<n;i++)
            tempList.add(table[i][col]);
        
        Collections.shuffle(tempList);
        
        for(i=0;i<n;i++)
        {
            table[i][col] = tempList.get(0);
            tempList.remove(0);
        }
        
        return table;
    }
}
