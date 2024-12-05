
package StudentIDScanner;

import Library.Dashboard_Inventory;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class QRScanner extends JFrame implements Runnable,ThreadFactory, ActionListener{
    public String formattedText;
    private Dashboard_Inventory dashboard;
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    
     JPanel panelcam = new JPanel();
     JLabel resultlabel = new JLabel("STUDENT QR ");
     JButton savebtn = new JButton("Save Student");
     public JTextArea resultField = new JTextArea();
    
    public QRScanner(Dashboard_Inventory dashboard){
        this.dashboard = dashboard;
        initWebcam();
        
        this.setLayout(null);
        this.setTitle("ID Scanner");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panelcam.setBounds(50, 50, 500,300);
        panelcam.setBorder(new LineBorder(Color.BLACK,1));
        add(panelcam);
        
        resultlabel.setBounds(50, 380, 100, 25);
        add(resultlabel);
        
        resultField.setBounds(50, 420, 500, 50);
        add(resultField);
        
        savebtn.setBounds(50, 480, 150, 30);
        add(savebtn);
        savebtn.addActionListener(this);
        
    }
    
    private void initWebcam(){
       Dimension size = WebcamResolution.VGA.getSize();
       webcam = Webcam.getWebcams().get(0);
       webcam.setViewSize(size);
       
       panel = new WebcamPanel(webcam);
       panel.setPreferredSize(size);
       panel.setFPSDisplayed(true);
       
       panelcam.add(panel);
       
       executor.execute(this);
    }

    @Override
    public void run() {
        
        do{
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(QRScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Result result = null;
            BufferedImage image = null;
            
            if(webcam.isOpen()){
                if((image = webcam.getImage()) == null){
                    continue;
                }
            }
            
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            
            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                Logger.getLogger(QRScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(result !=null){
                resultField.setText(result.getText());
            }
            
        }while(true);
    }

    @Override
    public Thread newThread(Runnable r) {
       
        Thread t = new Thread(r,"My Thread");
        t.setDaemon(true);
        return t;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(savebtn)){
            String QRValue = resultField.getText();
            
            if (!QRValue.isEmpty()) {
                 dashboard.scannedValue.setText(QRValue);
                 formattedText = QRValue.replace("Student No.:", "Student No.:\n")
                        .replace("Full Name:", "\nFull Name:\n")
                        .replace("Program:", "\nProgram:\n");
                JOptionPane.showMessageDialog(this, "Value sent to Student Details Form!", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No QR code scanned!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

  }

