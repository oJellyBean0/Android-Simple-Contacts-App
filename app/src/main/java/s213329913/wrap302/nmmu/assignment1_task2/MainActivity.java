package s213329913.wrap302.nmmu.assignment1_task2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ContactAdapter contactAdapter;
    public static final int add = 1;
    public static final int list = 2;
    public int sortPosition;
    final List<Contact> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views
        FloatingActionButton btnAddNewContact =  (FloatingActionButton) findViewById(R.id.btnAddNewContact);
        Spinner spinnerSort = (Spinner) findViewById(R.id.spinSort);
        SearchView search = (SearchView) findViewById(R.id.searchView);
        final ListView lstInfo = (ListView) findViewById(R.id.lstContacts);

        items.add(new Contact("John", "Smith", "0786594569", "johnSmith@gmail.com"));
        items.add(new Contact("Jack", "Wright", "0824659875", "jackW@nmmu.ac.za"));
        items.add(new Contact("Simon", "King", "0729658459", "simonKing@yahoo.com"));
        items.add(new Contact("Tara", "Klopper ", "0736954854", "taraklop@gmail.com"));
        items.add(new Contact("James", "Smith", "0715456569", "jamesSmith@gmail.com"));
        items.add(new Contact("Jason", "long", "0645814587", "jasonlong@nmmu.ac.za"));
        items.add(new Contact("Simon", "Snow", "0729658548", "SimonSnow@yahoo.com"));
        items.add(new Contact("Tayla", "Kern ", "0736958479", "taylakern@gmail.com"));
        items.add(new Contact("Jacques", "Griffiths", "0786598748", "jacquesG@gmail.com"));
        items.add(new Contact("Dillon", "Wright", "0829685875", "dillon@nmmu.ac.za"));
        items.add(new Contact("Kate", "Pringle", "0836658459", "katep@yahoo.com"));
        items.add(new Contact("Sarah", "Johnson ", "0796324854", "sarah@gmail.com"));

        //Creating and populating the sort spinner
        List<String> sortSpinnerList = new ArrayList<String>();
        sortSpinnerList.add("Name");
        sortSpinnerList.add("Surname");
        sortSpinnerList.add("E-mail Address");
        sortSpinnerList.add("Cellphone Number");
        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortSpinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);

        //linking contact adapter
        contactAdapter = new ContactAdapter(this, items);
        lstInfo.setAdapter(contactAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //boolean status = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*if (!status) {
                    status = true;
                    return;
                }*/
                sortPosition = position;
                contactAdapter.sortContacts(position);
                contactAdapter.notifyDataSetChanged();
                ((TextView) parent.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                //intent.putExtra("", "");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, add);
                }
            }
        });

        lstInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("contact", (Contact) view.getTag());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, list);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case add:
                    Contact contactToAdd = (Contact) data.getSerializableExtra("addNewContact");

                    if (contactToAdd != null) {
                        contactAdapter.add(contactToAdd);
                        contactAdapter.sortContacts(sortPosition);
                        contactAdapter.notifyDataSetChanged();
                    }
                    break;
                case list:
                    Contact contactToDelete = (Contact) data.getSerializableExtra("deleteContact");
                    Contact updatedContact = (Contact) data.getSerializableExtra("updatedContact");
                    if (updatedContact == null) {
                        if (contactToDelete != null) {
                            Toast.makeText(this, "Contact deleted",
                                    Toast.LENGTH_LONG).show();
                            contactAdapter.remove(contactToDelete);
                            //contactAdapter.sortContacts(sortPosition);
                            contactAdapter.notifyDataSetChanged();
                        }
                    } else {

                        Toast.makeText(this, "Contact updated",
                                Toast.LENGTH_LONG).show();
                        contactAdapter.remove(contactToDelete);
                        contactAdapter.add(updatedContact);
                        contactAdapter.sortContacts(sortPosition);
                        contactAdapter.notifyDataSetChanged();
                    }
                    break;

            }
        }
    }

}

