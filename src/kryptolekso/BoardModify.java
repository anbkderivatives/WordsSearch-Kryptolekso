
package kryptolekso;


public interface BoardModify {
    
    Object[][] switchTwoLetters(Object[][] table,int f1,int f2,int s1,int s2);
    Object[][] newRowLetters(Object[][] table,int row, int n, wordsManage wManage);
    Object[][] newColumnLetters(Object[][] table,int col, int n, wordsManage wManage);
    Object[][] rearrangeLetters(Object[][] table,int n);
    Object[][] rearrangeRow(Object[][] table, int row, int n);
    Object[][] rearrangeCol(Object[][] table, int col, int n);
}
