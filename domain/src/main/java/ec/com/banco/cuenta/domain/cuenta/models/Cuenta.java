package ec.com.banco.cuenta.domain.cuenta.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

    private Long cuentaId;

    private Long clienteId;

    private String numeroCuenta;


    private String tipoCuenta;


    private BigDecimal saldoInicial;


    private Boolean estado;

    private List<Movimiento> movimientos = new ArrayList<>();
}
