package kryptolekso;

import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextPane;

public class Help {
    JFrame frame;
    Container pane;
    public Help()
    {
        frame = new JFrame("Βοήθεια");
        frame.setLocation(750,100);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        JTextPane jTextPane = new JTextPane();
        jTextPane.setBorder(BorderFactory.createLineBorder(Color.black));
        //periexomeno sto jTextPane na einai tupou html
        jTextPane.setContentType("text/html");
        jTextPane.setEditable(false);
        jTextPane.setText(getHelp());
        
        pane = frame.getContentPane();
        pane.add(jTextPane);
        frame.setContentPane(pane);
        frame.pack();
        
    }
    public String getHelp()
    {
        String help="";
        
        help="<body >"
                + "<h3 style=\"text-align:center;\">Βοήθειες Χρήσης Παιχνιδιού</h3>"
                + "<ul>"
                    +"<li>Προσπαθήστε να βρείτε λέξεις απο την συλλογή λέξεων(.txt) που επιλέξατε,<br/>πατώντας κλικ στα γράμματα του ταμπλού με τους αντίστοιχους πόντους." 
                    +"<br/>Σκοπός του παιχνιδιού είναι να βρεθούν πολλές λέξεις με τους μέγιστους πόντους.</li>"
                    +"<li>Μπορείτε να επιλέξετε νέα δικιά σας συλλογή (.txt)<br/>σε ενα ήδη τρέχων παιχνίδι ώστε να βρείτε λέξεις απο αυτήν(Αναζήτηση αρχείου λέξεων)."
                    +"<br/>Προσοχή, το αρχείο .txt πρέπει να περιέχει αυστηρά τέτοια δομή<br/>ώστε η κάθε λέξη να αποτελεί καινούργια γραμμή χωρίς κενά.</li>"
                    +"<li>Στα επιλεγμένα γράμματα εμφανίζεται κίτρινο background."
                    +"<br/>Για να αφαιρεθεί/ματαιωθεί η τελευταία επιλογή γραμμάτων,<br/>πατήστε ξάνα κλικ στην τελευταία επιλογή."
                    +"<br/>Για να αφαιρεθεί όλη η επιλογή των γραμμάτων ώστε να ξεκινήσει νέα, πατήστε δεξί κλικ.</li>"
                    +"<li>Στο ταμπλό εμφανίζονται τυχαία ειδικά γράμματα που μεγιστοποιούν τους πόντους."
                    +"<br/>Τα γράμματα με κόκκινο background, διπλασιάζουν τους πόντους του αντίστοιχου γράμματος που αναφέρεται."
                    +"<br/>Τα γράμματα με μπλε background, διπλασιάζουν τους πόντους της συνολικής επιτυχής λέξης που βρίσκεται."
                    +"<br/>(Εαν σε μια επιτυχή λέξη υπάρχουν παραπάνω απο ενα μπλε background,<br/>ο διπλασιασμος της ποντων της συνολικής λέξης προσμετρείται μία φορά)</li>"
                    +"<li>Χρησιμοποιήστε τις βοήθειες που βρίσκονται δεξιά του ταμπλού <br/>ώστε να αναδιατάξετε και να ανανεώσετε γράμματα με σκοπό"
                    +"<br/>να διευκολυνθεί η εύρεση λέξεων. Στα text fields, τοποθετούνται <br/>αριθμοί που αντιπροσωπεύουν αντίστοιχα τις γραμμές/στήλες."
                    +"<br/>Για να ανταλλάξτε δυο γράμματα, πατήστε διπλό κλικ στο καθένα (εμφανίζεται gray border).</li>"
                +"</ul></body>";
        
        return help;
    }
    public void makeVisible()
    {
        frame.setVisible(true);
    }
}
