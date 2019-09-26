package core;


import okhttp3.*;
import utilities.FileUtils;
import utilities.JsonTemplate;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APIHelper {

	private static final MediaType MEDIA_TYPE_TXT = MediaType.parse("application/json");
	private final OkHttpClient client = new OkHttpClient();

	public String uploadFile(String fileName) {
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/testdata/" + fileName);
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("txt", file.getName(),
						RequestBody.create(MEDIA_TYPE_TXT, file))
				.build();
		Request request = new Request.Builder()
				.header("Content-Type", "multipart/form-data")
				.header("Accept", "text/html")
				.url("https://cgi-lib.berkeley.edu/ex/fup.cgi")
//				.url(BaseTest.Config.getProperty("hostName"))
				.post(requestBody)
				.build();
		String responseData = null;
		Response response = null;
		try {
			response = client.newCall(request).execute();
			responseData = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseData;
	}


	public static void main(String[] args) {
		String teamName = "Testaholic";
		Map<String, Integer> photos = new HashMap<String, Integer>();
		photos.put("Album 1",123);
		photos.put("Album 2",12);
		String reqPath = new FileUtils().createJSONFile(new JsonTemplate(teamName, photos).getJsonString());
		APIHelper apiHelper = new APIHelper();
		String response = apiHelper.uploadFile(reqPath);
		System.out.println(response);
	}
}
