package cn.istary.material.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.istary.material.R;

public class LoginActivity extends BaseActivity {

    private EditText usernameEdit;
    private EditText passwdEdit;
    private Button loginButton;
    private TextInputLayout tlUsername;
    private TextInputLayout tlPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.login_username_edit);
        passwdEdit = findViewById(R.id.login_passwd_edit);
        loginButton = findViewById(R.id.login_button);
        tlUsername = findViewById(R.id.tl_username);
        tlPasswd = findViewById(R.id.tl_passwd);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String username = usernameEdit.getText().toString();
                //String passwd = passwdEdit.getText().toString();
                String username = tlUsername.getEditText().getText().toString();
                String passwd = tlPasswd.getEditText().getText().toString();

                if(!validateUsername(username)){
                    tlUsername.setErrorEnabled(true);
                    tlUsername.setError("Please input correct username");
                }else if(!validatePasswd(passwd)){
                    tlPasswd.setErrorEnabled(true);
                    tlPasswd.setError("The password should between 6-12 bits");
                }else if(username.equals("yxr") && passwd.equals("1234567")){
                    tlUsername.setErrorEnabled(false);
                    tlPasswd.setErrorEnabled(false);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    finish();
                }else{
                    tlUsername.setErrorEnabled(false);
                    tlPasswd.setErrorEnabled(false);
                    Toast.makeText(LoginActivity.this, "username or passwd wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validatePasswd(String passwd){
        return (passwd.length() > 6 && passwd.length() < 16) ;
    }

    private boolean validateUsername(String name){
        return (name.equals("yxr"));
    }


}
