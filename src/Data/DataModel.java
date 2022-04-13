/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Frame.FrameData;
import Level.FrameLevel4;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class DataModel {
    public static MultiDate multiDate;
    
    public static void init() {
        if (FrameData.fileComboBox != null)
            MultiDate.file = FrameData.fileComboBox.getSelectedItem().toString();
        
        multiDate = new MultiDate();
    }
    
    public static String formatString(String s) {
        return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
    }
    
    public static String[] alphabetize(String s[]) {
        ArrayList<String> ret = new ArrayList<String>();
        for (int i = 0; i < s.length; i++)
            ret.add(s[i]);
        
        String[] s2 = new String[s.length];
        Collections.sort(ret);
        
        ret.toArray(s2);
        return s2;
    }
    
    public static int writeFile(String s, String f, boolean dir) {
        String d;
        if (!dir) {
            d = Path.of("").toAbsolutePath().toString() + File.separator 
                    + "output";

            (new File(d)).mkdir();
            
            f = d + File.separator + f + ".html";
        }
        
        try {
            File file = new File(f);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            
            return 2;
        }
        
        try {
            FileWriter myWriter = new FileWriter(f);
            
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            
            return 3;
        }
        return 0;
    }
    
    public static String printMonth(int m, int y) {
        String ret = "";
        
        ret += "Selected Month: " + formatString(LocalDate.of(y, m+1, 1).getMonth().toString());
        ret += "\nSelected Month Number: " + (m+1);
        ret += "\nSelected Year: " + y;
        
        var d = LocalDate.of(y, m+1, 1);
        
        ret += "\n\nFirst day of month: "+formatString(d.getDayOfWeek().toString());
        ret += "\nNumber of days: " + d.lengthOfMonth();
        
        return ret.stripTrailing();
    }
    
    public static String printYear(int y) {
        String ret = "";
        for (int i = 1; i <= 12; i++) {
            var d = LocalDate.of(y, i, 1);
            ret += "Number of days in " + formatString(d.getMonth().toString()) + ": "+ d.lengthOfMonth();
            ret += "\nFirst day of month: "+formatString(d.getDayOfWeek().toString())+"\n\n";
        }
        return ret.stripTrailing();
    }
    
    public static String formatCal(int y) {
        String ret = "Selected Year: " + y + "\n\n";
        for (int i = 1; i <= 12; i++) {
            var d = LocalDate.of(y, i, 1);
            
            ret += "Number of days in " + formatString(d.getMonth().toString()) + ": "+ d.lengthOfMonth();
            ret += "\nFirst day of month: "+ formatString(d.getDayOfWeek().toString());
            
            ret += "\nS   M   T   W   T   F   S\n";
            
            int iterator = 0,
                day = d.getDayOfWeek().getValue() == 7 ? 0 : d.getDayOfWeek().getValue();
            
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k <= 6; k++) {
                    if (iterator >= d.lengthOfMonth()) break;
                    else if (iterator == 0)
                        if (day != k)
                            continue;
                        else 
                            for (int m = 0; m < k; m++)
                                ret += "    ";
                   ret += ++iterator + (iterator > 9 ? "  " : "   ");
                }
                ret += "\n";
            }
            ret = ret.stripTrailing() + "\n\n";
        }
        return ret.stripTrailing();
    }
    
    public static String formatHTML(String s) {
        return "<html>\n<head>\n<title>Calendar</title></head><body><pre>\n\n" +
                s + "</pre></body></html>";
    }
    
    public static String returnDate(int y, int m, int d) {
        var ld = LocalDate.of(y, m, d);
        String ret = formatString(ld.getMonth().name()) + 
                " " + ld.getDayOfMonth() + ", " + 
                ld.getYear();
        
        for (SpecialDate date : DataModel.multiDate.dates)
            if (date.getDay() == d && date.getMonth() == m && 
                    ((date.getYear() == y || date.getYear() == -1) ) )
                ret += "<br>" + date.getLabel() + ": " + date.getDetail();
        
        return ret;
    }
    
    public static String formatSpecialCal(int y) {
        String ret = "Selected Year: " + y + "\n\n";
        for (int i = 1; i <= 12; i++) {
            var d = LocalDate.of(y, i, 1);
            
            ret += "Number of days in " + formatString(d.getMonth().toString()) + ": "+ d.lengthOfMonth();
            ret += "\nFirst day of month: "+ formatString(d.getDayOfWeek().toString());
            
            ret += "\nS   M   T   W   T   F   S\n";
            
            int iterator = 0,
                day = d.getDayOfWeek().getValue() == 7 ? 0 : d.getDayOfWeek().getValue();
            
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k <= 6; k++) {
                    if (iterator >= d.lengthOfMonth()) break;
                    else if (iterator == 0)
                        if (day != k)
                            continue;
                        else 
                            for (int m = 0; m < k; m++)
                                ret += "    ";
                    
                    ++iterator;
                    boolean linked = false;
                    String dateDir = Path.of("").toAbsolutePath().toString() + File.separator + 
                            "output/" + FrameLevel4.fileNameField.getText() + "/";
                    String dateDay = i + "_" + iterator + "_" + y + ".html";
                    String datePath = dateDir + dateDay;
                    
                    for (SpecialDate date : DataModel.multiDate.dates)
                        if (date.getDay() == iterator && date.getMonth() == i && 
                                (date.getYear() == -1 || date.getYear() == y) ) {
                            (new File(dateDir)).mkdir();
                            writeFile(returnDate(y, i, iterator), datePath, true);
                            linked = true;
                            break;
                    }
                    if (!linked) ret += iterator + (iterator > 9 ? "  " : "   ");
                    else ret += "<a href=\"" + FrameLevel4.fileNameField.getText() + "/" + dateDay + "\">" + iterator + "</a>" + (iterator > 9 ? "  " : "   ");
                }
                ret += "\n";
            }
            ret = ret.stripTrailing() + "\n\n";
        }
        return ret.stripTrailing();
    }
}
