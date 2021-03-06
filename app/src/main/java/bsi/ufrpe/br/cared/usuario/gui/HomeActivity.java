package bsi.ufrpe.br.cared.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.gui.CuidadorHomeFragment;
import bsi.ufrpe.br.cared.cuidador.gui.CuidadorMeusServicosActivity;
import bsi.ufrpe.br.cared.cuidador.gui.PerfilCuidadorFragment;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.TipoUsuario;
import bsi.ufrpe.br.cared.pessoa.gui.PerfilPessoaFragment;
import bsi.ufrpe.br.cared.pessoa.gui.PessoaCalendarActivity;
import bsi.ufrpe.br.cared.pessoa.gui.PessoaHomeFragment;
import bsi.ufrpe.br.cared.pessoa.gui.PessoaMeusServicosActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fm;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        fm = getSupportFragmentManager();
        if(Sessao.getTipo().equals(TipoUsuario.PESSOA)) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frameLayout, new PessoaHomeFragment());
            ft.commit();
        }else{
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frameLayout, new CuidadorHomeFragment());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            if(Sessao.getTipo().equals(TipoUsuario.PESSOA)) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, new PerfilPessoaFragment());
                ft.commit();
            }else{
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, new PerfilCuidadorFragment());
                ft.commit();
            }
        } else if (id == R.id.nav_home) {
            if(Sessao.getTipo().equals(TipoUsuario.PESSOA)) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, new PessoaHomeFragment());
                ft.commit();
            }else{
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, new CuidadorHomeFragment());
                ft.commit();
            }
        } else if (id == R.id.nav_exit) {
            Sessao.logout();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_meus_servicos) {
            if (Sessao.getTipo().equals(TipoUsuario.CUIDADOR)) {
                startActivity(new Intent(HomeActivity.this, CuidadorMeusServicosActivity.class));
            } else {
                startActivity(new Intent( HomeActivity.this, PessoaCalendarActivity.class));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void irParaPerfil(){
        if(Sessao.getTipo().equals(TipoUsuario.PESSOA)) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, new PerfilPessoaFragment());
            ft.commit();
        }else{
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, new PerfilCuidadorFragment());
            ft.commit();
        }
        navigationView.setCheckedItem(R.id.nav_perfil);
    }
}
