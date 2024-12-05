
package Library;
import javax.swing.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame implements ActionListener{
    JButton image;
    ImageIcon imageicon;
    
   public WelcomeFrame(){
        this.setLayout(null);
        this.setTitle("LBS BULSU BUSTOS");
        this.setSize(1366,768);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        try{
            imageicon = new ImageIcon(getClass().getResource("LibraryCover.jpg"));
            image = new JButton(imageicon);
            this.add(image);
            image.setBounds(0, 0, 1366, 768);
            image.addActionListener(this);
            
        } catch(Exception e){
            System.out.println("Image cannot found");
        }
    }
    
    public static void main(String[] args) {
      WelcomeFrame wf = new WelcomeFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         LogInFrame login = new LogInFrame();
         
         if(e.getSource().equals(image)){
             login.setVisible(true);
             this.dispose();
         }
    }
}

