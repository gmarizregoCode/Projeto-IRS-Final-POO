package tps.tp4.modelo;

import tps.tp4.excecoes.NifDuplicadoException;
import tps.tp4.excecoes.NifInvalidoException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseDeDados implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Contribuinte> utilizadores;

    public BaseDeDados() {
        this.utilizadores = new HashMap<>();
    }

    public void registarContribuinte(Contribuinte c) throws NifInvalidoException, NifDuplicadoException {
        if (!c.getNif().matches("^[0-9]{9}$")) {
            throw new NifInvalidoException("O NIF introduzido é inválido. Tem de conter exatamente 9 algarismos.");
        }

        if (utilizadores.containsKey(c.getNif())) {
            throw new NifDuplicadoException("O NIF " + c.getNif() + " já se encontra registado no sistema.");
        }

        utilizadores.put(c.getNif(), c);
    }

    // O verdadeiro Login: cruza o NIF com a senha
    public Contribuinte autenticar(String nif, String senha) {
        Contribuinte c = utilizadores.get(nif);
        if (c != null && c.getSenha().equals(senha)) {
            return c;
        }
        return null;
    }
}