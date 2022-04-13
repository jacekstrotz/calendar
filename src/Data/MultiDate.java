/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Frame.FrameData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 *
 * @author strotz
 */
public class MultiDate {
    public ArrayList<SpecialDate> dates = new ArrayList<SpecialDate>();
    private String output = "";
    
    public static String file = "";
    
    public MultiDate() {
        if (file.equals("")) return;
        output = "";
        dates.clear();
        
        ArrayList<String> lines = new ArrayList<>();
        Path f = Path.of(Path.of("").toAbsolutePath().toString(), "input", file + ".dates");
        
        try {
            output = "\n"+Files.readString(f)+"\n";
            for (int i = 0; i < output.length(); i++)
                if (output.charAt(i) == '\n' && i != output.length()-1)
                    lines.add(output.substring(i+1, output.indexOf("\n", i+1)).trim());
            
            for (int i = 0; i < lines.size(); i++)
                if (!lines.get(i).isBlank()) dates.add(new SpecialDate(lines.get(i)));
                else lines.remove(i);
            
        } catch (IOException e) {
            System.out.println("An error occured");
            System.out.println(e.toString());
        }
    }
    
    public MultiDate(String s) {
        ArrayList<String> lines = new ArrayList<>();
        
        output = "\n"+s+"\n";
        for (int i = 0; i < output.length(); i++)
            if (output.charAt(i) == '\n' && i != output.length()-1)
                lines.add(output.substring(i+1, output.indexOf("\n", i+1)).trim());

        for (int i = 0; i < lines.size(); i++)
            if (!lines.get(i).isBlank()) dates.add(new SpecialDate(lines.get(i)));
            else lines.remove(i);
    }
    
    public String getOutput() {
        return output.trim();
    }
}
