package Gemini;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HandleInsuranceForm {

    public static String getInsuranceRecommendation(String age,String income,String marital,String employement,String health,
                                                    String risk,String financial,String coverage,String geographic,String assets,String liabilities,String apiKey) {

        try {
            String apiUrl = "https://api.openai.com/v1/chat/completions";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(apiUrl);
            postRequest.addHeader("Authorization", "Bearer " + apiKey);
            postRequest.addHeader("Content-Type", "application/json");

            // Construct the prompt
            String prompt = "What is your age? " + age +
                    " What is your annual income? " + income +
                    " What is your marital status? " + marital +
                    " What is your employment status? " + employement +
                    " What is your Health Status? " + health +
                    " What is your Risk Tolerance? " + risk +
                    " What are your financial goals? " + financial +
                    " Preferred Coverage Level? " + coverage +
                    " What is your geographic location? " + geographic +
                    " Total value of your assets (in currency): " + assets +
                    " Total value of your liabilities (in currency): " + liabilities +

                    " According to the questions above, just answer me with one of these insurance types " +
                    "and give me a brief description why";

            JSONObject requestBody = new JSONObject();
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.put(message);

            requestBody.put("model", "gpt-3.5-turbo-0125");
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 2048);

            StringEntity requestEntity = new StringEntity(requestBody.toString());
            postRequest.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(postRequest);

            StringBuilder responseBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
            JSONArray choicesArray = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            String generatedText = firstChoice.getJSONObject("message").getString("content");

            httpClient.close();

            return generatedText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating insurance recommendation";
        }
    }}




