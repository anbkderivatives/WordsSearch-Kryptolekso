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
        frame = new JFrame("�������");
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
                + "<h3 style=\"text-align:center;\">�������� ������ ����������</h3>"
                + "<ul>"
                    +"<li>����������� �� ������ ������ ��� ��� ������� ������(.txt) ��� ���������,<br/>�������� ���� ��� �������� ��� ������� �� ���� ������������ �������." 
                    +"<br/>������ ��� ���������� ����� �� ������� ������ ������ �� ���� ��������� �������.</li>"
                    +"<li>�������� �� ��������� ��� ����� ��� ������� (.txt)<br/>�� ��� ��� ������ �������� ���� �� ������ ������ ��� �����(��������� ������� ������)."
                    +"<br/>�������, �� ������ .txt ������ �� �������� ������� ������ ����<br/>���� � ���� ���� �� �������� ���������� ������ ����� ����.</li>"
                    +"<li>��� ���������� �������� ����������� ������� background."
                    +"<br/>��� �� ���������/��������� � ��������� ������� ���������,<br/>������� ���� ���� ���� ��������� �������."
                    +"<br/>��� �� ��������� ��� � ������� ��� ��������� ���� �� ��������� ���, ������� ���� ����.</li>"
                    +"<li>��� ������ ������������ ������ ������ �������� ��� ������������� ���� �������."
                    +"<br/>�� �������� �� ������� background, ������������ ���� ������� ��� ����������� ��������� ��� ����������."
                    +"<br/>�� �������� �� ���� background, ������������ ���� ������� ��� ��������� �������� ����� ��� ���������."
                    +"<br/>(��� �� ��� ������� ���� �������� �������� ��� ��� ���� background,<br/>� ������������ ��� ������ ��� ��������� ����� ������������� ��� ����)</li>"
                    +"<li>�������������� ��� �������� ��� ���������� ����� ��� ������� <br/>���� �� ������������ ��� �� ���������� �������� �� �����"
                    +"<br/>�� ������������ � ������ ������. ��� text fields, ������������� <br/>������� ��� ��������������� ���������� ��� �������/������."
                    +"<br/>��� �� ���������� ��� ��������, ������� ����� ���� ��� ������ (����������� gray border).</li>"
                +"</ul></body>";
        
        return help;
    }
    public void makeVisible()
    {
        frame.setVisible(true);
    }
}
