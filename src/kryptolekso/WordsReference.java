
package kryptolekso;


public interface WordsReference {
    
    //briskei tis lekseis pou tairiazoun sto megethos tou tablou, ta upoloipomena prostithontai tuxaia
    public abstract Object[][] findFittedWordsOnPannel(int tableLetters);
    //Epistrefei true i false gia to an mia leksi word uparxei stin lista me tis lekseis
    public abstract boolean wordExists(String word);
    //epistrefei tous pontous mias leksis
    public abstract int getPointsWord(String word);
}
