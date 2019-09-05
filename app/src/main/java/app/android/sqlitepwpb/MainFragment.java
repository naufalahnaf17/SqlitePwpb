package app.android.sqlitepwpb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.android.sqlitepwpb.Database.DatabaseHelper;

public class MainFragment extends Fragment implements View.OnClickListener , RecyclerViewAdapter.OnUserClickListener {
    RecyclerView recyclerView;
    EditText edtName,edtAge;
    Button btnSubmit;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<PersonBean> listPersonInfo;
    public MainFragment() {
// Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_fragment, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        context=getActivity();
        recyclerView=view.findViewById(R.id.myRe);
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        edtName=view.findViewById(R.id.eNama);
        edtAge=view.findViewById(R.id.eUmur);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                PersonBean currentPerson = new PersonBean();
                currentPerson.setNama(edtName.getText().toString());
                currentPerson.setAge(Integer.parseInt(edtAge.getText().toString()));
                db.insert(currentPerson);
                setupRecyclerView();
                db.close();
            }
        });

        setupRecyclerView();
    }
    public void setupRecyclerView() {
        DatabaseHelper db=new DatabaseHelper(context);
        listPersonInfo=db.selectUserData();
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(context,listPersonInfo,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onUserClick(PersonBean current, String action) {
        if (action.equals("delete")){
            DatabaseHelper db = new DatabaseHelper(context);
            db.delete(current.getNama());
            setupRecyclerView();
        }
    }
}
