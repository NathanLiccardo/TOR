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
public class MainFrame extends JFrame implements ActionListener{
    private JTextField tf;
    private JTextArea ta;
    private JScrollPane jp;
    private JButton button;
    private JLabel title;
    private JLabel title2;
    private GridBagConstraints gbc;
    
    private SendingMessage send;
    
    private void initFrame() {
        this.setTitle("Client");
        this.setSize(450, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
    }
    private void initGbc() {
        gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 15, 0, 0);
        this.add(title, gbc);
    }
    private void addScrollPane() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 1.;
        gbc.weighty = 1.;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 15, 0, 10);
        this.add(jp, gbc);
    }
    private void addLabel() {
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.;
        gbc.weighty = 0.;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 15, 0, 0);
        this.add(title2, gbc);
    }
    private void addTextField() {
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 15, 5, 0);
        this.add(tf, gbc);
    }
    private void addButton() {
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.weightx = 0.;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(10, 3, 5, 10);
        button.addActionListener(this);
        this.add(button, gbc);
    }
    private void initLayout() {
        this.setLayout(new GridBagLayout());
        
        tf = new JTextField(" ");
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        jp = new JScrollPane(ta);
        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        button = new JButton("Envoi");
        title  = new JLabel("Messages : ");
        title2 = new JLabel("Votre message : ");
        
        initGbc();
        addScrollPane();
        addLabel();
        addTextField();
        addButton();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        send.createMessage(tf.getText());
        send.startThreads();
        updateScrollPane("Vous : \n"+tf.getText());
        tf.setText(" ");
    }
    
    public void updateScrollPane(String message) {
        ta.append(message);
        ta.append("\n\n");
    }
    
    public MainFrame(SendingMessage sm){
        send = sm;
        
        initFrame();
        initLayout();               
        this.setVisible(true);
  }       
}
