package com.e.validadordelogin.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.model.Fatura;
import com.e.validadordelogin.model.ItemFatura;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.retrofit.UsuarioRetrofit;
import com.e.validadordelogin.retrofit.service.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosDoUsuariosActivity extends AppCompatActivity {

    private TextView nome, idade, login, email, textViewExtrato;
    private Button sair;
    SharedPreferences prf;
    Intent intent;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_do_usuarios);
        intanciaViews();
        buscaUsuario();

        prf = getSharedPreferences("detalhe_usario", MODE_PRIVATE);
        login.setText(prf.getString("cpf_ou_email", ""));
        buscaFaturaApi();
        btnSair();
    }

    private void btnSair() {
        sair.setOnClickListener(v -> {
            editor = prf.edit();
            editor.clear();
            editor.apply();
            intent = new Intent(DadosDoUsuariosActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void buscaUsuario() {

        UsuarioService usuarioService = new UsuarioRetrofit().getUsuarioService();
        Call<Usuario> callUsuario = usuarioService.buscaUsuarioApi();

        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario user = response.body();

                    nome.setText(user.getNome());
                    idade.setText(user.getIdade());
                    email.setText(user.getEmail());
                    String idUsuario = user.getIdUsuario();
                    editor = prf.edit();
                    editor.putString("id", idUsuario);
                    editor.commit();

                } else {
                    Toast.makeText(DadosDoUsuariosActivity.this, "Erro" + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(DadosDoUsuariosActivity.this, "Erro" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscaFaturaApi() {
        UsuarioService usuarioService = new UsuarioRetrofit().getUsuarioService();
        Call<Fatura> callFatura = usuarioService.
                buscaFatura(prf.getString("id", ""));

        callFatura.enqueue(new Callback<Fatura>() {
            @Override
            public void onResponse(Call<Fatura> call, Response<Fatura> response) {
                if (response.isSuccessful()) {
                    String texto = "";
                    Fatura fatura = response.body();

                    for (ItemFatura itens : fatura.getItemFaturas()) {
                        texto += itens.mostraPreco(itens.getValor()) + " \n";
                    }
                    textViewExtrato.setText(texto);
                }
            }

            @Override
            public void onFailure(Call<Fatura> call, Throwable t) {
                Log.i("ERROR EXTRATO", "FALHA");
            }
        });
    }

    private void intanciaViews() {
        nome = findViewById(R.id.text_view_nome);
        idade = findViewById(R.id.text_view_idade);
        email = findViewById(R.id.text_view_email);
        login = findViewById(R.id.text);
        sair = findViewById(R.id.btn_sair);
        textViewExtrato = findViewById(R.id.text_view_extrato);
    }
}
