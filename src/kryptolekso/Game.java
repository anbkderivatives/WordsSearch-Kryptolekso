
package kryptolekso;

public interface Game {
    //oloi oi methodoi ennountai oti einai public abstract kai oi metablites public statick kai final
    
    
    
    //arxikopoisi tamplo
    void Initialize();
    //Elegxos geitniasis sto pannel
    //boolean validSelectedChar();
    //Otan patame to koumpi "check word"
    boolean checkWordExistance(String word);
    //Ypologismos pontwn paixti
    int stringPoints(String currentFindedWord);
}
