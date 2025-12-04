package com.sgc.model;

import jakarta.persistence.*;
import java.util.Date;

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

    // Getters y Setters
    public int getIdFalecido() { return idFalecido; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCedula() { return cedula; }
    public void setTumulo(Tumulo tumulo) { this.tumulo = tumulo; }
    public Tumulo getTumulo() { return tumulo; }
}