import java.security.Timestamp;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_catalogo")
public class Catalogo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(nullable = false)
    private UUID categoriaId;
    @Column(nullable = false)
    private UUID midiaId;
    @Column(nullable = false)
    private UUID tipoMidiaId;
    @Column(nullable = false)
    private UUID classificacaoId;
    @Column(nullable = false)
    private UUID participanteId;
    @Column(length = 300, nullable = false)
    private String caminho;
    @Column(nullable = false)
    private Float valor;
    @Column(nullable = false)
    private Date dataAlteracao;
    @Column(nullable = false)
    private Date dataInativacao;
}