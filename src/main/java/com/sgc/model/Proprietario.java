package com.sgc.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietario")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProprietario;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String cedula;

    private String telefone;
    private String endereco;
    private String email;

    // Relación 1:N con Tumulo [cite: 88]
    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Tumulo> tumulos = new ArrayList<>();

    public Proprietario() {}

    public Proprietario(String nomeCompleto, String cedula, String email) {
        this.nomeCompleto = nomeCompleto;
        this.cedula = cedula;
        this.email = email;
    }

    // Getters y Setters básicos
    public int getIdProprietario() { return idProprietario; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCedula() { return cedula; }
    public String getEmail() { return email; }
    public void setNomeCompleto(String nome) { this.nomeCompleto = nome; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setEmail(String email) { this.email = email; }
    public List<Tumulo> getTumulos() { return tumulos; }
}