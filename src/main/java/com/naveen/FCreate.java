package com.naveen;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.*;

public class FCreate extends JFrame implements ActionListener{

    JPanel pMain=new JPanel();
    JPanel pInfo=new JPanel();

    JButton bAddFiles=new JButton("Add files", null);
    JLabel lAddedFiles[];

    JButton bCreate =new JButton("Create PDF");

    JLabel lSelectedFiles =new JLabel("Selected Files");

    File files[];
    BufferedImage bim;

    FCreate(){
        setTitle("Create PDF");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // setSize(100, 100);
        setBounds(420, 250, 350, 300);

                //pMain
                pMain.setLayout(new BoxLayout(pMain,BoxLayout.Y_AXIS));
                bAddFiles.addActionListener(this);
                bCreate.addActionListener(this);
                pMain.add(bAddFiles);
                pMain.add(bCreate);
                bCreate.setVisible(false);
                pMain.setSize(20, 20);
                add(pMain);
                pMain.setVisible(true); 
        //pInfo
        pInfo.setLayout(new BoxLayout(pInfo,BoxLayout.Y_AXIS));

        pInfo.add(lSelectedFiles);
        pInfo.setVisible(false);
        add(pInfo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==bAddFiles){
            JFileChooser fc=new JFileChooser();  
            fc.setMultiSelectionEnabled(true);  
            int action=fc.showOpenDialog(null);    
            if(action==JFileChooser.APPROVE_OPTION){    
                try{  
                    files=fc.getSelectedFiles(); 
                    lAddedFiles =new JLabel[files.length];
                    for(int i=0;i<files.length;i++){   
                        lAddedFiles[i]=new JLabel(files[i].getAbsolutePath());
                        pInfo.add(lAddedFiles[i]);
                        lAddedFiles[i].setVisible(true);
                        // pMerge.setVisible(false); 
                        // pMerge.setVisible(true);
                    }
                    pInfo.setVisible(true);
                    bCreate.setVisible(true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();  
                }                 
            }
        }

        else if(e.getSource()==bCreate){
             
            try{
            PDDocument pdDocument = new PDDocument();
            PDPage page = null;
            PDPageContentStream contentStream;
            for (int i = 0; i < files.length; i++) {
                page = new PDPage(PDRectangle.LETTER);
                float height=PDRectangle.LETTER.getHeight();
                float width=PDRectangle.LETTER.getWidth();
                bim = ImageIO.read(files[i]);
                PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
                contentStream = new PDPageContentStream(pdDocument, page);
                float y1 = Math.max(0,(height- bim.getHeight())/2);
                float x1 = Math.max(0,(width - bim.getWidth())/2);
                float y2 = Math.min(height,(height+ bim.getHeight())/2);
                float x2 = Math.min(width,(width +bim.getWidth())/2);
                contentStream.drawImage(pdImage,x1,y1,x2-x1,y2-y1);
                contentStream.close();
                // page.getMediaBox().getHeight()
                pdDocument.addPage(page);
               }

               JFileChooser fc=new JFileChooser();    
               int action=fc.showSaveDialog(null);  
               File outputFile;  
               if(action==JFileChooser.APPROVE_OPTION){    
                   outputFile=fc.getSelectedFile();    
                   pdDocument.save(outputFile.getAbsolutePath());
                   pdDocument.close();
               }
               System.gc();
               
            }
              catch (Exception ee) {
               ee.printStackTrace();
              }
        }
    }
}
