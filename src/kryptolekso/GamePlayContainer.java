package kryptolekso;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import java.nio.file.Paths;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GamePlayContainer implements Game{
    
    String playerName;
    int tableLetters; //shmainei nxn,opou n=5,8 k 10 (eidos paixnidiou)
    int n;
    String wordsDirectory;//to path tou .txt me tis lekseis
    int playerPoints;
    int wordPoint;
    
    Object[][] tableBoard; //tamplo
    
    //antikeimena klasewn pou xreiazontai kai ekteloun apomonomenes sugkekrimenes leitourgies tou paixnidiou
    wordsManage wManage;
    computePoints cp;
    Rearrange rearrangeTable;
    
                //Sustatika Grafikwn
    JFrame frame;
    Container pane;
        
    JMenuBar menuBar;
    JMenu menu1, menu2;
    JMenuItem menu1Item1, menu1Item2,menu1Item3,menu1Item4,menu1Item5;
    JMenuItem menu2Item1,menu2Item2;
    
    //JFrame parathuro gia tis ruthmiseis kai boitheias
    Settings settings;
    Help help;
    
            //Components eisagwgis stoixeiwn paixnidiou kai paixti
    JPanel panel1,panel2,panel3,panel4;
    JTextField textfld1,textfld2;
    JButton choosefile,creatBtn;
    ButtonGroup group; // gia omadopoihsi JRadioButtons
    JFileChooser fc;
    
            //Components pou periexoun to grafiko periballon tou paixnidiou
    JPanel panel5,panel6;
    JPanel panel7,panel8;
    JTextPane[][] jTextPane;  
    //Components boitheiwn
    JButton CheckWordBtn,deleteRowBtn,deleteColBtn,rearrangeRowBtn,rearrangeColBtn,rearrangeBoardBtn,switchTwoLettersBtn;
    JTextField deleteRowTextField,rearrangeRowTextField,deleteColTextField,rearrangeColTextField;
    int numberSlectedfromTextFields;
    
            //JLabels Boitheiwn kai Enimerwsewn
    JLabel deleteRowJL,deleteColJL,rearrangeRowJL,rearrangeColJL,rearrangeBoardJL,switchTwoLettersJL;
    //counters
    int cntdeleteRowJL,cntrearrangeRowJL,cntdeleteColJL,cntrearrangeColJL,cntrearrangeBoardJL,cntswitchTwoLettersJL;
    int maxdeleteRowJL,maxrearrangeRowJL,maxdeleteColJL,maxrearrangeColJL,maxrearrangeBoardJL,maxswitchTwoLettersJL;
    
    JLabel inf1,inf2,inf3,inf4,inf5,inf6; //enimerwsi/emfanisi pros ton xristi tous counters twn boitheiwn
    JLabel updatedInf;//enimerwnei ton xristi gia tis energeies tou
    JLabel achievePoints,sumPoints,wordPoints,finedWords;
    int cntfinedWords=0;
    int stoxos=200;
        
    
                    //Handlers    
    MenuHandler handler;//xeiristis kounmpiwn kai menu
    BoardHandle boardHandle;//xeiristis kounmpiwn tamplou me to pontiki
    //lista me ta epilegmena antikeimena sto tamplo apo to pontiki
    ArrayList<MouseEvent> selectedJLetters;
    ArrayList<String> selected2JLettersForExchange;
    //listes pou periexoun tis theseis i,j ston tamplo gia ta backgrounds xrwmata 
    ArrayList<String> RedBackGrounds = new ArrayList<String>();//px {"01","42","67"}
    ArrayList<String> BlueBackGrounds = new ArrayList<String>();
    
    //Metablites gia tin ruthmisi twn diaforetikwn eidwn grammatwn
    int rangePosibility = 10;//euros gia na sximatisoume pithanotita
    int MaxRedBackgrounds = 4;
    int cntRedBackgrounds=0; //counter
    int MaxBlueBackgrounds = 3;
    int cntBlueBackgrounds=0;
    int MaxBalanters = 2;
    int cntBalanters=0;
    
                    //Peraiterw bohthitikes metablites
    String position,previousPosition,selectedLetters;
    char temp;
    int iIndex,jIndex;//deiktes trexwn epilegmenwn components/antikeimenwn tou tamplou
    int previous_iIndex,previous_jIndex;
    Object[] optionsLetters = {'Α','Β','Γ','Δ','Ε','Ζ','Η','Θ','Ι','Κ','Λ','Μ','Ν','Ξ','Ο','Π','Ρ','Σ','Τ','Υ','Φ','Χ','Ψ','Ω'};
    Object slct;
    boolean geitniasi=true;
    boolean findedAtLeastOneBlue=false;
    boolean balnterIsSetted = false;
    
    //abstract
    public GamePlayContainer()
    {
        frame = new JFrame("ΚΡΥΠΤΟΛΕΞΟ");
        frame.setLocation(350,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,450);
        frame.setVisible(true);
        
        //Settings settings = new Settings(this.GamePlayContainer);
        
        pane = frame.getContentPane();
        handler=new MenuHandler();
        
        menuBar = new JMenuBar();
        menu1 = new JMenu("Μενού");
        menu2 = new JMenu("Εργαλεία");
        menu1Item1 = new JMenuItem("Νέο Παιχνίδι");
        menu1Item2 = new JMenuItem("Ακύρωση/Τερματισμός παιχνιδιού");
        menu1Item3 = new JMenuItem("Ρυθμίσεις Βοηθειών");
        menu1Item5 = new JMenuItem("Αναζήτηση αρχείου λέξεων");
        menu1Item4 = new JMenuItem("Έξοδος");     
        menu2Item1 = new JMenuItem("Βοήθεια");
        menu2Item2 = new JMenuItem("About");
        
        menu1Item2.setEnabled(false);
        menu1Item3.setEnabled(false);
        menu1Item5.setEnabled(false);
        
        //prosthesi sto ActionListener koumpiwn tou menu wste na pragmatopoihthoun katalliles energeies me to patima tous
        menu1Item1.addActionListener(handler);
        menu1Item2.addActionListener(handler);
        menu1Item3.addActionListener(handler);
        menu1Item4.addActionListener(handler);
        menu2Item1.addActionListener(handler);
        menu2Item2.addActionListener(handler);
        menu1Item5.addActionListener(handler);
        
        menu1.add(menu1Item1);
        menu1.add(menu1Item2);
        menu1.add(menu1Item3);
        menu1.add(menu1Item5);
        menu1.add(menu1Item4);
        menu2.add(menu2Item1);
        menu2.add(menu2Item2);
        menuBar.add(menu1);
        menuBar.add(menu2);
        
        frame.setJMenuBar(menuBar);
        
        //Default times gia tis max times boitheiwn
        maxdeleteRowJL=maxdeleteColJL=maxrearrangeRowJL=maxrearrangeColJL=5;
        maxrearrangeBoardJL=maxswitchTwoLettersJL=10;
    }
    
    //methodos gia tin metabibasi tou trexwn antikeimenou GamePlayContainer se alla JFrames
    public void passOwnObject(GamePlayContainer g)
    {
        settings = new Settings(g);
        help = new Help();
    }
    
    //Klasi i opoia xeirizetai tin allilepidrasi tou xristi me to menu kai ta koumpia 
    //(exei sxesi mono me ta koumpia pou parousiazontai sto grafiko periballon)
    class MenuHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==menu1Item1)
            {//<editor-fold>
                String[] options = {"Ναι","Όχι"};
                int slct = JOptionPane.showOptionDialog(null, "Είστε σίγουροι πως θέλετε δημιουγία νέου παιχνιδιού", "Confirmation", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                //System.out.println(slct);
                if(slct == 0) CreateNewGame();
            }//</editor-fold>
            if (e.getSource()==menu1Item2)
            {//<editor-fold>
                String[] options = {"Ναι","Όχι"};
                int slct = JOptionPane.showOptionDialog(null, "Είστε σίγουροι πως θέλετε να ακυρώσετε/τερματίσετε το τρέχων παιχνίδι", "Confirmation", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                //System.out.println(slct);
                if(slct == 0)
                {
                    pane.removeAll();
                    pane.repaint();
                    menu1Item2.setEnabled(false);
                    menu1Item3.setEnabled(false);
                    menu1Item5.setEnabled(false);
                }
            }//</editor-fold>
            if (e.getSource()==menu1Item3)
            {
                settings.makeVisible();
            }
            if (e.getSource()==menu1Item5)
            {//<editor-fold>
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                fc.setCurrentDirectory(new java.io.File(currentPath));
                fc.setDialogTitle("File Chooser");
                if(fc.showOpenDialog(choosefile) == JFileChooser.APPROVE_OPTION)
                {
                    System.out.println("Το αρχείο που φόρτωσε :"+fc.getSelectedFile().getAbsolutePath());
                    updatedInf.setText("Το αρχείο που επιλέξατε φόρτωσε!");
                    wManage = new wordsManage(fc.getSelectedFile().getAbsolutePath()) 
                        {//prosthetoume mia ulopoihsh tis getPointsWord dioti suntaktika den epitrepetai dimiourgia abstract antikeimenwn
                            @Override
                            public int getPointsWord(String word) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }
                        };
                }
            }//</editor-fold>
            if (e.getSource()==menu1Item4) System.exit(0);

            if (e.getSource()==menu2Item1)
            {
                help.makeVisible();
            }
            if(e.getSource()==menu2Item2)
                JOptionPane.showMessageDialog(null, "Κρυπτόλεξο: 1η έκδοση\nAuthor: CodeFvr", "About", JOptionPane.INFORMATION_MESSAGE);

            if(e.getSource()==choosefile)
            {//<editor-fold>
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                fc.setCurrentDirectory(new java.io.File(currentPath));
                fc.setDialogTitle("File Chooser");
                if(fc.showOpenDialog(choosefile) == JFileChooser.APPROVE_OPTION)
                    textfld2.setText(fc.getSelectedFile().getAbsolutePath());
            }//</editor-fold>
            if(e.getSource()==creatBtn)
            {//<editor-fold>
                playerName = textfld1.getText();
                //Epistrefetai gia tin trexousa epilegomeni JRadioButton i ActionCommand pou tethike gia kathe JRadioButton
                tableLetters = Integer.parseInt(group.getSelection().getActionCommand());
                //System.out.println(tableLetters);

                wordsDirectory = textfld2.getText();
                GamePlay();
            }//</editor-fold>
            
            if(e.getSource()==CheckWordBtn)
            {//<editor-fold>
                selectedLetters="";
                for(int i=0; i< selectedJLetters.size(); i++)
                {
                    position = selectedJLetters.get(i).getComponent().getName();
                    iIndex = Character.getNumericValue(position.charAt(0));
                    jIndex = Character.getNumericValue(position.charAt(1));
                    
                    selectedLetters =  selectedLetters + tableBoard[iIndex][jIndex];
                }
                
                //Elegxos an uparxei i leksi stin lista .txt corpus
                if(checkWordExistance(selectedLetters) && selectedJLetters.isEmpty() == false) 
                {
                    //System.out.println("Συγχαρητήρια βρήκες την λέξη: "+selectedLetters);
                    updatedInf.setText("Συγχαρητήρια βρήκατε την λέξη: "+selectedLetters);
                    
                    //Ypologismos pontwn trexoysas epituxis leksis pou brisketai apo ton xristi xwris na upologizontai oi eidikoi xaraktires
                    wordPoint = stringPoints(selectedLetters);
                    
                    //Ksetsekarontai ta kitrina backgrounds twn epilegmenwn JTextPane k adeiazoume tin lista
                    //Ypologismos pontwn prosmetrwntas tous eidikous xaraktires
                    //Ginontai allages periexomenwn me tuxaia grammata stis sugkekrimenes theseis sto tamplo
                    //Epilegontai kai ta diforetika eidh grammatwn me tuxaio tropo
                    findedAtLeastOneBlue = false;
                    for(int k=selectedJLetters.size()-1;k>=0;k--)
                    {
                        position = selectedJLetters.get(k).getComponent().getName();
                        iIndex = Character.getNumericValue(position.charAt(0));
                        jIndex = Character.getNumericValue(position.charAt(1));
                        
                        //Ypologismos pontwn prosmetrwntas eidikous xaraktires kai meiwsi antistoixwn counters
                        for(int r=0;r<RedBackGrounds.size();r++)
                            if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == iIndex &&
                                    Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == jIndex )
                            {
                                //gia kathe kokkino background prosthetoume tous antistoixous pontous sto athroisma twn pontwn tou xristi
                                wordPoint += cp.pointsOfChar((char)tableBoard[iIndex][jIndex]);
                                cntRedBackgrounds--;
                            }
                        for(int b=0;b<BlueBackGrounds.size();b++)
                            if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == iIndex &&
                                    Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == jIndex )
                            {
                                findedAtLeastOneBlue = true;
                                cntBlueBackgrounds--;
                            }
                        
                        //allagi periexomenou me tyxaio gramma stin sugkekrimeni thesi sto tamplo
                        tableBoard[iIndex][jIndex] = wManage.getRandomCharacter();
                                                
                        setBalanters(iIndex,jIndex);
                        
                        //Kataxwrisi neou styled html periexomenou pros emfanisi
                        jTextPane[iIndex][jIndex].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[iIndex][jIndex].toString()+"</span>"
                                          +"<span style=\"font-size:11\">"+ String.valueOf(
                            //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                            cp.pointsOfChar(tableBoard[iIndex][jIndex].toString().charAt(0)) 
                        )+"</body>");
                                                
                        selectedJLetters.get(k).getComponent().setBackground(Color.white);
                        
                        if(balnterIsSetted == false)
                            if( (int)(Math.random()*2 + 1) == 1 ) setRedBackGrounds(iIndex,jIndex);
                            else setBlueBackGrounds(iIndex,jIndex);
                        
                        selectedJLetters.remove(k);
                    }
                    
                    if(findedAtLeastOneBlue) wordPoint = wordPoint * 2;
                    playerPoints += wordPoint;
                    
                    sumPoints.setText( String.valueOf(playerPoints) );
                    wordPoints.setText( String.valueOf(wordPoint) );
                    cntfinedWords++;
                    finedWords.setText( String.valueOf(cntfinedWords) );
                    
                    if(cntfinedWords == 4)
                    {
                        JOptionPane.showMessageDialog(null, "Μπράβο, βρήκατε 4 λέξεις!","ενημέρωση",JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                    if(playerPoints >= stoxos)
                    {
                        Object[] option = {"Ναι","Όχι"};
                        int slct = JOptionPane.showOptionDialog(null, "Μπράβο, φθάσατε τους απαιτούμενους πόντους, θέλετε να συνεχίσετε το παιχνίδι ?","ερώτηση",
                                JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[0]);
                        if(slct == 1)
                        {
                            pane.removeAll();
                            pane.repaint();
                            menu1Item2.setEnabled(false);
                            menu1Item3.setEnabled(false);
                            menu1Item5.setEnabled(false);
                        }
                    }
                }
                else 
                    updatedInf.setText("Δυστυχώς η λέξη δεν βρέθηκε.");
                    //System.out.println("Δυστυχώς η λέξη δεν βρέθηκε.");
            }//</editor-fold>
                                    
            //Ean i pigi proerxetai apo koumpia boitheias
            //<editor-fold>
            if(e.getSource()==deleteRowBtn)
            {
                RemoveSelectedYellows();
                RemoveSelectedGrayBorders();
                Rearrangements("newRowLetters");
                
                if(cntdeleteRowJL >= maxdeleteRowJL) 
                {
                    deleteRowBtn.setEnabled(false);
                    deleteRowTextField.setText("");
                    deleteRowTextField.setEnabled(false);
                }
            }
            if(e.getSource()==rearrangeRowBtn)
            {
                RemoveSelectedYellows();
                RemoveSelectedGrayBorders();
                Rearrangements("rearrangeRow");
                
                if(cntrearrangeRowJL >= maxrearrangeRowJL) 
                {
                    rearrangeRowBtn.setEnabled(false);
                    rearrangeRowTextField.setText("");
                    rearrangeRowTextField.setEnabled(false);
                }
            }
            if(e.getSource()==deleteColBtn)
            {
                RemoveSelectedYellows();
                RemoveSelectedGrayBorders();
                Rearrangements("newColumnLetters");
                
                if(cntdeleteColJL >= maxdeleteColJL) 
                {
                    deleteColBtn.setEnabled(false);
                    deleteColTextField.setText("");
                    deleteColTextField.setEnabled(false);
                }
            }
            if(e.getSource()==rearrangeColBtn)
            {
                RemoveSelectedYellows();
                RemoveSelectedGrayBorders();
                Rearrangements("rearrangeCol");
                
                if(cntrearrangeColJL >= maxrearrangeColJL) 
                {
                    rearrangeColBtn.setEnabled(false);
                    rearrangeColTextField.setText("");
                    rearrangeColTextField.setEnabled(false);
                }
            }
            if(e.getSource()==rearrangeBoardBtn)
            {
                RemoveSelectedYellows();
                RemoveSelectedGrayBorders();
                Rearrangements("rearrangeLetters");
                
                if(cntrearrangeBoardJL >= maxrearrangeBoardJL) 
                {
                    rearrangeBoardBtn.setEnabled(false);
                }
            }
            if(e.getSource()==switchTwoLettersBtn)
            {
                RemoveSelectedYellows();
                Rearrangements("switchTwoLetters");
                
                if(cntswitchTwoLettersJL >= maxswitchTwoLettersJL) 
                {
                    switchTwoLettersBtn.setEnabled(false);
                }
            }
            //</editor-fold>
        }
    }

    //Klasi i opoia xeirizetai tin allilepidrasi tou xristi me to tamplo
    //(exei sxesi mono me tis energeies tou pontikiou gia na allaksoun to grafiko periballon)
    class BoardHandle implements MouseListener
    {        
        @Override
        public void mouseClicked(MouseEvent e) {
            //Ean patietai to aristero klik tou pontikiou kai ean i pigi tou e.getSource() apotelei(einai upodeiksh) sustatiko JTextPane (meros tou tamplou)
            if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof JTextPane) 
            {//<editor-fold> 
                
                //proetoimasia entopismou thesis i,j tou trexwn JTextPane sto tamplo
                position = e.getComponent().getName();
                iIndex = Character.getNumericValue(position.charAt(0));
                jIndex = Character.getNumericValue(position.charAt(1));
                //System.out.println(iIndex+"-"+jIndex);
                
                //Periptwsi pou einai adeia i lista selectedJLetters
                if(selectedJLetters.isEmpty()==true )
                { 
                    e.getComponent().setBackground(Color.yellow);
                    selectedJLetters.add(e);
                    
                }
                //Periptwsi pou theloume na ksetsekaroume tin teleutaia epilogi apo to tamplo
                else if(e.getSource()==selectedJLetters.get(selectedJLetters.size()-1).getSource())
                {
                    e.getComponent().setBackground(Color.white);
                    selectedJLetters.remove(selectedJLetters.size()-1);
                                        
                    //Epanafora xrwmatos stin arxiki tou emfanisi otan ksestekaretai i epilogi apo to tamplo
                    for(int r=0;r<RedBackGrounds.size();r++)
                        if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == iIndex &&
                                Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == jIndex )
                            e.getComponent().setBackground(Color.red);
                    for(int b=0;b<BlueBackGrounds.size();b++)
                        if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == iIndex &&
                                Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == jIndex )
                            e.getComponent().setBackground(Color.cyan);
                            
                }
                //Periptwsi pou apotrepetai na epilegoun idi epilegmena grammata apo to tamplo
                else if(e.getComponent().getBackground()==Color.yellow)
                {
                    JOptionPane.showMessageDialog(null,"Παρακαλούμε να μην επιλέγετε ήδη επιλεγμένα γράμματα.", "warning message",JOptionPane.WARNING_MESSAGE);
                    //System.out.println("Παρακαλούμε να μην επιλέγετε ήδη επιλεγμένα γράμματα.");
                }
                else //Periptwsi Geitiniasis
                {
                    previousPosition = selectedJLetters.get(selectedJLetters.size()-1).getComponent().getName();
                    previous_iIndex = Character.getNumericValue(previousPosition.charAt(0));
                    previous_jIndex = Character.getNumericValue(previousPosition.charAt(1));
                    
                    //System.out.println("current:"+iIndex+"-"+jIndex+" previous:"+previous_iIndex+"-"+previous_jIndex);
                    
                    //Elegxos Geitniasis
                    if((iIndex==previous_iIndex || iIndex==previous_iIndex+1 || iIndex==previous_iIndex-1) 
                            && (jIndex==previous_jIndex || jIndex==previous_jIndex+1 || jIndex==previous_jIndex-1) )
                    {
                        e.getComponent().setBackground(Color.yellow);
                        selectedJLetters.add(e);
                        geitniasi=true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Για κάθε νέα επιλογή στον ταμπλό, θα πρέπει να είναι γειτονικό με την τελευταία επιλογή.", 
                                "'Ελεγχος Γειτνίασης",JOptionPane.WARNING_MESSAGE);
                        geitniasi=false;
                    }
                }
                
                if(selectedJLetters.isEmpty()) geitniasi=true;
                //Periptwsi pou i geitniasi isxyei k to periexomeno einai mpalanter '?'
                //Tote an einai alithis i sunthiki, emfanizontai oi epiloges pou dinei to mpalanter
                if( (geitniasi==true && tableBoard[iIndex][jIndex].equals('?') )  )
                {
                    slct =
                    JOptionPane.showInputDialog(null,"Παρακαλούμε επιλέξτε ενα γράμμα της επιθυμίας σας απο τον ελληνικό αλφάβητο.", 
                            "input message",JOptionPane.INFORMATION_MESSAGE, null, optionsLetters,optionsLetters[0]);
                    tableBoard[iIndex][jIndex] = slct;
                    
                    if(slct == null) tableBoard[iIndex][jIndex] = '?';//an patithei koumpi cancel k den epilextei kapoia epilogi
                    else cntBalanters--;//periptwsi pou epilegetai gramma apo mpalanter k prepei na meiwthei o counter twn Balanters
                    
                    //Kataxwrisi styled html periexomenou pros emfanisi
                    jTextPane[iIndex][jIndex].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[iIndex][jIndex].toString()+"</span>"
                                          +"<span style=\"font-size:11\">"+ String.valueOf(
                        //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                        cp.pointsOfChar(tableBoard[iIndex][jIndex].toString().charAt(0)) 
                    )+"</body>");
                }
                
            }//</editor-fold>
            //System.out.println(e.getSource() );
            
            //Xeirismos diplou click
            if (e.getClickCount() == 2 && !e.isConsumed() && e.getSource() instanceof JTextPane) 
            {//<editor-fold>
                e.consume();
                
                if(selected2JLettersForExchange.size()<2)
                {
                    //proetoimasia entopismou thesis i,j tou trexwn JTextPane sto tamplo
                    String positionDoubleClick = e.getComponent().getName();
                    int iIndexDoubleClick = Character.getNumericValue(positionDoubleClick.charAt(0));
                    int jIndexDoubleClick = Character.getNumericValue(positionDoubleClick.charAt(1));
                    
                    selected2JLettersForExchange.add(iIndexDoubleClick+""+jIndexDoubleClick);
                    jTextPane[iIndexDoubleClick][jIndexDoubleClick].setBorder(BorderFactory.createLineBorder(Color.darkGray,4));
                }
            }//</editor-fold>
        } 

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {//if right Button is Pressed
                //System.out.println("Right Button Pressed");
                //ksetsekontai ta kitrina backgrounds twn epilegmenwn JTextPane k adeiazoume tin lista
                for(int i=selectedJLetters.size()-1;i>=0;i--)
                {
                    selectedJLetters.get(i).getComponent().setBackground(Color.white);
                    
                    position = selectedJLetters.get(i).getComponent().getName();
                    iIndex = Character.getNumericValue(position.charAt(0));
                    jIndex = Character.getNumericValue(position.charAt(1));
                                                                              
                    //Epanafora xrwmatos stin arxiki tou emfanisi otan ksestekaretai i epilogi apo to tamplo
                    for(int r=0;r<RedBackGrounds.size();r++)
                        if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == iIndex &&
                                Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == jIndex )
                            selectedJLetters.get(i).getComponent().setBackground(Color.red);
                    for(int b=0;b<BlueBackGrounds.size();b++)
                        if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == iIndex &&
                                Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == jIndex )
                            selectedJLetters.get(i).getComponent().setBackground(Color.cyan);
                    
                    selectedJLetters.remove(i);
                }
                
                for(int i=selected2JLettersForExchange.size()-1;i>=0;i--)
                {
                    int ivalue = Character.getNumericValue(selected2JLettersForExchange.get(i).charAt(0));
                    int jvalue = Character.getNumericValue(selected2JLettersForExchange.get(i).charAt(1));
                    
                    jTextPane[ivalue][jvalue].setBorder(BorderFactory.createLineBorder(Color.black));
                    selected2JLettersForExchange.remove(i);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
    //panels gia eidagwgi stoixeiwn paixnidiou k paixti
    public void CreateNewGame()
    {
        //stin periptwsi pou exoume hdh alla panel/components pou periexontai sto pane
        pane.removeAll();
        
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        textfld1 = new JTextField(15);
        
        textfld2 = new JTextField(30);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        textfld2.setText(currentPath+"\\src\\kryptolekso\\Words.txt"); //Default directory
        textfld2.setEditable(false);
        
        choosefile = new JButton("...");
        creatBtn = new JButton("Δημιουργία Παιχνιδιού");
        
        //JRadioButtons gia to eidos paixnidiou
        JRadioButton option1 = new JRadioButton("5 x 5");
        JRadioButton option2 = new JRadioButton("8 x 8");
        JRadioButton option3 = new JRadioButton("10 x 10");
        //Dimiourgia epistrefomenwn timwn se periptwsi pou epilegetai i kathe JRadioButton
        option1.setActionCommand("25");
        option2.setActionCommand("64");
        option3.setActionCommand("100");
        //Omadopoihsi wste na einai dunati mias mono epilogis
        group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        option1.setSelected(true);
        
        pane.setLayout(new GridLayout(4,1));
        
        panel1.setLayout(new FlowLayout());
        panel1.add(new JLabel("Όνομα παίχτη : "));
        panel1.add(textfld1);        
        pane.add(panel1);        
        
        panel2.setLayout(new FlowLayout());
        panel2.add(new JLabel("Τύπος παιχνιδιού : "));
        panel2.add(option1);
        panel2.add(option2);
        panel2.add(option3);
        pane.add(panel2);
        
        panel3.setLayout(new FlowLayout());
        panel3.add(new JLabel("Επιλογή Αρχείου .txt με τις λέξεις : "));
        panel3.add(textfld2);
        panel3.add(choosefile);
        pane.add(panel3);
        
        panel4.setLayout(new FlowLayout());
        panel4.add(creatBtn);
        pane.add(panel4);
        
        //prosthesi sto ActionListener
        choosefile.addActionListener(handler);
        creatBtn.addActionListener(handler);
                
        frame.setContentPane(pane);
        frame.pack();
        
    }
 
    public void GamePlay()
    {
        pane.removeAll();
        menu1Item2.setEnabled(true);
        menu1Item3.setEnabled(true);
        menu1Item5.setEnabled(true);
        panel5 = new JPanel();
        panel6 = new JPanel();        
        
        selectedJLetters = new ArrayList<MouseEvent>();
        selected2JLettersForExchange = new ArrayList<String>();
        Initialize();
        
        jTextPane = new JTextPane[n][n];
        boardHandle = new BoardHandle();
        
        String positionArray=null;//metablitis gia na onomasoume ta jTextPane antikeimena
        
        
        
        //Dimoiourgia antikeimenou GridBagConstraints opou bohtha na stoixizei sugkekrimena ta JPanels sto Container pane sumfwna me GridBagLayout stoixisi
        GridBagConstraints gbcPane = new GridBagConstraints();
        pane.setLayout(new GridBagLayout());
        //prosthetoume tis energeis click tou pontikiou se olo ton Container pane (dil. opou klikaretai sto grafiko periballon diegeiretai to MouseListener)
        pane.addMouseListener(boardHandle);
        
        panel5.setLayout(new GridLayout(n,n,6,6));
        panel5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        for(int i=0; i<n;i++)
            for(int j=0;j<n;j++)
            {
                jTextPane[i][j] = new JTextPane();
                jTextPane[i][j].setEditable(false);
                jTextPane[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                jTextPane[i][j].setPreferredSize(new Dimension(40, 40));
                
                //periexomeno sto jTextPane na einai tupou html
                jTextPane[i][j].setContentType("text/html");
                
                setBalanters(i,j);
                
                //Kataxwrisi styled html periexomenou pros emfanisi
                jTextPane[i][j].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[i][j].toString()+"</span>"
                                          +"<span style=\"font-size:11\">"+ String.valueOf(
                        //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                        cp.pointsOfChar(tableBoard[i][j].toString().charAt(0)) 
                    )+"</body>");
                
                if(balnterIsSetted == false)
                {
                    if( (int)(Math.random()*2 + 1) == 1 ) setRedBackGrounds(i,j);
                    else setBlueBackGrounds(i,j);
                }
                
                panel5.add(jTextPane[i][j]);
                
                //prosthesi sto MouseListener
                jTextPane[i][j].addMouseListener(boardHandle);
                //Dimiourgia onomatos gia tin kathe jTextPane [i][j] wste na apofeuxthoun tyxon epanaleipseis gia na brethei i thesi ston grafiko pinaka(tamplo)
                positionArray = String.valueOf(i)+String.valueOf(j);
                jTextPane[i][j].setName(positionArray);
            }
        
        CheckWordBtn = new JButton("Έλεγχος Λέξης");
        panel6.setLayout(new FlowLayout()); 
        panel6.add(CheckWordBtn);
        
        
        gbcPane.gridx = 0;//Column posittion
        gbcPane.gridy = 0;//Row posittion
        pane.add(panel5,gbcPane);
        gbcPane.gridx = 0;
        gbcPane.gridy = 1;
        pane.add(panel6,gbcPane);
        
        panel7 = new JPanel();
        panel8 = new JPanel();
        
        //Dimiourgia koumpiwn k textfields metis protimomenes diastaseis
        deleteRowBtn = new JButton("Διαγραφή Γραμμής");
        deleteRowBtn.setPreferredSize(new Dimension(160, 25));
        rearrangeRowBtn = new JButton("Αναδιάταξη Γραμμής");
        rearrangeRowBtn.setPreferredSize(new Dimension(160, 25));        
        deleteColBtn = new JButton("Διαγραφή Στήλης");
        deleteColBtn.setPreferredSize(new Dimension(160, 25));        
        rearrangeColBtn = new JButton("Αναδιάταξη Στήλης");
        rearrangeColBtn.setPreferredSize(new Dimension(160, 25));
        rearrangeBoardBtn = new JButton("Αναδιάταξη Ταμπλό");
        rearrangeBoardBtn.setPreferredSize(new Dimension(160, 25));
        switchTwoLettersBtn = new JButton("Εναλλαγή Γραμμάτων");
        switchTwoLettersBtn.setPreferredSize(new Dimension(160, 25));
        deleteRowTextField = new JTextField(1);
        deleteColTextField = new JTextField(1);
        rearrangeRowTextField = new JTextField(1);
        rearrangeColTextField = new JTextField(1);
                
        inf1 = new JLabel(cntdeleteRowJL+"/"+maxdeleteRowJL);
        inf2 = new JLabel(cntrearrangeRowJL+"/"+maxrearrangeRowJL);
        inf3 = new JLabel(cntdeleteColJL+"/"+maxdeleteColJL);
        inf4 = new JLabel(cntrearrangeColJL+"/"+maxrearrangeColJL);
        inf5 = new JLabel(cntrearrangeBoardJL+"/"+maxrearrangeBoardJL);
        inf6 = new JLabel(cntswitchTwoLettersJL+"/"+maxswitchTwoLettersJL);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);//padding
        panel7.setLayout(new GridBagLayout());
                
        gbc.gridx = 2;//Column posittion
        gbc.gridy = 0;//Row position
        panel7.add(new JLabel("Βοήθειες"),gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel7.add(deleteRowBtn,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel7.add(deleteRowTextField,gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel7.add(inf1,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel7.add(rearrangeRowBtn,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel7.add(rearrangeRowTextField,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel7.add(inf2,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel7.add(deleteColBtn,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel7.add(deleteColTextField,gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel7.add(inf3,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel7.add(rearrangeColBtn,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel7.add(rearrangeColTextField,gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel7.add(inf4,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel7.add(rearrangeBoardBtn,gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        panel7.add(inf5,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel7.add(switchTwoLettersBtn,gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        panel7.add(inf6,gbc);
        
        //Prosthesi koumpiwn ston akroati ActionListener
        CheckWordBtn.addActionListener(handler);
        deleteRowBtn.addActionListener(handler);
        deleteColBtn.addActionListener(handler);
        rearrangeRowBtn.addActionListener(handler);
        rearrangeColBtn.addActionListener(handler);
        rearrangeBoardBtn.addActionListener(handler);
        switchTwoLettersBtn.addActionListener(handler);
        
        updatedInf = new JLabel("Προς το παρόν, καμία ενημέρωση για το νέο παιχνίδι.");
        achievePoints = new JLabel();
        sumPoints = new JLabel();
        wordPoints = new JLabel();
        finedWords = new JLabel();

        achievePoints.setText(String.valueOf(stoxos) );
        sumPoints.setText(String.valueOf(playerPoints) );
        wordPoints.setText(String.valueOf(wordPoint) );
        finedWords.setText("0");
        
        panel8.setLayout(new GridBagLayout());
        String spaces="                    ";
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;//topothetei pros dutika ta components
        panel8.add(new JLabel("Στόχος:"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel8.add(new JLabel(spaces),gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel8.add(achievePoints,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel8.add(new JLabel("Συνολική Βαθμολογία:"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel8.add(new JLabel(spaces),gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel8.add(sumPoints,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel8.add(new JLabel("Βαθμολογία Λέξης:"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel8.add(new JLabel(spaces),gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel8.add(wordPoints,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel8.add(new JLabel("Λέξεις που βρέθηκαν:"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel8.add(new JLabel(spaces),gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        panel8.add(finedWords,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3; //poses theseis ston orizontia aksona theloume na katalabei to component pou tha kataxwrisoume stin panel8 me GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel8.add(updatedInf,gbc);
                        
        //panel7.setBorder(BorderFactory.createLineBorder(Color.black));
        
        gbcPane.gridx = 1;
        gbcPane.gridy = 0;
        pane.add(panel7,gbcPane);
        gbcPane.gridx = 1;
        gbcPane.gridy = 1;
        pane.add(panel8,gbcPane);
                                
        frame.setContentPane(pane);
        frame.pack();
    }
    
    //arxikopoihsi tamplou kai etoimasia tou paixnidiou
    public void Initialize()
    {
        wManage = new wordsManage(textfld2.getText()) 
            {//prosthetoume mia ulopoihsh tis getPointsWord dioti suntaktika den epitrepetai dimiourgia abstract antikeimenwn
                @Override
                public int getPointsWord(String word) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        
                //DEFAULT TIMES GIA TIS METABLITES (arxikopoihsi)
        cp = new computePoints();
        rearrangeTable = new Rearrange();
        playerPoints = 0;
        wordPoint = 0;
        geitniasi=true;
        findedAtLeastOneBlue=false;
        balnterIsSetted = false;
        cntfinedWords=0;
        
        //midenismos listwn
        for(int k=RedBackGrounds.size()-1;k>=0;k--)
            RedBackGrounds.remove(k);
        for(int k=BlueBackGrounds.size()-1;k>=0;k--)
            BlueBackGrounds.remove(k);
        for(int k=selectedJLetters.size()-1;k>=0;k--)
            selectedJLetters.remove(k);
        
        //counters
        cntRedBackgrounds=0; 
        cntBlueBackgrounds=0;
        cntBalanters=0;
        
        cntdeleteRowJL=cntdeleteColJL=cntrearrangeRowJL=cntrearrangeColJL=cntrearrangeBoardJL=cntswitchTwoLettersJL = 0;
        //maxdeleteRowJL=maxdeleteColJL=maxrearrangeRowJL=maxrearrangeColJL=5;
        //maxrearrangeBoardJL=maxswitchTwoLettersJL=10;
        
        n = (int) Math.sqrt(tableLetters);
        tableBoard = new Object[n][n];
        tableBoard = wManage.findFittedWordsOnPannel(tableLetters);
        //Anadiataksi tamplou
        tableBoard = rearrangeTable.rearrangeLetters(tableBoard,n);
        
        //Anadiataksi tamplou xwris grafika (sxolia)
        //<editor-fold>
        //tableBoard = rearrangeTable.rearrangeLetters(tableBoard,n);
        
        /*
        for(int i=0; i<n;i++)
        {
            for(int j=0;j<n;j++)
                System.out.print(tableBoard[i][j]+" ");
        System.out.println();
        }
        System.out.println("-----------------------------------------");
        /*        
        //tableBoard = rearrangeTable.switchToLetters(tableBoard, 2, 3, 5, 6);
        //tableBoard = rearrangeTable.newRowLetters(tableBoard, 2, n, wManage);
        //tableBoard = rearrangeTable.newColumnLetters(tableBoard, 2, n, wManage);
        //tableBoard = rearrangeTable.rearrangeLetters(tableBoard, n);
        //tableBoard = rearrangeTable.rearrangeRow(tableBoard, 3, n);
        //tableBoard = rearrangeTable.rearrangeCol(tableBoard, 7, n);
        
        for(int i=0; i<n;i++)
        {
            for(int j=0;j<n;j++)
                System.out.print(tableBoard[i][j]+" ");
        System.out.println();
        }
        */
        //</editor-fold>
    }
    
    
    public boolean checkWordExistance(String word)
    {
        if(wManage.wordExists(word)==true) return true;
        else return false;
    }
    
    public int stringPoints(String currentFindedWord)
    {
        return cp.getPointsWord(currentFindedWord);
    }
    
    
    public void RemoveSelectedYellows()
    {
        //remove selected yellows
        for(int i=selectedJLetters.size()-1;i>=0;i--)
        {
            selectedJLetters.get(i).getComponent().setBackground(Color.white);

            position = selectedJLetters.get(i).getComponent().getName();
            iIndex = Character.getNumericValue(position.charAt(0));
            jIndex = Character.getNumericValue(position.charAt(1));

            //Epanafora xrwmatos stin arxiki tou emfanisi otan ksestekaretai i epilogi apo to tamplo
            for(int r=0;r<RedBackGrounds.size();r++)
                if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == iIndex &&
                        Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == jIndex )
                    selectedJLetters.get(i).getComponent().setBackground(Color.red);
            for(int b=0;b<BlueBackGrounds.size();b++)
                if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == iIndex &&
                        Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == jIndex )
                    selectedJLetters.get(i).getComponent().setBackground(Color.cyan);

            selectedJLetters.remove(i);
        }
    }
    
    public void RemoveSelectedGrayBorders()
    {
        //remove selected gray border for exchange 
        for(int i=selected2JLettersForExchange.size()-1;i>=0;i--)
        {
            int ivalue = Character.getNumericValue(selected2JLettersForExchange.get(i).charAt(0));
            int jvalue = Character.getNumericValue(selected2JLettersForExchange.get(i).charAt(1));

            jTextPane[ivalue][jvalue].setBorder(BorderFactory.createLineBorder(Color.black));
            selected2JLettersForExchange.remove(i);
        }
    }
    
    public void setRedBackGrounds(int i, int j)
    {
        //Mesw tis random k tis rangePosibility epityxanetai na sximatisoume mia pithanotita p.x 1/rangePosibility
        //Stin periptwsi pou tyxainei auti i pitanotita prosthetoume RedBackground sto tamplo
        //o arithmos 4 epilexthke  tuxaia
        if(cntRedBackgrounds < MaxRedBackgrounds && (int)(Math.random() * rangePosibility + 1) == 4)
        {
            jTextPane[i][j].setBackground(Color.red);
            RedBackGrounds.add(i+""+j);
            cntRedBackgrounds++;
        }
        
    }
    
    public void setBlueBackGrounds(int i, int j)
    {
        //Mesw tis random k tis rangePosibility epityxanetai na sximatisoume mia pithanotita p.x 1/rangePosibility
        //Stin periptwsi pou tyxainei auti i pitanotita prosthetoume BlueBackground sto tamplo
        if(cntBlueBackgrounds < MaxBlueBackgrounds && (int)(Math.random() * rangePosibility + 1) == 4)
        {
            jTextPane[i][j].setBackground(Color.cyan);
            BlueBackGrounds.add(i+""+j);
            cntBlueBackgrounds++;
        }
    }
    
    public void setBalanters(int i, int j)
    {
        //Mesw tis random k tis rangePosibility epityxanetai na sximatisoume mia pithanotita p.x 1/rangePosibility
        //Stin periptwsi pou tyxainei auti i pitanotita prosthetoume mpalanter sto tamplo
        balnterIsSetted = false;
        if(cntBalanters < MaxBalanters && (int)(Math.random() * rangePosibility + 1) == 4)
        {
            tableBoard[i][j] = '?';
            cntBalanters++;
            balnterIsSetted = true;
        }
    }
    
    //Leitourgies Boitheiwn
    public void Rearrangements(String nameFunction)
    {
        numberSlectedfromTextFields=0;
        
        if(nameFunction.equals("newRowLetters"))
        {//<editor-fold>
            //elegxos egkurotitas arithmou apo text field
            numberSlectedfromTextFields = returnCheckedNrField(deleteRowTextField);
            if(numberSlectedfromTextFields==-1)
                return;
            
            //enimerwsi eidikwn xaraktirwn stin sygkekrimeni grammi
            manageFindedSpecialChar(numberSlectedfromTextFields-1,1,"no");
            
            //Anadiatasei me neous tuxaious xaraktires tin sugkekrimeni grammi
            //Oi eidikoi xaraktires xanontai
            tableBoard = rearrangeTable.newRowLetters(tableBoard,numberSlectedfromTextFields - 1 , n, wManage);
            
            //Symplirwsi tuxaia apodidontas eidikous xaraktires stin sygkekrimeni grammi
            for(int i=0;i<n;i++)
                setSpecialLetters(numberSlectedfromTextFields-1,i);
            
            
            cntdeleteRowJL++;
            inf1.setText(cntdeleteRowJL+"/"+maxdeleteRowJL);
        }//</editor-fold>
        
        if(nameFunction.equals("rearrangeRow"))
        {//<editor-fold>
            //elegxos egkurotitas arithmou apo text field
            numberSlectedfromTextFields = returnCheckedNrField(rearrangeRowTextField);
            if(numberSlectedfromTextFields==-1)
                return;
            
            //enimerwsi eidikwn xaraktirwn stin sygkekrimeni grammi
            manageFindedSpecialChar(numberSlectedfromTextFields-1,1,"yes");
            
            //Anadiatasei me neous tuxaious xaraktires tin sugkekrimeni grammi
            //Oi eidikoi xaraktires xanontai
            tableBoard = rearrangeTable.rearrangeRow(tableBoard,numberSlectedfromTextFields - 1 , n);
            
            //topothetountai ta backgrounds sta antistoixa grammata pou eixan prin tin anadiataksi
            for(int i=0;i<n;i++)
            {
                int r=numberSlectedfromTextFields-1;
                
                if(tableBoard[r][i].toString().charAt(0) == 'r')
                {
                    tableBoard[r][i] = tableBoard[r][i].toString().charAt(1);
                    jTextPane[r][i].setBackground(Color.red);
                    RedBackGrounds.add(r+""+i);
                    cntRedBackgrounds++;
                }
                if(tableBoard[r][i].toString().charAt(0) == 'b')
                {
                    tableBoard[r][i] = tableBoard[r][i].toString().charAt(1);
                    jTextPane[r][i].setBackground(Color.cyan);
                    BlueBackGrounds.add(r+""+i);
                    cntBlueBackgrounds++;
                }
                
                //Kataxwrisi neou styled html periexomenou pros emfanisi
                jTextPane[r][i].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[r][i].toString()+"</span>"
                                  +"<span style=\"font-size:11\">"+ String.valueOf(
                        //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                        cp.pointsOfChar(tableBoard[r][i].toString().charAt(0)) 
                    )+"</body>");
            }
            
            cntrearrangeRowJL++;
            inf2.setText(cntrearrangeRowJL+"/"+maxrearrangeRowJL);
        }//</editor-fold>
        
        if(nameFunction.equals("newColumnLetters"))
        {//<editor-fold>
            //elegxos egkurotitas arithmou apo text field
            numberSlectedfromTextFields = returnCheckedNrField(deleteColTextField);
            if(numberSlectedfromTextFields==-1)
                return;
            
            //enimerwsi eidikwn xaraktirwn stin sygkekrimeni stili
            manageFindedSpecialChar(numberSlectedfromTextFields-1,2,"no");
            
            //Anadiatasei me neous tuxaious xaraktires stin sugkekrimeni stili
            //Oi eidikoi xaraktires xanontai
            tableBoard = rearrangeTable.newColumnLetters(tableBoard,numberSlectedfromTextFields - 1 , n, wManage);
            
            //Symplirwsi tuxaia apodidontas eidikous xaraktires stin sygkekrimeni grammi
            for(int i=0;i<n;i++)
                setSpecialLetters(i,numberSlectedfromTextFields-1);
            
            cntdeleteColJL++;
            inf3.setText(cntdeleteColJL+"/"+maxdeleteColJL);
        }//</editor-fold>
        
        if(nameFunction.equals("rearrangeCol"))
        {//<editor-fold>
            //elegxos egkurotitas arithmou apo text field
            numberSlectedfromTextFields = returnCheckedNrField(rearrangeColTextField);
            if(numberSlectedfromTextFields==-1)
                return;
            
            //enimerwsi eidikwn xaraktirwn stin sygkekrimeni grammi
            manageFindedSpecialChar(numberSlectedfromTextFields-1,2,"yes");
            
            //Anadiatasei tuxaia tpus xaraktires apo tin sugkekrimeni stili
            tableBoard = rearrangeTable.rearrangeCol(tableBoard,numberSlectedfromTextFields - 1 , n);
            
            //topothetountai ta backgrounds sta antistoixa grammata pou eixan prin tin anadiataksi
            for(int i=0;i<n;i++)
            {
                int r=numberSlectedfromTextFields-1;
                
                if(tableBoard[i][r].toString().charAt(0) == 'r')
                {
                    tableBoard[i][r] = tableBoard[i][r].toString().charAt(1);
                    jTextPane[i][r].setBackground(Color.red);
                    RedBackGrounds.add(i+""+r);
                    cntRedBackgrounds++;
                }
                if(tableBoard[i][r].toString().charAt(0) == 'b')
                {
                    tableBoard[i][r] = tableBoard[i][r].toString().charAt(1);
                    jTextPane[i][r].setBackground(Color.cyan);
                    BlueBackGrounds.add(i+""+r);
                    cntBlueBackgrounds++;
                }
                
                //Kataxwrisi neou styled html periexomenou pros emfanisi
                jTextPane[i][r].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[i][r].toString()+"</span>"
                                  +"<span style=\"font-size:11\">"+ String.valueOf(
                        //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                        cp.pointsOfChar(tableBoard[i][r].toString().charAt(0)) 
                    )+"</body>");
            }
            
            cntrearrangeColJL++;
            inf4.setText(cntrearrangeColJL+"/"+maxrearrangeColJL);
        }//</editor-fold>
        
        if(nameFunction.equals("rearrangeLetters"))
        {//<editor-fold>
            
            //enimerwsi eidikwn xaraktirwn ston pinaka
            manageFindedSpecialChar(numberSlectedfromTextFields-1,3,"no");
            
            //anadiataksi
            tableBoard = rearrangeTable.rearrangeLetters(tableBoard, n);
            
            //topothetountai ta backgrounds sta antistoixa grammata pou eixan prin tin anadiataksi
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                {
                    if(tableBoard[i][j].toString().charAt(0) == 'r')
                    {
                        tableBoard[i][j] = tableBoard[i][j].toString().charAt(1);
                        jTextPane[i][j].setBackground(Color.red);
                        RedBackGrounds.add(i+""+j);
                        cntRedBackgrounds++;
                    }
                    if(tableBoard[i][j].toString().charAt(0) == 'b')
                    {
                        tableBoard[i][j] = tableBoard[i][j].toString().charAt(1);
                        jTextPane[i][j].setBackground(Color.cyan);
                        BlueBackGrounds.add(i+""+j);
                        cntBlueBackgrounds++;
                    }

                    //Kataxwrisi neou styled html periexomenou pros emfanisi
                    jTextPane[i][j].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[i][j].toString()+"</span>"
                                      +"<span style=\"font-size:11\">"+ String.valueOf(
                            //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                            cp.pointsOfChar(tableBoard[i][j].toString().charAt(0)) 
                        )+"</body>");
                }
            
            cntrearrangeBoardJL++;
            inf5.setText(cntrearrangeBoardJL+"/"+maxrearrangeBoardJL);
        }//</editor-fold>
        
        if(nameFunction.equals("switchTwoLetters"))
        {//<editor-fold>
            if(selected2JLettersForExchange.size() != 2)
            {
                JOptionPane.showMessageDialog(null,
                    "Πρέπει να είναι επιλεγμένα 2 γράμματα απο το ταμπλό.\n(διπλό κλικ για να επιλέξετε το κάθε γραμμα)", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int xi = Character.getNumericValue(selected2JLettersForExchange.get(0).charAt(0));
            int xj = Character.getNumericValue(selected2JLettersForExchange.get(0).charAt(1));
            int yi = Character.getNumericValue(selected2JLettersForExchange.get(1).charAt(0));
            int yj = Character.getNumericValue(selected2JLettersForExchange.get(1).charAt(1));
            
            //enimerwsi kainourgiwn deiktwn stis listes RedBackGrounds kai BlueBackGrounds
            if(jTextPane[xi][xj].getBackground() == Color.red)
                for(int r=0;r<RedBackGrounds.size();r++)
                    if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == xi &&
                                Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == xj )
                    {
                        RedBackGrounds.remove(r);
                        RedBackGrounds.add(yi+""+yj);
                    }
            if(jTextPane[yi][yj].getBackground() == Color.red)
                for(int r=0;r<RedBackGrounds.size();r++)
                    if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == yi &&
                                Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == yj )
                    {
                        RedBackGrounds.remove(r);
                        RedBackGrounds.add(xi+""+xj);
                    }
            if(jTextPane[xi][xj].getBackground() == Color.cyan)
                for(int b=0;b<BlueBackGrounds.size();b++)
                    if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == xi &&
                                Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == xj )
                    {
                        BlueBackGrounds.remove(b);
                        BlueBackGrounds.add(yi+""+yj);
                    }
            if(jTextPane[yi][yj].getBackground() == Color.cyan)
                for(int b=0;b<BlueBackGrounds.size();b++)
                    if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == yi &&
                                Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == yj )
                    {
                        BlueBackGrounds.remove(b);
                        BlueBackGrounds.add(xi+""+xj);
                    }
             
            
            Object temp = tableBoard[xi][xj];
            tableBoard[xi][xj] = tableBoard[yi][yj];
            tableBoard[yi][yj] = temp;
                        
            Color colorTemp = jTextPane[xi][xj].getBackground();
            jTextPane[xi][xj].setBackground(jTextPane[yi][yj].getBackground());
            jTextPane[yi][yj].setBackground(colorTemp);
            
                        
            //Kataxwrisi neou styled html periexomenou pros emfanisi gia tis syntetagmenes xi,xj
            jTextPane[xi][xj].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[xi][xj].toString()+"</span>"
                              +"<span style=\"font-size:11\">"+ String.valueOf(
                    //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                    cp.pointsOfChar(tableBoard[xi][xj].toString().charAt(0)) 
                )+"</body>");
            
            //Kataxwrisi neou styled html periexomenou pros emfanisi gia tis syntetagmenes yi,yj
            jTextPane[yi][yj].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[yi][yj].toString()+"</span>"
                              +"<span style=\"font-size:11\">"+ String.valueOf(
                    //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                    cp.pointsOfChar(tableBoard[yi][yj].toString().charAt(0)) 
                )+"</body>");
            
            RemoveSelectedGrayBorders();
            
            cntswitchTwoLettersJL++;
            inf6.setText(cntswitchTwoLettersJL+"/"+maxswitchTwoLettersJL);                
        }//</editor-fold>
    }
    
    public int returnCheckedNrField(JTextField field)
    {
        try
        {
            numberSlectedfromTextFields = Integer.parseInt(field.getText());
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "Στα πεδία Text Fields, πρέπει να δωθούν ακέραιοι θετικοί αριθμοί.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if ( numberSlectedfromTextFields < 1 || numberSlectedfromTextFields > n )
        {
            JOptionPane.showMessageDialog(null,
                    "Στο πεδία Text Fields, πρέπει να δωθεί ακέραιος θετικός αριθμός στο διάστημα ["+1+","+n+"].", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        return numberSlectedfromTextFields;
    }
    
    //Entopizontai kai xeirizotai katallila oi eidikoi xaraktires otan einai amagkaia i anadiataksi pinaka
    //O entopismos mporei na ginei apo grammi, stili i ki apo olokliro ton pinaka kataxwrontas tin antistoixia mesw metabliti operation
    //Diaxeirizontai/Enimerwnontai katallila oi metrites j oi listes twn eidikwn xaraktirwn
    //H parametros store, dilwnei tin anakaiotita na markaristei antistoixa to red/blue backgraound sto tableBoard me enan xaraktira 'r'/'b' 
    //mprosta apo to antistoixo periexomeno
    public void manageFindedSpecialChar(int rc, int operation,String store)
    {
        if(operation == 1) //leitourgia gia grammes
            //gia tin sugkekrimeni grammi elegxetai an yparxoyn eidikoi xaraktires kai tous markarei sto tableBoard
            for(int ij=0; ij < n; ij++)
            {
                if(store.equals("yes"))
                {
                     if(tableBoard[rc][ij].equals('?')) cntBalanters--;
                }
                for(int r=0;r<RedBackGrounds.size();r++)
                    if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == rc &&
                            Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == ij )
                    {
                        if(store.equals("yes")) tableBoard[rc][ij] = "r" + tableBoard[rc][ij];//markarisma san red background
                        RedBackGrounds.remove(r);
                        cntRedBackgrounds--;
                        jTextPane[rc][ij].setBackground(Color.white);
                        break;
                    }
                for(int b=0;b<BlueBackGrounds.size();b++)
                    if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == rc &&
                            Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == ij )
                    {
                        if(store.equals("yes")) tableBoard[rc][ij] = "b" + tableBoard[rc][ij];//markarisma san blue background
                        BlueBackGrounds.remove(b);
                        cntBlueBackgrounds--;
                        jTextPane[rc][ij].setBackground(Color.white);
                        break;
                    }
            }
        
        if(operation == 2)//leitourgia gia stiles
            //gia tin sugkekrimeni stili elegxetai an yparxoyn eidikoi xaraktires kai tous markarei sto tableBoard
            for(int ij=0; ij < n; ij++)
            {
                if(tableBoard[ij][rc].equals('?'))
                {
                    cntBalanters--;
                }
                for(int r=0;r<RedBackGrounds.size();r++)
                    if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == ij &&
                            Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == rc )
                    {
                        if(store.equals("yes")) tableBoard[ij][rc] = "r" + tableBoard[ij][rc];
                        RedBackGrounds.remove(r);
                        cntRedBackgrounds--;
                        jTextPane[ij][rc].setBackground(Color.white);
                        break;
                    }
                for(int b=0;b<BlueBackGrounds.size();b++)
                    if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == ij &&
                            Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == rc )
                    {
                        if(store.equals("yes")) tableBoard[ij][rc] = "b" + tableBoard[ij][rc];
                        BlueBackGrounds.remove(b);
                        cntBlueBackgrounds--;
                        jTextPane[ij][rc].setBackground(Color.white);
                        break;
                    }
            }
        
        if(operation == 3)
            //gia tin olon ton pinaka elegxetai an yparxoyn eidikoi xaraktires kai tous markarei sto tableBoard
            for(int i=0; i < n; i++)
                for(int j=0; j < n; j++)
                {
                    for(int r=0;r<RedBackGrounds.size();r++)
                        if( Character.getNumericValue(RedBackGrounds.get(r).charAt(0)) == i &&
                                Character.getNumericValue(RedBackGrounds.get(r).charAt(1)) == j )
                        {
                            tableBoard[i][j] = "r" + tableBoard[i][j];
                            RedBackGrounds.remove(r);
                            cntRedBackgrounds--;
                            jTextPane[i][j].setBackground(Color.white);
                            break;
                        }
                    for(int b=0;b<BlueBackGrounds.size();b++)
                        if( Character.getNumericValue(BlueBackGrounds.get(b).charAt(0)) == i &&
                                Character.getNumericValue(BlueBackGrounds.get(b).charAt(1)) == j)
                        {
                            tableBoard[i][j] = "b" + tableBoard[i][j];
                            BlueBackGrounds.remove(b);
                            cntBlueBackgrounds--;
                            jTextPane[i][j].setBackground(Color.white);
                            break;
                        }
                }
    }
    
    //Topothetisi stin thesi i,j xaraktira, o opoios kata tyxaia mporei na einai k eidikos xaraktiras
    public void setSpecialLetters(int i,int j)
    {
        setBalanters(i,j);//tuxaia

        //Kataxwrisi neou styled html periexomenou pros emfanisi
        jTextPane[i][j].setText("<body style=\"text-align:center;\"> <span style=\"font-weight:bold; font-size:22 \">"+tableBoard[i][j].toString()+"</span>"
                          +"<span style=\"font-size:11\">"+ String.valueOf(
                //ypologismos pontwn tou xaraktira pou metatrepetai object se -> char
                cp.pointsOfChar(tableBoard[i][j].toString().charAt(0)) 
            )+"</body>");

        jTextPane[i][j].setBackground(Color.white);
        
        if(balnterIsSetted == false)
            if( (int)(Math.random()*2 + 1) == 1 ) setRedBackGrounds(i,j);
            else setBlueBackGrounds(i,j);
    }
    
}