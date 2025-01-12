package ec.com.banco.cuenta.domain.cuenta.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimiento {

    private Long movimientoId;

    private Long cuentaId;


    private Date fecha;


    private String tipoMovimiento;


    private BigDecimal valor;


    private BigDecimal saldo;


}
