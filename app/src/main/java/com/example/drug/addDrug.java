package com.example.drug;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class addDrug extends AppCompatActivity {

    EditText drugName;
    ImageView clear;
    RecyclerView recyclerView;
    DrugAdapter adapter;
    ArrayList<String> drugList;
    String drugItems;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_drug);
        ImageView close = findViewById(R.id.back);
        close.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drugList = new ArrayList<>();
        adapter = new DrugAdapter(drugList);
        recyclerView.setAdapter(adapter);

        drugName = findViewById(R.id.drugName);
        handler = new Handler();

        drugName.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                return true;
            }else{
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                runnable = () -> {
                    new Thread(() -> {
                        try {
                            drugItems = ApiClient.ApiExplorer(drugName.getText().toString());
                            runOnUiThread(() -> xmlParsing(drugItems));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                };
                handler.postDelayed(runnable, 1000); // 1-second delay
                return true;
            }
        });


        clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            drugName.setText("");
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void xmlParsing(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");
            drugList.clear();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList name = element.getElementsByTagName("itemName");
                    if (name.getLength() > 0) {
                        String itemName = name.item(0).getTextContent();
                        drugList.add(itemName);
                    }
                }
            }
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable divider;

    public DividerItemDecoration(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.divider);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
