package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AchsahSiri on 12/15/2015.
 */
public class USStateCode {

    private Map<String, String> stateCodeMapping;

    public Map<String, String> getStateCodeMapping() {
        return stateCodeMapping;
    }

    public void setStateCodeMapping(Map<String, String> stateCodeMapping) {
        this.stateCodeMapping = stateCodeMapping;
    }

    public USStateCode() {
        stateCodeMapping = new HashMap<>();
        stateCodeMapping.put("alabama","AL");
        stateCodeMapping.put("alaska","AK");
        stateCodeMapping.put("arizona","AZ");
        stateCodeMapping.put("arkansas","AR");
        stateCodeMapping.put("california","CA");
        stateCodeMapping.put("colorado","CO");
        stateCodeMapping.put("delaware","DE");
        stateCodeMapping.put("district of columbia","DC");
        stateCodeMapping.put("florida","FL");
        stateCodeMapping.put("georgia","GA");
        stateCodeMapping.put("hawaii","HI");
        stateCodeMapping.put("idaho","ID");
        stateCodeMapping.put("illinois","IL");
        stateCodeMapping.put("indiana","IN");
        stateCodeMapping.put("iowa","IA");
        stateCodeMapping.put("kansas","KS");
        stateCodeMapping.put("kentucky","KY");
        stateCodeMapping.put("louisiana","LA");
        stateCodeMapping.put("maine","ME");
        stateCodeMapping.put("maryland","MD");
        stateCodeMapping.put("massachusetts","MA");
        stateCodeMapping.put("michigan","MI");
        stateCodeMapping.put("minnesota","MN");
        stateCodeMapping.put("mississippi","MS");
        stateCodeMapping.put("missouri","MO");
        stateCodeMapping.put("montana","MT");
        stateCodeMapping.put("nebraska","NE");
        stateCodeMapping.put("nevada","NV");
        stateCodeMapping.put("new hampshire","NH");
        stateCodeMapping.put("new jersey","NJ");
        stateCodeMapping.put("new mexico","NM");
        stateCodeMapping.put("new york","NY");
        stateCodeMapping.put("north carolina","NC");
        stateCodeMapping.put("north dakota","ND");
        stateCodeMapping.put("ohio","OH");
        stateCodeMapping.put("oklahoma","OK");
        stateCodeMapping.put("oregon","OR");stateCodeMapping.put("Pennsylvania","PA");stateCodeMapping.put("Rhode Island","RI");
        stateCodeMapping.put("south carolina","SC");stateCodeMapping.put("South Dakota","SD");stateCodeMapping.put("Tennessee","TN");
        stateCodeMapping.put("texas","TX");stateCodeMapping.put("Utah","UT");stateCodeMapping.put("Vermont","VT");
        stateCodeMapping.put("virginia","VA");
        stateCodeMapping.put("washington","WA");
        stateCodeMapping.put("west virginia","WV");
        stateCodeMapping.put("wisconsin","WI");
        stateCodeMapping.put("wyoming","WY");
    }

}
