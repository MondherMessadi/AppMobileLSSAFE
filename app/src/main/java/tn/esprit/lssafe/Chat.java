package tn.esprit.lssafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.lssafe.Fragments.ChatFragment;
import tn.esprit.lssafe.Fragments.UserFragment;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new ChatFragment(), "Chats");
        adapter.addFragment(new UserFragment(),"User");
        viewPager.setAdapter(adapter);

        // Set the default fragment to display
        viewPager.setCurrentItem(0);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getTitle(position))
        ).attach();
    }

    public class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }

        @NonNull

        public String getPageTitle(int position) {
            return titleList.get(position);
        }
        public String getTitle(int position) {
            return titleList.get(position);
        }

    }
}