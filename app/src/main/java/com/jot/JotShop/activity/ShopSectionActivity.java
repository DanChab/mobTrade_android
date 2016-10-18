package com.jot.JotShop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jot.JotShop.DividerItemDecoration;
import com.jot.JotShop.R;
import com.jot.JotShop.RecyclerTouchListener;
import com.jot.JotShop.Section;
import com.jot.JotShop.adapter.SectionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopSectionActivity extends AppCompatActivity {

    private List<Section> sectionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SectionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_section_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new SectionsAdapter(sectionList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Section section = sectionList.get(position);
                Toast.makeText(getApplicationContext(), section.getName() + " is selected!", Toast.LENGTH_SHORT).show();
                if(position==1){
                    Intent intent = new Intent(ShopSectionActivity.this,SectionOneActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareSectionData();
    }

    private void prepareSectionData() {
        Section section = new Section("Section1","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);

        section = new Section("Section2","Beef,T-bon,Chicken,Pork");
        sectionList.add(section);

        section = new Section("Section3","Soap,Tooth past,sugar");
        sectionList.add(section);

        section = new Section("Section4","Micro wave,Stove,blinder");
        sectionList.add(section);

        section = new Section("Section5","Bred,Cake,Scone,rows");
        sectionList.add(section);

        section = new Section("Section6","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);

        section = new Section("Section7","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);

        section = new Section("Section8","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);

        section = new Section("Section9","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);

        section = new Section("Section10","Coffee,Biscuit,Tea,sugar");
        sectionList.add(section);


        mAdapter.notifyDataSetChanged();
    }
}
