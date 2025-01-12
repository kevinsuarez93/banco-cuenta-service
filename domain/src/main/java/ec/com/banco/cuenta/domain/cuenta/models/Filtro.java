package ec.com.banco.cuenta.domain.cuenta.models;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filtro {

    private Long clienteId;
    private Date fechaInicio;
    private Date fechaFinal;
}
