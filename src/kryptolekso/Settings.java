package kryptolekso;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Settings {
    JFrame frameS;
    Container paneS;
    JPanel panel1S;
    JButton saveS;
    GamePlayContainer game;
    JComboBox numbersJC1,numbersJC2,numbersJC3,numbersJC4,numbersJC5,numbersJC6,numbersJC7;
    public Settings (GamePlayContainer g)
    {
        game=g;
        frameS = new JFrame("Ρυμίσεις");
        frameS.setLocation(250,100);
        frameS.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //frameS.setSize(550,450);
                
        paneS = frameS.getContentPane();
        panel1S = new JPanel();
        
        saveS = new JButton("Αποθήκευση");
        String[] numbers ={"1","2","3","4","5","6","7","8","9","10",
                            "11","12","13","14","15","16","17","18","19","20",
                            "21","22","23","24","25","26","27","28","29","30"
                            };
        String[] pointsOption ={"25","50","75","100",
                                "125","150","175","200",
                                "225","250","275","300",
                                "325","350","375","400",
                                "425","450","475","500",
                                "525","550","575","600"
                                };
        numbersJC1 = new JComboBox(numbers);
        numbersJC2 = new JComboBox(numbers);
        numbersJC3 = new JComboBox(numbers);
        numbersJC4 = new JComboBox(numbers);
        numbersJC5 = new JComboBox(numbers);
        numbersJC6 = new JComboBox(numbers);
        numbersJC7 = new JComboBox(pointsOption);
        //default times
        numbersJC1.setSelectedIndex(4);
        numbersJC2.setSelectedIndex(4);
        numbersJC3.setSelectedIndex(4);
        numbersJC4.setSelectedIndex(4);
        numbersJC5.setSelectedIndex(9);
        numbersJC6.setSelectedIndex(9);
        numbersJC7.setSelectedIndex(7);
        
        GridBagConstraints gbcPane = new GridBagConstraints();
        gbcPane.insets = new Insets(6,6,6,6);//padding
        paneS.setLayout(new GridBagLayout());
        gbcPane.gridx=0;//Column posittion
        gbcPane.gridy=0;//Row posittion
        paneS.add(new JLabel("Ρυθμίσεις Βοηθειών"),gbcPane);
        
        GridBagConstraints gbcPanel1 = new GridBagConstraints();
        panel1S.setLayout(new GridBagLayout());
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=0;//Row posittion
        panel1S.add(new JLabel("Max Διαγραφές Γραμμών :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=0;
        panel1S.add(numbersJC1,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=1;//Row posittion
        panel1S.add(new JLabel("Max Αναδιατάξεις Γραμμών :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=1;
        panel1S.add(numbersJC2,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=2;//Row posittion
        panel1S.add(new JLabel("Max Διαγραφές Στηλών :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=2;
        panel1S.add(numbersJC3,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=3;//Row posittion
        panel1S.add(new JLabel("Max Αναδιατάξεις Στηλών :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=3;
        panel1S.add(numbersJC4,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=4;//Row posittion
        panel1S.add(new JLabel("Max Αναδιατάξεις Ταμπλού :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=4;
        panel1S.add(numbersJC5,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=5;//Row posittion
        panel1S.add(new JLabel("Max Εναλλαγή Γραμμάτων :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=5;
        panel1S.add(numbersJC6,gbcPanel1);
        gbcPanel1.gridx=0;//Column posittion
        gbcPanel1.gridy=6;//Row posittion
        panel1S.add(new JLabel("Max Στοχος πόντων :"),gbcPanel1);
        gbcPanel1.gridx=1;
        gbcPanel1.gridy=6;
        panel1S.add(numbersJC7,gbcPanel1);
        
        gbcPane.gridx=0;
        gbcPane.gridy=1;
        paneS.add(panel1S,gbcPane);
        
        gbcPane.gridx=0;
        gbcPane.gridy=2;
        paneS.add(saveS,gbcPane);
        
        Handler handler=new Handler();
        saveS.addActionListener(handler);
        
        frameS.setContentPane(paneS);
        frameS.pack();
    }
    public void makeVisible()
    {
        frameS.setVisible(true);
    }
    
    class Handler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource()==saveS)
            {
                //prosarmogi timwn
                game.maxdeleteRowJL = Integer.parseInt(numbersJC1.getSelectedItem().toString());
                game.maxrearrangeRowJL = Integer.parseInt(numbersJC2.getSelectedItem().toString());
                game.maxdeleteColJL = Integer.parseInt(numbersJC3.getSelectedItem().toString());
                game.maxrearrangeColJL = Integer.parseInt(numbersJC4.getSelectedItem().toString());
                game.maxrearrangeBoardJL = Integer.parseInt(numbersJC5.getSelectedItem().toString());
                game.maxswitchTwoLettersJL = Integer.parseInt(numbersJC6.getSelectedItem().toString());
                game.stoxos = Integer.parseInt(numbersJC7.getSelectedItem().toString());
                
                game.inf1.setText(game.cntdeleteRowJL+"/"+game.maxdeleteRowJL);
                game.inf2.setText(game.cntrearrangeRowJL+"/"+game.maxrearrangeRowJL);
                game.inf3.setText(game.cntdeleteColJL+"/"+game.maxdeleteColJL);
                game.inf4.setText(game.cntrearrangeColJL+"/"+game.maxrearrangeColJL);
                game.inf5.setText(game.cntrearrangeBoardJL+"/"+game.maxrearrangeBoardJL);
                game.inf6.setText(game.cntswitchTwoLettersJL+"/"+game.maxswitchTwoLettersJL);
                game.achievePoints.setText(String.valueOf(game.stoxos) );
                
                game.updatedInf.setText("Οι αλλαγές αποθηκεύτηκαν!");
            }
        }
    }
}