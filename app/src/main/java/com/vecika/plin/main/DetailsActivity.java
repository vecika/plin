package com.vecika.plin.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import butterknife.ButterKnife;

/**
 * Created by vecika on 05.11.2017..
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Workorder workorder = getIntent().getExtras().getParcelable("OBJECT");
        Toast.makeText(this, workorder.getName(), Toast.LENGTH_SHORT).show();
    }
}
