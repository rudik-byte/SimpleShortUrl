package com.rudik.SimpleShortURL_bot;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ShortenURL {

    private static final String IS_GD = "https://is.gd/create.php?format=simple&url=";

    private static final int HTTP_STATUS_OK = 200;

    public String getShortURL(String longURL) {
        String url = IS_GD + longURL;

        String resultShortURL = "";
        int responseCode = 0;
        HttpsURLConnection connection;
        URL urlObject;

        try {
            urlObject = new URL(url);
            connection = (HttpsURLConnection) urlObject.openConnection();
            //connection.setRequestMethod("GET"); get запрос будет по умолчанию
            connection.setRequestProperty("User-Agent", "Mozilla/5.0\"");

            responseCode = connection.getResponseCode();
            if (responseCode == HTTP_STATUS_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                StringBuilder response = new StringBuilder();

                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }

                resultShortURL = response.toString();
                bufferedReader.close();

            } else {

                System.err.println("ResponseCode: " + connection.getResponseCode());
                System.err.println("ResponseMessage: " + connection.getResponseMessage());
                resultShortURL = "Unable to shorten URL link. Please try again.";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultShortURL;
    }

}
