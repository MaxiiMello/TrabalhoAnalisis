package com.sgc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tumulo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"setor", "fila", "numero"})
})
public class Tumulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTumulo;

    @Column(nullable = false)
    private String setor;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTumulo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTumulo estado;

    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    @OneToOne(mappedBy = "tumulo")
    private Falecido falecido;

    public Tumulo() {
        this.estado = EstadoTumulo.DISPONIVEL;
    }

    public Tumulo(String setor, String fila, String numero, TipoTumulo tipo, Proprietario proprietario) {
        this.setor = setor;
        this.fila = fila;
        this.numero = numero;
        this.tipo = tipo;
        this.proprietario = proprietario;
        this.estado = EstadoTumulo.DISPONIVEL;
    }

    public int getIdTumulo() { return idTumulo; }
    public String getSetor() { return setor; }
    public String getFila() { return fila; }
    public String getNumero() { return numero; }
    public TipoTumulo getTipo() { return tipo; }
    public EstadoTumulo getEstado() { return estado; }
    public void setEstado(EstadoTumulo estado) { this.estado = estado; }
    public void setFalecido(Falecido falecido) { this.falecido = falecido; }
    public Proprietario getProprietario() { return proprietario; }
    public void setProprietario(Proprietario proprietario) { this.proprietario = proprietario; }
}