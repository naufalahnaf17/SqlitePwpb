package app.android.sqlitepwpb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.android.sqlitepwpb.Database.DatabaseHelper;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder> {

    Context ctx;
    List<PersonBean> personBeans;
    OnUserClickListener listener;

    public RecyclerViewAdapter(Context ctx, List<PersonBean> personBeans , OnUserClickListener listener) {
        this.ctx = ctx;
        this.personBeans = personBeans;
        this.listener = listener;
    }

    public interface OnUserClickListener{
        void onUserClick(PersonBean current , String action);
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(v);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.UserViewHolder holder, int position) {
        final PersonBean person = personBeans.get(position);
        holder.txtNama.setText(person.getNama());
        holder.txtUmur.setText(person.getAge()+"");
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(person,"edit");
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(person,"delete");
            }
        });
    }

    @Override
    public int getItemCount() {
        return personBeans.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView txtNama , txtUmur;
        Button btnUpdate,btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtUmur = itemView.findViewById(R.id.txtUmur);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
