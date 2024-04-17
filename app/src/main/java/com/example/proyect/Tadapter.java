package com.example.proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Tadapter extends RecyclerView.Adapter<Tadapter.ViewHolder> {

    private List<Tarea> tareas;
    private Context context;

    public Tadapter(Context context, List<Tarea> tareas) {
        this.context = context;
        this.tareas = tareas;
    }

    public interface OnItemClickListener {
        void onItemClick(Tarea tarea);
    }


    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        String key = tarea.getKey();

        holder.textViewName.setText(tarea.getName());
        holder.textViewDetails.setText(tarea.getDetalles());
        holder.textViewDate.setText(tarea.getDate());
        holder.textViewState.setText(tarea.getEstado().toString());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(tarea);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewName;
        TextView textViewDetails;

        TextView textViewDate;

        TextView textViewState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textViewName = itemView.findViewById(R.id.nombre);
            textViewDetails = itemView.findViewById(R.id.infod);
            textViewDate = itemView.findViewById(R.id.fecha);
            textViewState = itemView.findViewById(R.id.estado);
        }
    }
}

