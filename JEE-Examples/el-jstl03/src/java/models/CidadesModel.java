package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CidadesModel {

    private CidadesModel (String nome) {
        this.nome = nome;
    }
    private String nome;

    public String getNome() {
        return this.nome;
    }

    public static List <CidadesModel> find (String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(
                                 new FileReader(filePath));
        List <CidadesModel> cidades = new ArrayList <CidadesModel> ();
        while (reader.ready())
            cidades.add(new CidadesModel(reader.readLine()));
        return cidades;
    }

}
