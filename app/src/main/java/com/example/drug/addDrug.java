package com.example.drug;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class addDrug extends AppCompatActivity { //약물 추가 액티비티

    EditText drugName; //약물 이름
    ImageView clear;    //지우기 버튼
    RecyclerView recyclerView;  //리사이클러뷰
    DrugAdapter adapter;    //어댑터
    ArrayList<String> drugList; //약물 리스트
    String drugItems;   //약물 아이템
    Handler handler;    //핸들러
    Runnable runnable;  //실행 가능한 클래스

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {    //액티비티 시작시
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_drug);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // 상태바 높이만큼 패딩 설정
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // 시스템 바
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // 패딩 설정
            return insets;  // 반환
        }); // 상태바 높이만큼 패딩 설정
        ImageView close = findViewById(R.id.back);
        close.setOnClickListener(v -> finish());  //닫기 버튼 클릭시 액티비티 종료


        recyclerView = findViewById(R.id.recyclerView); //리사이클러뷰
        recyclerView.setLayoutManager(new LinearLayoutManager(this));   //리사이클러뷰 레이아웃 설정

        drugList = new ArrayList<>();   //약물 리스트
        adapter = new DrugAdapter(drugList);    //어댑터
        recyclerView.setAdapter(adapter);   //리사이클러뷰에 어댑터 설정

        drugName = findViewById(R.id.drugName); //약물 이름
        handler = new Handler();    //핸들러

        drugName.setOnEditorActionListener((v, actionId, event) -> {   //약물 이름 입력 후 엔터키 누르면
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                Intent intent = new Intent(v.getContext(), SaveDrug.class); //저장된 약물 액티비티로 이동
                intent.putExtra("drugName", drugName.getText().toString()); //약물 이름 전달
                v.getContext().startActivity(intent);   //액티비티 시작
                return true;
            }
            return false;
        });

        drugName.addTextChangedListener(new TextWatcher() {   //약물 이름 텍스트 변경시
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 텍스트 변경 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경 중
                if (runnable != null) {
                    handler.removeCallbacks(runnable);  //핸들러 콜백 제거
                }
                runnable = () -> {
                    new Thread(() -> {  //스레드
                        try {
                            drugItems = ApiClient.drugNameApi(drugName.getText().toString());   //오픈 api 호출
                            runOnUiThread(() -> xmlParsing(drugItems));   //xml 파싱
                        } catch (IOException e) {
                            throw new RuntimeException(e);  //예외 처리
                        }
                    }).start();
                };
                handler.postDelayed(runnable, 2000);   //2초 뒤 실행
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트 변경 후
            }
        });

        clear = findViewById(R.id.clear);   //지우기 버튼
        clear.setOnClickListener(v -> {  //지우기 버튼 클릭시
            drugName.setText("");
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(this));    //리사이클러뷰 아이템 구분선 추가
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void xmlParsing(String xml) {   //xml 파싱
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");
            drugList.clear();
            for (int i = 0; i < nodeList.getLength(); i++) {    //노드 리스트 길이만큼 반복
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList name = element.getElementsByTagName("itemName");   //제품명
                    if (name.getLength() > 0) {
                        String itemName = name.item(0).getTextContent();
                        drugList.add(itemName); //약물 리스트에 추가
                    }
                }
            }
            recyclerView.setAdapter(adapter);   //리사이클러뷰에 어댑터 설정
            adapter.notifyDataSetChanged(); //어댑터 갱신
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class DividerItemDecoration extends RecyclerView.ItemDecoration {   //리사이클러뷰 아이템 구분선
    private final Drawable divider;

    public DividerItemDecoration(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.divider);   //구분선 이미지
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {    //구분선 그리기
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();    //리사이클러뷰 자식 뷰 개수
        for (int i = 0; i < childCount - 1; i++) {  //자식 뷰 개수만큼 반복
            View child = parent.getChildAt(i);  //자식 뷰

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams(); //리사이클러뷰 레이아웃 설정

            int top = child.getBottom() + params.bottomMargin;  //위
            int bottom = top + divider.getIntrinsicHeight();    //아래

            divider.setBounds(left, top, right, bottom);    //구분선 위치 설정
            divider.draw(c);    //구분선 그리기
        }
    }
}
