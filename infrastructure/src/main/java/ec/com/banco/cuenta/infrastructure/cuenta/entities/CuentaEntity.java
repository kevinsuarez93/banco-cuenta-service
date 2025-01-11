package ec.com.banco.cuenta.infrastructure.cuenta.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuenta")
public class CuentaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long cuentaId;

    @NotNull
    @Size(max = 20)
    @Column(name = "numero_cuenta", unique = true, nullable = false, length = 20)
    private String numeroCuenta;

    @NotNull
    @Size(max = 20)
    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta;

    @NotNull
    @Column(name = "saldo_inicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado; // Ahora es un boolean

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // Relaciones (Ejemplo: Si un cliente tiene múltiples cuentas)
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimientoEntity> movimientos;
}
