package io.github.matheusvdlima.incidents.entity;

import io.github.matheusvdlima.incidents.enums.PrioridadeEnum;
import io.github.matheusvdlima.incidents.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_ocorrencia")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PrioridadeEnum prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private StatusEnum status;

    @Column(name = "email_responsavel", nullable = false, length = 255)
    private String emailResponsavel;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "tags", columnDefinition = "text[]")
    private String[] tags;

    @Column(name = "data_abertura", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataAbertura;

    @Column(name = "data_atualizacao")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    public void atualizar(Ocorrencia nova) {
        this.titulo = nova.titulo;
        this.descricao = nova.descricao;
        this.prioridade = nova.prioridade;
        this.status = nova.status;
        this.emailResponsavel = nova.emailResponsavel;
        this.tags = nova.tags;
    }

}
