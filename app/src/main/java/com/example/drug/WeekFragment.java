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

public class WeekFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    private TextView selectedDateTextView;
    private TextView[] dateTextViews;
    private TextView[] dateWeek;
    public static WeekFragment newInstance(int position, TextView selectedDateTextView) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        fragment.setSelectedDateTextView(selectedDateTextView);
        return fragment;
    }

    public void setSelectedDateTextView(TextView selectedDateTextView) {
        this.selectedDateTextView = selectedDateTextView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        dateTextViews = new TextView[7];
        dateTextViews[0] = view.findViewById(R.id.tv_1);
        dateTextViews[1] = view.findViewById(R.id.tv_2);
        dateTextViews[2] = view.findViewById(R.id.tv_3);
        dateTextViews[3] = view.findViewById(R.id.tv_4);
        dateTextViews[4] = view.findViewById(R.id.tv_5);
        dateTextViews[5] = view.findViewById(R.id.tv_6);
        dateTextViews[6] = view.findViewById(R.id.tv_7);


        List<Calendar> weekDates = getWeekDates(position);
        setWeekDates(weekDates);

        return view;
    }

    private List<Calendar> getWeekDates(int position) {
        List<Calendar> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, position - (Integer.MAX_VALUE / 2));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        for (int i = 0; i < 7; i++) {
            dates.add((Calendar) calendar.clone());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private void setWeekDates(List<Calendar> weekDates) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        Calendar today = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            Calendar date = weekDates.get(i);
            dateTextViews[i].setText(dateFormat.format(date.getTime()));


            if (isSameDay(date, today)) {
                dateTextViews[i].setBackgroundColor(Color.GREEN);
            }else {
                dateTextViews[i].setBackgroundColor(Color.WHITE);
            }

            final int index = i;
            dateTextViews[i].setOnClickListener(v -> { // 날짜 클릭 시
                selectedDateTextView.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(date.getTime()));   // 선택된 날짜로 TextView 업데이트
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
