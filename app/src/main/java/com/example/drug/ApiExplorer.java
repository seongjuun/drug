package com.example.drug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class ApiClient {

    private static String ApiExplorer(String urlBuilder) throws IOException {
        /*URL*/
        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb);
        return sb.toString(); // 반환할 문자열
    }
    public static String drugNameApi(String drugName) throws IOException {
        String urlBuilder = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList" + "?" + URLEncoder.encode("serviceKey", "UTF-8") +
                "=PfD2%2BbYHcC9WsY%2BH5xYpt9Tj39TG%2B3AVSkpfdygTUCxTNEj3mpWXeG5hEa4p7uSwN10Xdy%2BFZIBlvsdKtjTxAQ%3D%3D" + /*Service Key*/
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*페이지번호*/
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8") + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode(drugName, "UTF-8") + /*제품명*/
                "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*응답데이터 형식(xml/json) Default:xml*/
        return ApiExplorer(urlBuilder);
    }
    public static String drugInfoApi(String drugName) throws IOException {
        String urlBuilder = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList" + "?" + URLEncoder.encode("serviceKey", "UTF-8") +
                "=PfD2%2BbYHcC9WsY%2BH5xYpt9Tj39TG%2B3AVSkpfdygTUCxTNEj3mpWXeG5hEa4p7uSwN10Xdy%2BFZIBlvsdKtjTxAQ%3D%3D" + /*Service Key*/
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*페이지번호*/
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode(drugName, "UTF-8") + /*제품명*/
                "&" + URLEncoder.encode("efcyQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약의 효능은 무엇입니까?*/
                "&" + URLEncoder.encode("useMethodQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약은 어떻게 사용합니까?*/
                "&" + URLEncoder.encode("atpnWarnQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약을 사용하기 전에 반드시 알아야 할 내용은 무엇입니까?*/
                "&" + URLEncoder.encode("atpnQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약의 사용상 주의사항은 무엇입니까?*/
                "&" + URLEncoder.encode("intrcQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약을 사용하는 동안 주의해야 할 약 또는 음식은 무엇입니까?*/
                "&" + URLEncoder.encode("seQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약은 어떤 이상반응이 나타날 수 있습니까?*/
                "&" + URLEncoder.encode("depositMethodQesitm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8") +  /*이 약은 어떻게 보관해야 합니까?*/
                "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*응답데이터 형식(xml/json) Default:xml*/
        return ApiExplorer(urlBuilder);
    }
}
