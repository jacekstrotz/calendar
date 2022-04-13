/* Author: Jacek Strotz
 *
 * Purpose: This class parses a simple format (<key:val><key2:val2>)
 * using a simple method. Unfortunately, Java does not support ArrayList[],
 * which would have allowed me to be able to combine keys and values into one
 * explicit variable. 
 */
package Data;

import java.util.ArrayList;

public class SpecialDate {
    private ArrayList<String> keys = new ArrayList<String>(), 
            values = new ArrayList<String>();
    
    private int day, month, year;
    private String label, detail;
    
    public SpecialDate(String s) {
        parse(s);
        
        day = Integer.parseInt(getKeyVal("day"));
        month = Integer.parseInt(getKeyVal("month"));
        
        year = keys.contains("year") ? Integer.parseInt(getKeyVal("year")) : -1;
        
        label = getKeyVal("label");
        detail = getKeyVal("detail");
    }
    
    public void parse(String s) {
        ArrayList<Integer[]> tags = new ArrayList<Integer[]>();
        keys.clear();
        values.clear();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '<')
                for (int j = i; j < s.length(); j++)
                    if (s.charAt(j) == ':') {
                        tags.add(new Integer[]{i+1, j});
                        break;
                    }
        }
        
        for (int i = 0; i < tags.size(); i++) {
            keys.add(s.substring(tags.get(i)[0], tags.get(i)[1]));
            values.add(s.substring(tags.get(i)[1]+1, s.indexOf(">", tags.get(i)[1])).trim());
        }
    }
    
    public String getKeyVal(String k) {
        int idx = keys.indexOf(k);
        return values.get(idx);
    }
    
    public int getDay() {
        return day;
    }
    
    public int getMonth() {
        return month;
    }
    
    public int getYear() {
        return year;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public String toString() {
        return month+"/"+day+"/"+(year == -1 ? "" : year) +" - "+label+" ("+detail+")";
    }
}
