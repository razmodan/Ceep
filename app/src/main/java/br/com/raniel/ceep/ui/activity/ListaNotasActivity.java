package br.com.raniel.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import br.com.raniel.ceep.R;
import br.com.raniel.ceep.dao.NotaDAO;
import br.com.raniel.ceep.model.Nota;
import br.com.raniel.ceep.ui.recyclerViewAdapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciaFormularioNota  = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivityForResult(iniciaFormularioNota, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 2 && data.hasExtra("nota")){
            Nota nota = (Nota) data.getSerializableExtra("nota");
            new NotaDAO().insere(nota);
            adapter.adiciona(nota);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();

        for(int i = 1; i <= 1000; i++) {
            dao.insere(new Nota("nota " + i,
                    "descrição " + i));
        }

        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycleview);
        configuraAdapter(todasNotas, listaNotas);
//        configuraLayoutManager(listaNotas); //configura o layoutmanager por codigo, porém pode-se configurar direto no xml quando ele for fixo com app:layoutManager
//                                              para colocar um gridlayout adiciona-se o app:spanCount para definir o numero de colunas
    }

    private void configuraLayoutManager(RecyclerView listaNotas) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(manager);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }
}
