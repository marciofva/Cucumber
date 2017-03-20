package com.propertyFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
	
    Properties properties = new Properties();
    InputStream inputStream;
    

	 private void loadFile() {
	        try {
	            inputStream = new FileInputStream("src/config.properties");
	            properties.load(inputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public String readFile(String key) {
	        return properties.getProperty(key);
	    }
}
