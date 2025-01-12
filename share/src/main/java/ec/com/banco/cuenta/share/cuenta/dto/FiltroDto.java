package ec.com.banco.cuenta.share.cuenta.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDto {

    private Long clienteId;
    private Date fechaInicio;
    private Date fechaFinal;
}
