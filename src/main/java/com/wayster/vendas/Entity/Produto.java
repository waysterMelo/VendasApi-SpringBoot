package com.wayster.vendas.Entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;



@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Campo Descrição é obrigatório.")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    @NotNull(message = "Campo Preço é obrigatório.")
    private BigDecimal preco;








}
