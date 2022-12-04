package AQUSP7;

import java.io.*;

import org.json.simple.*;

public class JsonWriteAQUSP7 {
	
	public static void main(String[] args) {
		
		JSONObject student2 = new JSONObject();
		
    student2.put("hallgato", "hallgato neve");
    student2.put("neptunkod", asdasd
    student2.put("szak", "PTI");
        
    JSONArray studentList = new JSONArray();
    studentList.add(student2);
        
    System.out.println(studentList);
        
    FileWriter file = new FileWriter("JSONAQUSP7.json");
        
    file.writestudentList.toJSONString()); 
    file.flush();
	}

}
