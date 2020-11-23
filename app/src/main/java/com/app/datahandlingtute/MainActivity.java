package com.app.datahandlingtute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.datahandlingtute.database.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText uName, pwd;
    private Button selectAll, add, signIn, delete, update;
    private DBHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uName = findViewById(R.id.uName);
        pwd = findViewById(R.id.pwd);
        selectAll = findViewById(R.id.select_all);
        add = findViewById(R.id.add);
        signIn = findViewById(R.id.sign_in);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);

        context = this;
        dbHelper = new DBHelper(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUName = uName.getText().toString();
                String txtPwd = pwd.getText().toString();

                long newRowId = dbHelper.addInfo(txtUName, txtPwd);

                Toast.makeText(context, "new Row ID is "+ newRowId, Toast.LENGTH_SHORT).show();

            }
        });

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List usernames = dbHelper.readAllInfo("user");
                Toast.makeText(context, usernames.toString(), Toast.LENGTH_LONG).show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List usernames = dbHelper.readAllInfo("user");
                List passwords = dbHelper.readAllInfo("password");

                String name = uName.getText().toString();
                String password = pwd.getText().toString();

                if (usernames.contains(name)){
                    int nameIndex = usernames.indexOf(name);
                    if (passwords.contains(password)){
                        int pwdIndex = passwords.indexOf(password);

                        if (nameIndex == pwdIndex) {
                            Toast.makeText(context, "Login Successfull", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context, "Login Failed. Incorrect Password.", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(context, "Password is not in database.", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context, "Username is not in database.", Toast.LENGTH_LONG).show();
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteData(uName.getText().toString());
                Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateUser(uName.getText().toString(), pwd.getText().toString());
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            }
        });


    }
}