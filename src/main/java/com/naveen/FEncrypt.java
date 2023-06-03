package com.naveen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class FEncrypt extends JFrame implements ActionListener{
    JPanel pMain =new JPanel();
    JPanel pInfo=new JPanel();
     
    JButton bSelectFile= new JButton("Select File", null);
    JLabel lPassword =new JLabel("Enter Password");
    JTextField tfPassword=new JTextField();

    JButton bEncrypt=new JButton("Encrypt", null);

    File inputFile;
    File outputFile;

    JLabel lSelectedFile1 =new JLabel("Selected File");
    JLabel lSelectedFile2= new JLabel(); 
    JLabel lSelectLocation=new JLabel("Choose Location to save");
    JLabel lSelectedLocation=new JLabel();
    JButton bSaveAs=new JButton("Save As");

    JLabel lWrongPassword=new JLabel();

    FEncrypt(){
        // setVisible(true);
        setTitle("Encrypt PDF");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // setSize(100, 100);
        setBounds(420, 250, 350, 300);


        //pMain
        bSelectFile.addActionListener(this);
        bEncrypt.addActionListener(this);

        tfPassword.setSize(10, 10);
        pMain.add(bSelectFile);
        pMain.add(lPassword);
        pMain.add(tfPassword);
        pMain.add(bEncrypt);
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        pMain.setSize(20, 20);
        pMain.add(lWrongPassword);

        add(pMain);
        // pMain.setVisible(true);
        lPassword.setVisible(false);
        tfPassword.setVisible(false);
        bEncrypt.setVisible(false);

        //pInfo
        bSaveAs.addActionListener(this);
        pInfo.add(lSelectedFile1);
        pInfo.add(lSelectedFile2);
        pInfo.add(lSelectLocation);
        pInfo.add(bSaveAs);
        pInfo.add(lSelectedLocation);
        pInfo.setLayout(new BoxLayout(pInfo, BoxLayout.Y_AXIS));
        pInfo.setSize(20, 20);
        add(pInfo);
        pInfo.setVisible(false);
        lSelectedLocation.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bSelectFile){
            JFileChooser fc=new JFileChooser();  
            int action=fc.showOpenDialog(null);    
            if(action==JFileChooser.APPROVE_OPTION){    
                try{  
                    inputFile=fc.getSelectedFile(); 
                    lPassword.setVisible(true);
                    tfPassword.setVisible(true);
                    bEncrypt.setVisible(true);
                    pInfo.setVisible(true);
                    lSelectedFile2.setText(inputFile.getAbsolutePath());
                }
                catch (Exception ex) {
                    ex.printStackTrace();  
                }                 
            }
        }
        else if(e.getSource()==bSaveAs){
            JFileChooser fc=new JFileChooser();    
            int action=fc.showSaveDialog(null);    
            if(action==JFileChooser.APPROVE_OPTION){    
                outputFile=fc.getSelectedFile();    
                String filepath=outputFile.getPath();   
                lSelectedLocation.setText(filepath); 
                lSelectedLocation.setVisible(true);
            }
        }
        else if(e.getSource()==bEncrypt){
            try{
                PDDocument document=PDDocument.load(inputFile);
                AccessPermission ap=new AccessPermission();
                StandardProtectionPolicy spp=new StandardProtectionPolicy(tfPassword.getText(), tfPassword.getText(), ap);
                spp.setEncryptionKeyLength(128);
                spp.setPermissions(ap);
                document.protect(spp);
                document.save(outputFile.getAbsolutePath());
                document.close();
            }
            catch(InvalidPasswordException ee){
                lWrongPassword.setText("Wrong Password");
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
}
