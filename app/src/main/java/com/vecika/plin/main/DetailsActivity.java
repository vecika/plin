package com.vecika.plin.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.vecika.plin.R;
import com.vecika.plin.models.Workorder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vecika on 05.11.2017..
 */

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.textViewSerialNumber)    TextView    mTextViewSerialNumber;
    @BindView(R.id.textViewName)            TextView    mTextViewName;
    @BindView(R.id.textViewSurname)         TextView    mTextViewSurname;
    @BindView(R.id.textViewAddress)         TextView    mTextViewAddress;
    @BindView(R.id.textViewLastState)       TextView    mTextViewLastRead;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Workorder workorder = getIntent().getExtras().getParcelable("OBJECT");
        mTextViewSerialNumber.setText(workorder.getSerialNumber());
        mTextViewName.setText(workorder.getName());
        mTextViewSurname.setText(workorder.getSurname());
        mTextViewAddress.setText(workorder.getAddress());
        mTextViewLastRead.setText(workorder.getLastRead());

    }

    @OnClick(R.id.buttonReadState)
    public void onButtonReadStatePressed(){

    }






}
