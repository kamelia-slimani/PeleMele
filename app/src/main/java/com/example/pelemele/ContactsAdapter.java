package com.example.pelemele;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    protected ArrayList<Contact> tabContacts;
    protected Context context;
    public ContactsAdapter(ArrayList<Contact> contacts, Context c) {
            tabContacts = contacts;
            context = c;
        }


    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.activity_contact_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {

        Contact contact = tabContacts.get(position);
        TextView textViewNom = holder.nom;
        textViewNom.setText(contact.getNom());
        TextView textViewNum = holder.num;
        textViewNum.setText(contact.getNum());

    }


    @Override
    public int getItemCount() {
        return tabContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView num;

        public ViewHolder(View itemView) {

        super(itemView);

        nom = itemView.findViewById(R.id.nom);
        num = itemView.findViewById(R.id.num);

        }
    }
}
