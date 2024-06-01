package com.example.drug;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeekFragment extends Fragment { // 주별 날짜 Fragment

    private static final String ARG_POSITION = "position";
    private int position;
    private TextView selectedDateTextView;
    private TextView[] dateTextViews;
    public static WeekFragment newInstance(int position, TextView selectedDateTextView) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);    // position 값 전달
        fragment.setArguments(args);    // fragment에 전달
        fragment.setSelectedDateTextView(selectedDateTextView); // 선택된 날짜 TextView 전달
        return fragment;
    }

    public void setSelectedDateTextView(TextView selectedDateTextView) {
        this.selectedDateTextView = selectedDateTextView;   // 선택된 날짜 TextView 설정
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {   // fragment 생성 시
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION); // position 값 설정
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        dateTextViews = new TextView[7]; // 날짜 TextView 배열
        dateTextViews[0] = view.findViewById(R.id.tv_1);
        dateTextViews[1] = view.findViewById(R.id.tv_2);
        dateTextViews[2] = view.findViewById(R.id.tv_3);
        dateTextViews[3] = view.findViewById(R.id.tv_4);
        dateTextViews[4] = view.findViewById(R.id.tv_5);
        dateTextViews[5] = view.findViewById(R.id.tv_6);
        dateTextViews[6] = view.findViewById(R.id.tv_7);

        List<Calendar> weekDates = getWeekDates(position);  // 주별 날짜 리스트
        setWeekDates(weekDates);

        return view;
    }

    private List<Calendar> getWeekDates(int position) { // 주별 날짜 리스트 반환
        List<Calendar> dates = new ArrayList<>();   // 날짜 리스트
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, position - (Integer.MAX_VALUE / 2));    // 주별 날짜 설정
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());   // 주의 첫 날짜로 설정
        for (int i = 0; i < 7; i++) {   // 7일 동안
            dates.add((Calendar) calendar.clone());  // 날짜 추가
            calendar.add(Calendar.DAY_OF_MONTH, 1);  // 다음 날짜로 이동
        }
        return dates;
    }

    private void setWeekDates(List<Calendar> weekDates) {   // 주별 날짜 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());  // 날짜 형식
        Calendar today = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {   // 7일 동안
            Calendar date = weekDates.get(i);   // 날짜 설정
            dateTextViews[i].setText(dateFormat.format(date.getTime()));    // 날짜 TextView 업데이트


            if (isSameDay(date, today)) {   // 오늘 날짜인 경우
                dateTextViews[i].setBackgroundColor(Color.GREEN);   // 배경색 변경
            }else { // 오늘 날짜가 아닌 경우
                dateTextViews[i].setBackgroundColor(Color.WHITE);   // 배경색 변경
            }

            final int index = i;
            dateTextViews[i].setOnClickListener(v -> { // 날짜 클릭 시
                selectedDateTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date.getTime()));   // 선택된 날짜로 TextView 업데이트
                Home.Refresh(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date.getTime()));
                for (TextView tv : dateTextViews) { // 선택된 날짜 배경색 변경
                    if(tv.getId() == dateTextViews[index].getId() && isSameDay(date, today)) {    // 오늘 날짜인 경우
                        tv.setBackgroundColor(Color.GREEN);
                    } else if (tv.getId() == dateTextViews[index].getId()) {  // 선택된 날짜인 경우
                        tv.setBackgroundColor(Color.YELLOW);
                    } else {
                        tv.setBackgroundColor(Color.WHITE); // 나머지 날짜는 흰색
                    }
                }
            });

            dateTextViews[i].setTag(date);
        }
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
