package essths.li3.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView register = findViewById(R.id.registerhere);
        EditText username = findViewById(R.id.user);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.button);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, register.class);
            startActivity(intent);
        });
        login.setOnClickListener(v->{
            if (username.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Le champ Username  est vide", Toast.LENGTH_SHORT).show();
            }else if (password.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Le champ Password est vide", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
        } );


         
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}