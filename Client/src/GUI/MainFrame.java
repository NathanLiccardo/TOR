/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Client.SendingMessage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author nathan
 */
public class MainFrame extends JFrame implements ActionListener {
    private SendingMessage send;
    private JTextArea _textArea;
    private JTextField _textField;
    private JScrollPane _scrollPane;
    private GridBagConstraints _gridConstraints;
    
    private void initFrame() {
        this.setTitle("Client");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
    }
    private void setGridConstraints(int one,int two,double tree,double four, int five, int six, int seven, int eight,Insets in) {
        if (one != -1) _gridConstraints.gridx = one;
        if (two != -1) _gridConstraints.gridy = two;
        if (tree != -1) _gridConstraints.weightx = tree;
        if (four != -1) _gridConstraints.weighty = four;
        if (five != -1) _gridConstraints.gridwidth = five;
        if (six != -1) _gridConstraints.gridheight = six;
        if (seven != -1) _gridConstraints.fill = seven;
        if (eight != -1) _gridConstraints.anchor = eight;
        if (in != null) _gridConstraints.insets = in;
    }
    private void addElements() {
        setGridConstraints(0, 0, -1, -1, GridBagConstraints.REMAINDER, 1, -1, GridBagConstraints.LINE_START, new Insets(10, 15, 0, 0));
        this.add(new JLabel("Messages : "), _gridConstraints);
        setGridConstraints(0, 1, 1., 1., GridBagConstraints.REMAINDER, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START, new Insets(10, 15, 0, 10));
        this.add(_scrollPane,_gridConstraints);
        setGridConstraints(0, 2, 0., 0., -1, -1, GridBagConstraints.NONE, GridBagConstraints.LINE_START, new Insets(10, 15, 0, 0));
        this.add(new JLabel("Votre message : "),_gridConstraints);
        setGridConstraints(0, 3, 1., 0, 3, -1, GridBagConstraints.HORIZONTAL, GridBagConstraints.LINE_START, new Insets(10, 15, 5, 10));
        this.add(_textField, _gridConstraints);
        setGridConstraints(3, 4, 0., -1, -1, -1, GridBagConstraints.NONE, GridBagConstraints.BASELINE, new Insets(10, 3, 5, 10));
        JButton sendButton = new JButton("Envoi");
        sendButton.addActionListener(this);
        this.add(sendButton, _gridConstraints);
    }
    private void initLayout() {
        this.setLayout(new GridBagLayout());
        _textField = new JTextField(" ");
        _textArea = new JTextArea();
        _textArea.setLineWrap(true);
        _textArea.setWrapStyleWord(true);
        _scrollPane = new JScrollPane(_textArea);
        _scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addElements();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        send.createMessage(_textField.getText());
        updateScrollPane("Vous : \n"+_textField.getText());
        _textField.setText(" ");
    }
    
    public void updateScrollPane(String message) {
        _textArea.append(message);
        _textArea.append("\n\n");
    }
    
    public MainFrame(SendingMessage sm){
        send = sm;
        _gridConstraints = new GridBagConstraints();
        initFrame();
        initLayout();               
        this.setVisible(true);
  }       
}
