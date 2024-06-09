package com.example.drug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class ApiClient { // API 요청 클래스

    private static String ApiExplorer(String urlBuilder) throws IOException {    // API 요청 메소드

        URL url = new URL(urlBuilder);  // URL 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // URL 연결
        conn.setRequestMethod("GET");   // GET 방식으로 요청
        conn.setRequestProperty("Content-type", "application/json");    // 요청 헤더 설정
        System.out.println("Response code: " + conn.getResponseCode());  // 응답 코드 출력
        BufferedReader rd;  // 버퍼 리더 객체 생성
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {   // 응답 코드가 200 ~ 300 사이일 때
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  // 버퍼 리더 객체 생성
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));  // 에러 스트림으로 버퍼 리더 객체 생성
        }
        StringBuilder sb = new StringBuilder(); // 문자열 객체 생성
        String line;
        while ((line = rd.readLine()) != null) {    // 읽어온 데이터가 null이 아닐 때까지
            sb.append(line);    // 문자열에 추가
        }
        rd.close(); // 버퍼 리더 닫기
        conn.disconnect();  // 연결 종료
        System.out.println(sb); // 응답 데이터 출력
        return sb.toString(); // 반환할 문자열
    }
    public static String drugNameApi(String drugName) throws IOException {  // 약품명 검색 URL
        String urlBuilder = "https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList" + "?" + URLEncoder.encode("serviceKey", "UTF-8") +
                "=PfD2%2BbYHcC9WsY%2BH5xYpt9Tj39TG%2B3AVSkpfdygTUCxTNEj3mpWXeG5hEa4p7uSwN10Xdy%2BFZIBlvsdKtjTxAQ%3D%3D" + /*Service Key*/
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*페이지번호*/
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("50", "UTF-8") + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode(drugName, "UTF-8") + /*제품명*/
                "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*응답데이터 형식(xml/json) Default:xml*/
        return ApiExplorer(urlBuilder);
    }
    public static String drugInfoApi(String drugName) throws IOException {  // 약품 정보 검색 URL
        String urlBuilder = "https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList" + "?" + URLEncoder.encode("serviceKey", "UTF-8") +
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
