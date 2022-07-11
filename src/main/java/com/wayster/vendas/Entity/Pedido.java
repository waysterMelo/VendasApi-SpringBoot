package com.wayster.vendas.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate datapedido;

    @Column(precision = 20, scale = 2, name = "total")
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido")
    private List<Itens> itens;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status statusPedido;



}
