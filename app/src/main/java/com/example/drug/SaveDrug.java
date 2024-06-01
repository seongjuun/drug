package com.example.drug;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SaveDrug extends AppCompatActivity {

    private TextView drugNameTextView;  // 약물 이름 텍스트뷰
    private ImageView close; // 닫기 버튼
    ListView listView;  // 리스트뷰
    SQLiteHelper sqLiteHelper;  // SQLiteHelper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_drug);

        // 시스템 바 패딩 조정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 인텐트를 통해 약물 이름 가져오기
        Intent intent = getIntent();
        drugNameTextView = findViewById(R.id.saveDrugTitle);
        String drugName = intent.getStringExtra("drugName");
        if (drugName != null) {
            drugNameTextView.setText(drugName);
        } else {
            drugNameTextView.setText("약물 이름 없음");
        }
        Button save = findViewById(R.id.saveButton); // 저장 버튼
        save.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance(); // 오늘 날짜
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String todayString = dateFormat.format(today.getTime());
            System.out.println(todayString);
            sqLiteHelper = new SQLiteHelper(this);
            sqLiteHelper.insertDrug(drugNameTextView.getText().toString(), todayString);
            Intent saveIntent = new Intent(this, MainActivity.class);
            finishActivity(0);
            startActivity(saveIntent);
        });

        // 닫기 버튼 설정
        close = findViewById(R.id.back);
        close.setOnClickListener(v -> finish());



        listView = findViewById(R.id.setList); // 리스트뷰
        List<String> setList = new ArrayList<>(); // 세트 리스트
        setList.add("얼마나 자주 복용하십니까?얼마나 자주 복용하십니까?");
        setList.add("시간 및 용량 설정시간 및 용량 설정");
        setList.add("치료 기간을 설정하시겠습니까?");
        setList.add("설명을 추가하시겠습니까?");
        setList.add("약물 사진을 변경하시겠습까?");
        setList.add("알림 시간을 설정하시겠습니까?");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setList);
        listView.setAdapter(adapter);

    }
}
