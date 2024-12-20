package essths.li3.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        Button signup = findViewById(R.id.button);
        EditText username = findViewById(R.id.user);
        EditText email = findViewById(R.id.mail);
        EditText password = findViewById(R.id.password);
        DBhelper DB = new DBhelper(this);
        FloatingActionButton back = findViewById(R.id.backarrow);
        back.setOnClickListener(v -> {
            Intent home = new Intent(register.this, MainActivity.class);
            startActivity(home);
        });
        signup.setOnClickListener(v -> {
            if (username.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please Enter You Username", Toast.LENGTH_SHORT).show();
            }else if (email.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please Enter You Email", Toast.LENGTH_SHORT).show();
            }else if (password.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please Enter You Password", Toast.LENGTH_SHORT).show();
            }else{
                Boolean check = DB.insert_user(username.getText().toString(), email.getText().toString(), password.getText().toString());
                if (check){
                    Toast.makeText(register.this, "inserted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(register.this, MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(register.this, "not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}