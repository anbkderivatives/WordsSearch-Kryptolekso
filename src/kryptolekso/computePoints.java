
package kryptolekso;

//H klasi auti klironomei tin wordsManage, opote prepei na ulopoihsei opoiadhpote eklplirwsi 
//pou exei kanei i wordsManage me tin WordsReference
public class computePoints extends wordsManage {
    
    //Oi antistoixoi pontoi tou ellinikou alfabitou
    //new int[24];
    public int[] pointsOfChar = {1,8,4,4,1,8,1,8,1,2,3,3,1,10,1,2,2,1,1,2,8,10,10,3};
    
    //epistrefei tous pontous tou antistoixou xaraktira
    public int pointsOfChar(char ch)
    {   
        int i;
        int find_i=-1;
        for( i=0; i < this.tableChar.length; i++)
            if( this.tableChar[i].equals(ch) )
            {
                find_i = i;
                break;
            }
        
        if(find_i== -1) return 0; //periptwsi pou den brethei kapoio gramma p.x '?'
        return this.pointsOfChar[find_i];
    }
    
    //epistrefei tous pontous mias leksis
    public int getPointsWord(String word)
    {
        int SumPoints=0;
        int i;
        for( i=0; i<word.length(); i++)
            SumPoints += pointsOfChar(word.charAt(i));
        
        return SumPoints;
    }
}
