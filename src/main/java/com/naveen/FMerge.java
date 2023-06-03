package com.naveen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import org.apache.pdfbox.multipdf.*;

public class FMerge extends JFrame implements ActionListener{
    JPanel pMerge=new JPanel();
    JPanel pFiles=new JPanel();

    //pMerge
    JButton bAddFiles=new JButton("Add files", null);
    JButton bMerge=new JButton("Merge", null);

    //pFiles
    JLabel lAddedFiles[];

    PDFMergerUtility PDFmerger=new PDFMergerUtility();
    FMerge(){
        setTitle("Merge PDFs");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // setSize(100, 100);
        setBounds(420, 250, 350, 300);

        //pMerge
        pMerge.setLayout(new BoxLayout(pMerge,BoxLayout.Y_AXIS));
        bAddFiles.addActionListener(this);
        bMerge.addActionListener(this);
        pMerge.add(bAddFiles);
        pMerge.add(bMerge);
        bMerge.setVisible(false);
        pMerge.setSize(20, 20);
        add(pMerge);
        pMerge.setVisible(true); 

        //pFiles 
        pFiles.setLayout(new BoxLayout(pFiles, BoxLayout.Y_AXIS));
        pFiles.setVisible(false);
        add(pFiles);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bAddFiles){
            JFileChooser fc=new JFileChooser();  
            fc.setMultiSelectionEnabled(true);  
            int action=fc.showOpenDialog(null);    
            if(action==JFileChooser.APPROVE_OPTION){    
                try{  
                    File file[]=fc.getSelectedFiles(); 
                    lAddedFiles =new JLabel[file.length];
                    for(int i=0;i<file.length;i++){   
                        PDFmerger.addSource(file[i]);
                        lAddedFiles[i]=new JLabel(file[i].getAbsolutePath().toString());
                        pFiles.add(lAddedFiles[i]);
                        lAddedFiles[i].setVisible(true);
                        // pMerge.setVisible(false); 
                        // pMerge.setVisible(true);
                    }
                    pFiles.setVisible(true);
                    bMerge.setVisible(true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();  
                }                 
            }
        }
        else if(e.getSource()==bMerge){
            // PDFmerger.setDestinationFileName("merged.pdf"); 

            JFileChooser fc=new JFileChooser();    
            int action=fc.showSaveDialog(null);    
            if(action==JFileChooser.APPROVE_OPTION){    
                File outputFile=fc.getSelectedFile();    
                PDFmerger.setDestinationFileName(outputFile.getAbsolutePath()); 
            }
            try{
            PDFmerger.mergeDocuments(null); 
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
}
