package com.dorellano.parcialsqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.dorellano.parcialsqlite.helpers.person;
import com.dorellano.parcialsqlite.helpers.personDBHelper;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "recyclerViewAdapter";
    private ArrayList<person> people;
    private ArrayList<person> contactosFull;
    private Context mContext;
    private int position;
    private personDBHelper con;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public recyclerViewAdapter(ArrayList<person> people, Context mContext) {
        this.people = people;
        this.mContext = mContext;
        contactosFull = new ArrayList<>(people);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapterlis, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindCalled");
        viewHolder.tvNombre.setText(people.get(i).getNombre());
        viewHolder.tvCedula.setText("Cedula: "+ people.get(i).getCedula());
        viewHolder.tvEstrato.setText("Estrato: "+ people.get(i).getEstrato());
        viewHolder.tvSalario.setText("Salario: "+ people.get(i).getSalario());
        viewHolder.tvNivel.setText(people.get(i).getNivelEdu());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(viewHolder.getPosition());
                // notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<person> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(contactosFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (person contact : contactosFull) {
                    if (contact.getCedula().toLowerCase().contains(filterPattern)) {
                        filteredList.add(contact);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            people.clear();
            people.addAll((ArrayList) results.values);
            notifyDataSetChanged();
            // Toast.makeText(mContext, "Resultados Publicados", Toast.LENGTH_SHORT).show();

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tvNombre, tvEstrato, tvCedula, tvSalario,tvNivel;
        ImageView ivNombre;
        ConstraintLayout parent_layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEstrato = itemView.findViewById(R.id.tvEstrato);
            tvCedula = itemView.findViewById(R.id.tvcedula);
            tvSalario = itemView.findViewById(R.id.tvSalario);
            tvNivel=itemView.findViewById(R.id.tvNivelE);

            parent_layout = itemView.findViewById(R.id.parent_layout);
            parent_layout.setBackgroundResource(R.drawable.edit_text_white);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Opciones");
            contextMenu.add(0, 122, 0, "Editar");
            contextMenu.add(0, 123, 0, "Eliminar");

        }


    }
}
