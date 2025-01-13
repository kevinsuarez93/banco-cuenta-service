package ec.com.banco.cuenta.share.cuenta.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDto {

    @NotNull()
    private Long clienteId;
    @NotNull()
    private Date fechaInicio;
    @NotNull()
    private Date fechaFinal;
}
