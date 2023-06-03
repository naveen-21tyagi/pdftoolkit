package com.naveen;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
public class App implements ActionListener
{
    JFrame F=new JFrame("PDFToolkit", null);
    JButton mergeOB=new JButton("Merge PDFs", null);




    JButton removePasswordOB=new JButton("Remove Password", null);

    JButton bEncryptPDF=new JButton("Encrypt PDF");

    JButton bCompress=new JButton("Compress");

    JButton bCreate = new JButton("Create PDF" );


    JLabel lAuthor = new JLabel("created by naveen_21tyagi:)",JLabel.RIGHT);
    File file;
    JLabel l=new JLabel();

    JFrame FMerge;
    App(){
        mergeOB.addActionListener(this);
        removePasswordOB.addActionListener(this);
        bEncryptPDF.addActionListener(this);
        bCompress.addActionListener(this);
        bCreate.addActionListener(this);

        // F.add(null);
        F.add(bCreate);
        F.add(mergeOB);
        F.add(bEncryptPDF);
        F.add(removePasswordOB);
        F.add(bCompress);
        F.add(lAuthor);

        F.setBounds(400, 250, 250, 200);
        F.setLayout(new GridLayout(6,1,5,10));
        F.setVisible(true);
        F.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==mergeOB){
            // p.setVisible(false);
            // F.setVisible(false);
            // JPanel pMerge=new PMerge();
            // F.add(pMerge);
            // pMerge.setVisible(true);
            new FMerge().setVisible(true);;
        }
        

        else if(e.getSource()==removePasswordOB){
            // p.setVisible(false);
            new FDecrypt().setVisible(true);

        }
        else if(e.getSource()==bEncryptPDF){
            new FEncrypt().setVisible(true);
        }
        else if(e.getSource()==bCompress){
            new FCompress().setVisible(true);
        }
        else if(e.getSource()==bCreate){
            new FCreate().setVisible(true);
        }
    }
    public static void main(String args[])throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new App();
    }
}
