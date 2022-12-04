package AQUSP7;

import java.io.*;

import org.json.simple.*;

public class ObjectAQUSP7 {
  
	public static void main(String[] args) {
    
		JSONObject j = new JSONObject(); 
    
		j.put("hallgato", "hallgato neve");
		j.put("neptunkod", "asdasd");
		j.put("szak", "PTI");
		System.out.println(j.toString());
	
	}	
 
}
