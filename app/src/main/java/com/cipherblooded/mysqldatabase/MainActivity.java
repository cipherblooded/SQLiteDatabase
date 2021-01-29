package com.cipherblooded.mysqldatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cipherblooded.mysqldatabase.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button bt_submit,bt_view,bt_update,bt_delete;
    EditText et_id,et_name,et_surname,et_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        et_id=findViewById(R.id.id);
        et_name=findViewById(R.id.name);
        et_surname=findViewById(R.id.surname);
        et_marks=findViewById(R.id.marks);
        bt_submit=findViewById(R.id.submit);
        bt_view=findViewById(R.id.viewData);
        bt_update=findViewById(R.id.update);
        bt_delete=findViewById(R.id.delete);

        addData();
        viewData();
        updateData();
        deleteData();
    }

    public void addData(){
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long result = myDb.insertData(
                        et_name.getText().toString(),
                        et_surname.getText().toString(),
                        et_marks.getText().toString()
                );

                if(result != -1){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewData(){
        bt_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Cursor res=myDb.getData();
                if(res.getCount()==0){
                    showMessage("Error ","Nothing Found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n");
                    buffer.append("Marks :"+res.getString(3)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void updateData(){
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer updatedRows=myDb.updateData(et_id.getText().toString(),et_name.getText().toString(),et_surname.getText().toString(),et_marks.getText().toString());
                if(updatedRows>0){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer deletedRows = myDb.deleteData(et_id.getText().toString());

                if(deletedRows>0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showMessage(String tittle, String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(Message);
        builder.show();
    }
}
