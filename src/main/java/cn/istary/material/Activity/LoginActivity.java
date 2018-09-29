package cn.istary.material.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.istary.material.R;

public class LoginActivity extends BaseActivity {

    private EditText usernameEdit;
    private EditText passwdEdit;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.login_username_edit);
        passwdEdit = findViewById(R.id.login_passwd_edit);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdit.getText().toString();
                String passwd = passwdEdit.getText().toString();
                if(username.equals("yxr") && passwd.equals("123456")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "username or passwd wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
