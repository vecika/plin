package com.vecika.plin.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.vecika.plin.main.MainActivity;
import com.vecika.plin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vecika on 04.11.2017..
 */

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.editTextPassword)    EditText    mEditTextPassword;
    @BindView(R.id.editTextUserName)    EditText    mEditTextUserName;
    @BindView(R.id.buttonLogIn)         Button      mButtonLogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {



    }



    @OnClick(R.id.buttonLogIn)
    public void onButtonLoginClicked(){
        // TODO: 04.11.2017. validate username and password

        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }


}
