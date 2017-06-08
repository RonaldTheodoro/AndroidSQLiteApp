package com.app.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper database;
    EditText editName, editSurename, editMarks;
    Button btnAddData, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editTextName);
        editSurename = (EditText) findViewById(R.id.editTextSurename);
        editMarks = (EditText) findViewById(R.id.editTextMarks);
        btnAddData = (Button) findViewById(R.id.addData);
        btnViewAll= (Button) findViewById(R.id.viewAll);

        addData();
        viewAll();
    }

    public void addData() {
        btnAddData.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = database.insertData(
                        editName.getText().toString(),
                        editSurename.getText().toString(),
                        editMarks.getText().toString()
                    );

                    String message = (isInserted) ? "Data Inserted" : "Data not Inserted";

                    showToastMessage(message);
                }
            }
        );
    }

    private void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = database.getAllData();

                    if (cursor.getCount() == 0) {
                        showMessage("Error", "Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();

                    while (cursor.moveToNext()) {
                        buffer.append("id: " + cursor.getString(0) + "\n");
                        buffer.append("name: " + cursor.getString(1) + "\n");
                        buffer.append("surename: " + cursor.getString(2) + "\n");
                        buffer.append("marks: " + cursor.getString(3) + "\n\n");
                    }

                    showMessage("Data", buffer.toString());
                }
            }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
