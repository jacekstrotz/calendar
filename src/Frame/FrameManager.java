
package Frame;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 *
 * @author Jim Klayder spring 2022
 */
public class FrameManager {
    
    private static JFrame startFrame;
    private static Dimension minimumDimension; 
    private static ArrayList<JFrame> frameList;
     
    public FrameManager(JFrame startFrame) {
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        
        this.startFrame = startFrame; 
        minimumDimension = new Dimension(startFrame.getWidth(), startFrame.getHeight());
        
        JFrame tempFrame;
        frameList = new ArrayList<JFrame>();
        
        addFrameToFrameList(startFrame, "Main");
        addFrameToFrameList(new FrameAbout(), "FrameAbout");
        addFrameToFrameList(new FrameData(), "FrameData");
        addFrameToFrameList(new FrameDataModel(), "FrameDataModel");
        
        addFrameToFrameList(new Level.FrameLevel0(), "FrameLevel0");
        addFrameToFrameList(new Level.FrameLevel1(), "FrameLevel1");
        addFrameToFrameList(new Level.FrameLevel2(), "FrameLevel2");
        addFrameToFrameList(new Level.FrameLevel3(), "FrameLevel3");
        addFrameToFrameList(new Level.FrameLevel4(), "FrameLevel4");
        
    }
    
    public static void addFrameToFrameList(JFrame theFrame, String theName) {
        JFrame tempFrame = theFrame;
        tempFrame.setName(theName);
        
        //tempFrame.setMinimumSize(minimumDimension);
        //tempFrame.setMaximumSize(minimumDimension);
        
        frameList.add(tempFrame);
    }
    
    public static void addFrameToFrameList(JFrame theFrame, String theName, Dimension dim) {
        JFrame tempFrame = theFrame;
        tempFrame.setName(theName);
        
        //tempFrame.setMinimumSize(dim);
        //tempFrame.setMaximumSize(dim);
        
        frameList.add(tempFrame);
    }
    
    public static void showAllFrameNames() {
        for (int i = 0; i < frameList.size(); i++) {
            System.out.print(" "+i+".   ");
            System.out.println("    frameList.get("+i+").getName()  "+frameList.get(i).getName());
        }
    }
    
    public static String getAllFrameNames() {
        String answer = new String();
        for (int i = 0; i < frameList.size(); i++)
            answer += " "+i+".   "+frameList.get(i).getName()+"\n";

        return answer;
    }
       
    public static void displayFrame(JFrame oldFrame, String nameOfNewFrame) {
        boolean newFrameFound = false;
        
        for (int i = 0; i < frameList.size(); i++) {
            if (nameOfNewFrame.equals(frameList.get(i).getName())) {
                newFrameFound = true;
                
                frameList.get(i).setLocation(oldFrame.getX(), oldFrame.getY());

                frameList.get(i).setVisible(true);
                oldFrame.setVisible(false);                
                
                System.out.println("in FrameManager.displayFrame      -----    "+frameList.get(i).getName());
                break;
            }
        }
        
        if (newFrameFound == false) {
            
            System.out.println("\n\nERROR: in Utility.displayFrame could not find '"+ nameOfNewFrame +"'\n\n");

            JOptionPane.showMessageDialog(oldFrame,
                "Could not find '"+ nameOfNewFrame +"' \nin FrameManager constructor.",
                "Missing Frame Name",
                JOptionPane.INFORMATION_MESSAGE);
            
            // many more JOptionPane options csn be found at:
            // https://alvinalexander.com/java/joptionpane-showmessagedialog-examples-1/
            // https://alvinalexander.com/java/joptionpane-showmessagedialog-examples-2/
        
        }
    }    
}
