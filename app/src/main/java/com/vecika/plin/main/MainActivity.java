package com.vecika.plin.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vecika on 04.11.2017..
 */

public class MainActivity extends AppCompatActivity implements MainAdapter.IOnListClicked, SearchView.OnQueryTextListener {

    private MainAdapter mAdapter;
    private ArrayList<Workorder> mData;

    @BindView(R.id.recyclerViewMain)    RecyclerView    mRecyclerViewMain;
    @BindView(R.id.searchView)          SearchView      mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        mData = new ArrayList<>();
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111121", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111131", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1114111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1115111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1161111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1171111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1117111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111888111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11199111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1119111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11121111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11113211", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11111211", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111113211", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11145111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1131111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11123111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11231111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11111561", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11115611", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1116111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("11145111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111611", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111711", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111711", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1119111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1118111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("1111101", "Miro", "Puzic", "Vukovarksa 33", "123334"));

        mAdapter = new MainAdapter();
        mAdapter.setCallback(this);
        mAdapter.addItems(mData);
        mRecyclerViewMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewMain.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClicked(Workorder workorder) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("OBJECT", workorder);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<Workorder> newList = new ArrayList<>();
        for (Workorder workorder : mData) {
            String serialNo = workorder.getSerialNumber().toLowerCase();
            if(serialNo.contains(newText)){
                newList.add(workorder);
            }
        }
        mAdapter.setFilter(newList);
        return true;
    }
}
