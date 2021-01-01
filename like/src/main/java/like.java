import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

class Like {
   
    private static String PAYLOAD = "payload";
    private static String DATA = "data";
    private static String ID = "id";
    private static String NAME = "name";
    private static String PROFILE = "profile";
    private static Integer LIMIT = 100;
    private static Integer START_OFFSET = 8300;
    private static String SINGLE_SPACE = " ";
    private static List users = new ArrayList<String>();
    private static Integer id = START_OFFSET;

    public static void main(String[] args) {
        Integer offset = START_OFFSET;
        while (true){
            // get data
            String res = getData(offset, LIMIT);

            // parsing
            if (!parseNameandId(res)) break;

            offset += LIMIT;
        }
        saveFile();
    }

    public static void saveFile(){
        try {
            FileWriter myWriter = new FileWriter("result2.txt");
            for(Object obj: users){
                myWriter.write(((String) obj) + "\r\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static Boolean parseNameandId(String res) {
        JSONObject object = new JSONObject(res);
        JSONArray data = (JSONArray) ((JSONObject) object.get(PAYLOAD)).get(DATA);

        Integer cntItems = data.length();
        for (Object obj: data) {
            JSONObject profile = (JSONObject) ((JSONObject) obj).get(PROFILE);
            String userInfo = (id += 1) + SINGLE_SPACE + profile.get(ID) + SINGLE_SPACE + profile.get(NAME);
            users.add(userInfo);
            System.out.println(userInfo);
        }
        return cntItems == LIMIT;
    }

    private static String getData(Integer offset, Integer limit){
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(String.format(URL, offset, limit));

            StringEntity input = new StringEntity(BODY);
            input.setContentType("raw");

            postRequest.setEntity(input);
            postRequest.addHeader("User-Agent", USER_AGENT);
            postRequest.addHeader("Content-Type", CONTENT_TYPE);
            //postRequest.setHeader("Content-Length", CONTENT_LENGTH);
            postRequest.addHeader("Cookie", COOKIE);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuilder strBuildOut = new StringBuilder();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                strBuildOut.append(output);
            }

            output = strBuildOut.toString().replace("for (;;);", "");

            httpClient.getConnectionManager().shutdown();

            return output;

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
}