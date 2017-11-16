package s213329913.wrap302.nmmu.assignment1_task2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by s2133 on 2017/07/30.
 */

public class ContactAdapter extends ArrayAdapter<Contact> implements Filterable {
    List<Contact> originalContacts;
    List<Contact> displayedContacts;

    ContactAdapter contactAdapter = this;
    ContactFilter contactFilter = new ContactFilter();

    public ContactAdapter(Context context, List<Contact> contacts){
        super(context, R.layout.contact_layout, contacts);
        this.displayedContacts = contacts;
        this.originalContacts = new ArrayList<>(contacts);
    }

    @Override
    public void add(@Nullable Contact object) {
        super.add(object);
        this.originalContacts.add(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if this is a new view, then inflate it
        View personView = convertView;

        if (personView == null) {
            // get the inflater that will convert the person_layout.xml file
            // into an actual object (i.e. inflate it)
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // create a view to display the person's info
            personView = inflater.inflate(R.layout.contact_layout, parent, false);
        }

        // keep track of person this view is working with
        //The contact at the specific position
        personView.setTag(getItem(position));

        // get views that will hold data
        TextView txtSurname = (TextView) personView.findViewById(R.id.txtSurname);
        TextView txtFirstname = (TextView) personView.findViewById(R.id.txtName);
        TextView txtEmail = (TextView) personView.findViewById(R.id.txtEmail);
        TextView txtCellphone = (TextView) personView.findViewById(R.id.txtNumber);
        Button btnDelete = (Button) personView.findViewById(R.id.btnDelete);


        // set data fields
        final Contact contact = getItem(position);
        txtSurname.setText(contact.surname);
        txtFirstname.setText(contact.firstName);
        txtEmail.setText(contact.email);
        txtCellphone.setText(contact.cellNumber);


        // return view to ListView to display
        return personView;
    }

    public void sortContacts(final int position){
        super.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                if(position==0) {
                    return contact.firstName.toUpperCase().compareTo(t1.firstName.toUpperCase());
                }
                else if(position==1) {
                    return contact.surname.toUpperCase().compareTo(t1.surname.toUpperCase());
                }
                else if(position==2) {
                    return contact.email.toUpperCase().compareTo(t1.email.toUpperCase());
                }
                else {
                    return contact.cellNumber.toUpperCase().compareTo(t1.cellNumber.toUpperCase());
                }
            }
        });
    }


    //getfilter method return new contactfliter only

    @Override
    public Filter getFilter() {
        if (contactFilter == null)
            contactFilter = new ContactFilter();
        return contactFilter;
    }

    private class ContactFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = originalContacts;
                results.count = originalContacts.size();
            }
            else {
                // We perform filtering operation
                List<Contact> contactList = new ArrayList<Contact>();

                for (Contact contact : originalContacts) {
                    if (contact.firstName.toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        contactList.add(contact);
                    else if (contact.surname.toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        contactList.add(contact);
                    else if (contact.cellNumber.startsWith(constraint.toString().toUpperCase()))
                        contactList.add(contact);
                    else if (contact.email.toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        contactList.add(contact);
                }

                results.values = contactList;
                results.count = contactList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            contactAdapter.clear();
            contactAdapter.displayedContacts.clear();
            contactAdapter.addAll((List<Contact>) results.values);
            contactAdapter.notifyDataSetChanged();
        }
    }

}
