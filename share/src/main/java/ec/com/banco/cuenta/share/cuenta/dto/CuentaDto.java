package ec.com.banco.cuenta.share.cuenta.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CuentaDto {

    @NotNull(groups = { Default.class, Actualizar.class })
    private Long cuentaId;

    @NotBlank(groups = { Default.class, Crear.class })
    @Size(max = 20, groups = { Default.class, Crear.class }) // Ajuste de longitud según BD
    private String numeroCuenta;

    @NotBlank(groups = { Default.class, Crear.class })
    @Size(max = 20, groups = { Default.class, Crear.class }) // Ajuste de longitud según BD
    private String tipoCuenta;

    @NotNull(groups = { Default.class, Crear.class }) // Cambiado de @NotBlank a @NotNull
    private BigDecimal saldoInicial;

    @NotNull(groups = { Default.class, Crear.class }) // Cambiado a Boolean para aceptar nulos
    private Boolean estado;

    @NotNull(groups = { Default.class, Crear.class })
    private Long clienteId;

    private List<MovimientoDto> movimientos = new ArrayList<>();

    // Interfaces para definir grupos
    public interface Crear {
    }

    public interface Actualizar {
    }
}
