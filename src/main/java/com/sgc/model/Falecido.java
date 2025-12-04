package com.sgc.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "falecido")
public class Falecido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFalecido;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Temporal(TemporalType.DATE)
    private Date dataFalecimento;

    private String causaMorte;

    @OneToOne
    @JoinColumn(name = "id_tumulo", unique = true)
    private Tumulo tumulo;

    public Falecido() {}

    public Falecido(String nomeCompleto, String cedula, Date dataFalecimento) {
        this.nomeCompleto = nomeCompleto;
        this.cedula = cedula;
        this.dataFalecimento = dataFalecimento;
    }

    public int getIdFalecido() { return idFalecido; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCedula() { return cedula; }
    public void setTumulo(Tumulo tumulo) { this.tumulo = tumulo; }
    public Tumulo getTumulo() { return tumulo; }
}