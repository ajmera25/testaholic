package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class FileUtils {

	//private String fileName = "";
	
	public String readFile(String fileName) throws IOException {
		String fileContent = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/test/resources/" + fileName));
			//String line ="";
			while ((br.readLine()) != null) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			br.close();
		}
		return fileContent;
	} 
	
	public void writeToFile(String filePath,String fileContent){
			try (FileWriter file = new FileWriter(filePath)){
				file.write(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createJSONFile(String data) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		String fileName = "data_" + timeStampMillis+ ".json" ;
		String filePath = System.getProperty("user.dir") + "//src//test//resources//testdata//" + fileName;
		try {
			Files.write(Paths.get(filePath), data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

}
