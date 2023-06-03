package com.naveen;


import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
 
  
public class FCompress extends JFrame implements ActionListener{
    JPanel pMain =new JPanel();
    JPanel pInfo=new JPanel();
     
    JButton bSelectFile= new JButton("Select File", null);
    JLabel lDPI =new JLabel("Enter DPI");
    JTextField tfDPI=new JTextField();

    JButton bCompress=new JButton("Compress", null);

    File inputFile;
    File outputFile;

    JLabel lSelectedFile1 =new JLabel("Selected File");
    JLabel lSelectedFile2= new JLabel(); 
    JLabel lSelectLocation=new JLabel("Choose Location to save");
    JLabel lSelectedLocation=new JLabel();
    JButton bSaveAs=new JButton("Save As");

    JLabel lCompleted=new JLabel();

    BufferedImage bim;
    PDImageXObject pdImage ;
    PDPageContentStream contentStream ;


 public FCompress() {
  // setVisible(true);
  setTitle("Compress PDF");
  setLayout(new FlowLayout());
  setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  // setSize(100, 100);
  setBounds(420, 250, 350, 300);
  


  //pMain
  bSelectFile.addActionListener(this);
  bCompress.addActionListener(this);

  tfDPI.setSize(10, 10);
  pMain.add(bSelectFile);
  pMain.add(lDPI);
  pMain.add(tfDPI);
  pMain.add(bCompress);
  pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
  pMain.setSize(20, 20);
  pMain.add(lCompleted);

  add(pMain);
  // pMain.setVisible(true);
  lDPI.setVisible(false);
  tfDPI.setVisible(false);
  bCompress.setVisible(false);

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
 public void actionPerformed(ActionEvent e){
    if(e.getSource()==bSelectFile){
        JFileChooser fc=new JFileChooser();  
        int action=fc.showOpenDialog(null);    
        if(action==JFileChooser.APPROVE_OPTION){    
            try{  
                inputFile=fc.getSelectedFile(); 
                lDPI.setVisible(true);
                tfDPI.setVisible(true);
                bCompress.setVisible(true);
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
    else if(e.getSource()==bCompress){
 
        try {
 
            PDDocument pdDocument = new PDDocument();
            PDDocument oDocument = PDDocument.load(inputFile);
            PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
            int numberOfPages = oDocument.getNumberOfPages();
            PDPage page = null;
          
            for (int i = 0; i < numberOfPages; i++) {
             page = new PDPage(PDRectangle.LETTER);
             bim = pdfRenderer.renderImageWithDPI(i, Integer.parseInt(tfDPI.getText()), ImageType.RGB);
             pdImage = JPEGFactory.createFromImage(pdDocument, bim);
             contentStream = new PDPageContentStream(pdDocument, page);
             float newHeight = PDRectangle.LETTER.getHeight();
             float newWidth = PDRectangle.LETTER.getWidth();
             contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
             contentStream.close();
          
             pdDocument.addPage(page);
            }
          
            pdDocument.save(outputFile.getAbsolutePath());
            pdDocument.close();
            oDocument.close();
            lCompleted.setText("Completed");
            System.gc();
            
          
           } catch (Exception ee) {
            ee.printStackTrace();
           }
    }
 }
}