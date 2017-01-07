
package kryptolekso;

import java.util.ArrayList;

/**
 *
 * @author Á
 */
public interface FileCorpus {
    public static String fileName="";
    
    public abstract void AddCorpusToList(String path);
    public abstract void AddElementToList(int position, String p1);
    public abstract void RemoveElementFromList(int position);
    public abstract ArrayList GetList();
}
