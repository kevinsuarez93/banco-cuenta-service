package ec.com.banco.cuenta.share.cuenta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDto {

    @NotNull(groups = { Default.class, Actualizar.class })
    private Long movimientoId;

    @NotNull(groups = { Default.class, Crear.class })
    private Date fecha;

    @NotBlank(groups = { Default.class, Crear.class })
    private String tipoMovimiento;

    @NotNull(groups = { Default.class, Crear.class })
    private BigDecimal valor;

    @NotNull(groups = { Default.class, Crear.class })
    private BigDecimal saldo;

    @NotNull(groups = { Default.class, Crear.class })
    private Long cuentaId;




    // Interfaces para definir grupos
    public interface Crear {
    }

    public interface Actualizar {
    }
}
