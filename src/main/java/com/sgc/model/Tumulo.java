package com.sgc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tumulo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"setor", "fila", "numero"}) // [cite: 220] RN-004: ID único
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

    // Relación N:1 con Proprietario [cite: 220]
    @ManyToOne
    @JoinColumn(name = "id_proprietario", nullable = false)
    private Proprietario proprietario;

    // Relación 1:1 con Falecido
    @OneToOne(mappedBy = "tumulo")
    private Falecido falecido;

    public Tumulo() {
        this.estado = EstadoTumulo.DISPONIVEL; // [cite: 218] RF-001: Estado inicial
    }

    public Tumulo(String setor, String fila, String numero, TipoTumulo tipo, Proprietario proprietario) {
        this.setor = setor;
        this.fila = fila;
        this.numero = numero;
        this.tipo = tipo;
        this.proprietario = proprietario;
        this.estado = EstadoTumulo.DISPONIVEL;
    }

    // Getters y Setters
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