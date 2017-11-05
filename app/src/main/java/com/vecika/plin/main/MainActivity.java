package com.vecika.plin.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vecika on 04.11.2017..
 */

public class MainActivity extends AppCompatActivity implements MainAdapter.IOnListClicked {

    private MainAdapter mAdapter;
    private ArrayList<Workorder> mData;

    @BindView(R.id.recyclerViewMain)    RecyclerView    mRecyclerViewMain;

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
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));
        mData.add(new Workorder("111111", "Miro", "Puzic", "Vukovarksa 33", "123334"));

        mAdapter = new MainAdapter();
        mAdapter.setCallback(this);
        mAdapter.addItems(mData);
        mRecyclerViewMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewMain.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(Workorder workorder) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("OBJECT", workorder);
        startActivity(intent);
    }
}
