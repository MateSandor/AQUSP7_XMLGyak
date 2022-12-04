package AQUSP7;

import java.io.*;

import org.json.simple.*;

public class JsonReadAQUSP7 {
	
	public static void main(String[] args) {
		
		JSONParser parser = new parser();

		FileReader parse = new FileReader("JSONAQUSP7.json");
		
		 Object obj = parser.parse(parse);
		 
         System.out.println(obj);
		
	}
  
}
