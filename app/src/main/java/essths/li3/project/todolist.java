package essths.li3.project;

import static essths.li3.project.R.id.addButton1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import essths.li3.project.Adapter.OnDialogCloseListner;
import essths.li3.project.Adapter.ToDoAdapter;
import essths.li3.project.Utils.DataBaseHelper;
import essths.li3.project.model.ToDoModel;

public class todolist extends AppCompatActivity implements OnDialogCloseListner {

     RecyclerView recyclerView;
     FloatingActionButton addButton;
     DataBaseHelper myDB;
     private List<ToDoModel> mlist;
     private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(addButton1);

        myDB = new DataBaseHelper(todolist.this, "TODO_DATABASE", null, 1);
        mlist = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, todolist.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mlist = myDB.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTask(mlist);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTask.newInstance().show(getSupportFragmentManager(), AddTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todolist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Blocnote) {
            Toast.makeText(this, "Bloc Notes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(todolist.this, blocnote.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.quit) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(todolist.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mlist = myDB.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTask(mlist);
        adapter.notifyDataSetChanged();
    }
}
