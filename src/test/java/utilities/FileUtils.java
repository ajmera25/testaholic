package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import javax.imageio.ImageIO;

public class FileUtils {

	public int getFileSizeInKb(String filePath) {
		File file = new File(filePath);
		int fileSize = 0;
		if (file.exists()) {
			fileSize = (int) file.length() / 1024 ;
			System.out.println(fileSize);
		}
		return fileSize;
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
	
    public void downloadImage(String src, String platform, int fileName) {
        try {
        BufferedImage bufferedImage = ImageIO.read(new URL(src));
        String filePath = System.getProperty("user.dir") + "/src/test/resources/" + platform + "Photos/" + fileName + ".jpg";
        File outputfile = new File(filePath);
        ImageIO.write(bufferedImage, "jpeg", outputfile);
        }catch (IOException e) {
        	e.printStackTrace();
        }
    }


}
