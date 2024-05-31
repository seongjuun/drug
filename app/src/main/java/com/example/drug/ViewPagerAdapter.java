package com.example.drug;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter { // 뷰페이저 어댑터

    private TextView selectedDateTextView;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, TextView selectedDateTextView) { // 생성자
        super(fragmentActivity);
        this.selectedDateTextView = selectedDateTextView;
    }

    public ViewPagerAdapter(@NonNull Fragment fragment, TextView selectedDateTextView) { // 생성자
        super(fragment);
        this.selectedDateTextView = selectedDateTextView;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { // 프래그먼트 생성
        return WeekFragment.newInstance(position, selectedDateTextView);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; // 무한 스크롤을 위해 큰 값 반환
    }
}
