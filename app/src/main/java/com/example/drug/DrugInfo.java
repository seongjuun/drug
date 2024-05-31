package com.example.drug;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DrugInfo extends AppCompatActivity {

    TextView drugNameTextView;  // 약물 이름 텍스트뷰
    ImageView back; // 닫기 버튼
    TextView efcyQesitm, useMethodQesitm, atpnWarnQesitm, atpnQesitm, intrcQesitm, seQesitm, depositMethodQesitm; // 효능, 사용법, 주의사항 경고, 주의사항, 상호작용, 부작용, 보관법
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drug_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drugNameTextView = findViewById(R.id.DrugInfoTitle);
        String drugName = getIntent().getStringExtra("drugName");
        drugNameTextView.setText(drugName);
        back = findViewById(R.id.back); // 닫기 버튼
        back.setOnClickListener(v -> finish()); // 닫기 버튼 클릭시 액티비티 종료

        efcyQesitm = findViewById(R.id.efcyQesitm); // 효능
        useMethodQesitm = findViewById(R.id.useMethodQesitm);   // 사용법
        atpnWarnQesitm = findViewById(R.id.atpnWarnQesitm);  // 주의사항 경고
        atpnQesitm = findViewById(R.id.atpnQesitm); //  주의사항
        intrcQesitm = findViewById(R.id.intrcQesitm);   // 상호작용
        seQesitm = findViewById(R.id.seQesitm); // 부작용
        depositMethodQesitm = findViewById(R.id.depositMethodQesitm);   // 보관법

        new Thread(() -> {
            try {
                String drugInfoContent = ApiClient.drugInfoApi(drugName); // 약물 정보 가져오기
                runOnUiThread(() -> xmlParsing(drugInfoContent));   //xml 파싱
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    protected void xmlParsing(String drugItems) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //문서 빌더 팩토리
            DocumentBuilder builder = factory.newDocumentBuilder(); //문서 빌더
            Document doc = builder.parse(new ByteArrayInputStream(drugItems.getBytes("UTF-8"))); //문서 파싱
            doc.getDocumentElement().normalize(); // 문서 정규화

            efcyQesitm.setText(doc.getElementsByTagName("efcyQesitm").item(0).getTextContent()); // 효능
            useMethodQesitm.setText(doc.getElementsByTagName("useMethodQesitm").item(0).getTextContent()); // 사용법
            atpnWarnQesitm.setText(doc.getElementsByTagName("atpnWarnQesitm").item(0).getTextContent()); // 주의사항 경고
            atpnQesitm.setText(doc.getElementsByTagName("atpnQesitm").item(0).getTextContent()); // 주의사항
            intrcQesitm.setText(doc.getElementsByTagName("intrcQesitm").item(0).getTextContent()); // 상호작용
            seQesitm.setText(doc.getElementsByTagName("seQesitm").item(0).getTextContent()); // 부작용
            depositMethodQesitm.setText(doc.getElementsByTagName("depositMethodQesitm").item(0).getTextContent()); // 보관법

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}