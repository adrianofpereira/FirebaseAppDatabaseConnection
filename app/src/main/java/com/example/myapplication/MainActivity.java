package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Verifica se o usuário está logado
        if(usuario.getCurrentUser() !=null){
            Log.i("CreateUser","Usuário Logado");
        }else{
            Log.i("CreateUser","Usuário não Logado");
        }

        //Cadastro de Usuario
        usuario.createUserWithEmailAndPassword("jamilton@gmail.com","ja12345")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser","Sucesso ao Cadastrar Uusário");
                        }else {
                            Log.i("CreateUser","Erro ao Cadastrar Uusário");
                        }
                    }
                });

        DatabaseReference usuarios = referencia.child("usuarios");
        DatabaseReference produtos = referencia.child("produtos");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Firebase", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Salvar dados do usuário no Firebase
        Usuario usuario = new Usuario();
        usuario.setNome("Jamilon");
        usuario.setSobrenome("");
        usuario.setIdade(30);

        Produtos produto = new Produtos();
        produto.setDescricao("Nexus");
        produto.setMarca("LG");
        produto.setPreco(99.9);

        produtos.child("001").setValue(produto);

        usuarios.child("001").setValue("usuarios");

    }
}