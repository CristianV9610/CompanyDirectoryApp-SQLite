package edu.unal.sqlite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayContact extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView nameTextView;
    TextView urlTextView;
    TextView phoneTextView;
    TextView emailTextView;
    TextView products_servicesTextView;
    TextView company_typeTextView;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        nameTextView = (TextView) findViewById(R.id.editTextName);
        urlTextView = (TextView) findViewById(R.id.editTextUrl);
        phoneTextView = (TextView) findViewById(R.id.editTextPhone);
        emailTextView = (TextView) findViewById(R.id.editTextEmail);
        products_servicesTextView = (TextView) findViewById(R.id.editTextProductServices);
        company_typeTextView = (TextView) findViewById(R.id.editTextCompanyType);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            int value = extras.getInt("id");

            if(value > 0){
                Cursor rs = mydb.getData(value);
                id_To_Update = value;
                rs.moveToFirst();

                @SuppressLint("Range") String name = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                @SuppressLint("Range") String url = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_URL));
                @SuppressLint("Range") String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                @SuppressLint("Range") String email = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                @SuppressLint("Range") String products_services = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PRODUCTS_SERVICES));
                @SuppressLint("Range") String company_type = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_COMPANY_TYPE));

                if (!rs.isClosed()){
                    rs.close();
                }

                Button saveButton = (Button) findViewById(R.id.button1);
                saveButton.setVisibility(View.INVISIBLE);

                nameTextView.setText((CharSequence) name);
                nameTextView.setFocusable(false);
                nameTextView.setClickable(false);

                urlTextView.setText((CharSequence) url);
                urlTextView.setFocusable(false);
                urlTextView.setClickable(false);

                phoneTextView.setText((CharSequence) phone);
                phoneTextView.setFocusable(false);
                phoneTextView.setClickable(false);

                emailTextView.setText((CharSequence) email);
                emailTextView.setFocusable(false);
                emailTextView.setClickable(false);

                products_servicesTextView.setText((CharSequence) products_services);
                products_servicesTextView.setFocusable(false);
                products_servicesTextView.setClickable(false);

                company_typeTextView.setText((CharSequence) company_type);
                company_typeTextView.setFocusable(false);
                company_typeTextView.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int value = extras.getInt("id");
            if( value > 0 ){
                getMenuInflater().inflate(R.menu.display_contact_menu, menu);
            }
            else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.Edit_Contact:
                Button saveButton = (Button) findViewById(R.id.button1);

                saveButton.setVisibility(View.VISIBLE);

                nameTextView.setEnabled(true);
                nameTextView.setFocusableInTouchMode(true);
                nameTextView.setClickable(true);

                urlTextView.setEnabled(true);
                urlTextView.setFocusableInTouchMode(true);
                urlTextView.setClickable(true);

                phoneTextView.setEnabled(true);
                phoneTextView.setFocusableInTouchMode(true);
                phoneTextView.setClickable(true);

                emailTextView.setEnabled(true);
                emailTextView.setFocusableInTouchMode(true);
                emailTextView.setClickable(true);

                products_servicesTextView.setEnabled(true);
                products_servicesTextView.setFocusableInTouchMode(true);
                products_servicesTextView.setClickable(true);

                company_typeTextView.setEnabled(true);
                company_typeTextView.setFocusableInTouchMode(true);
                company_typeTextView.setClickable(true);

                return true;

            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Are you sure?");
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,nameTextView.getText().toString(),
                        urlTextView.getText().toString(), phoneTextView.getText().toString(),
                        emailTextView.getText().toString(), products_servicesTextView.getText().toString(), company_typeTextView.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertContact(nameTextView.getText().toString(), urlTextView.getText().toString(),
                        phoneTextView.getText().toString(), emailTextView.getText().toString(),
                        products_servicesTextView.getText().toString(),company_typeTextView.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
