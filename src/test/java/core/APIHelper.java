package core;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
				.url(BaseTest.Config.getProperty("hostName"))
				.post(requestBody)
				.build();
		Response response = null;
		try {
			response = client.newCall(request).execute();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.body().string();
	}

}
