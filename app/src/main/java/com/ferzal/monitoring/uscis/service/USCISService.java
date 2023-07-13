package com.ferzal.monitoring.uscis.service;

import static com.ferzal.monitoring.uscis.util.Constant.EMPTY;

import com.ferzal.monitoring.uscis.dto.CaseStatusResponse;
import com.ferzal.monitoring.uscis.dto.CaseStatusResponseBody;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class USCISService {
    private static final String API_URL = "https://egov.uscis.gov/csol-api/case-statuses/";

    public CaseStatusResponse getCaseInformation(String caseNumber) {
        try {
            return makeHttpRequest(caseNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return new CaseStatusResponse();
        }
    }

    private CaseStatusResponse makeHttpRequest(String caseNumber) throws IOException {
        URL url = new URL(String.join(EMPTY,API_URL,caseNumber));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String stringResponse = response.toString();
            Gson gson = new Gson();
            return gson.fromJson(stringResponse, CaseStatusResponse.class);
        } else {
            throw new IOException("HTTP request failed with response code: " + responseCode);
        }
    }
}
