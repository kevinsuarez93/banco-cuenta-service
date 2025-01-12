package ec.com.banco.cuenta.infrastructure.cuenta.mappers;

import ec.com.banco.cuenta.domain.cuenta.models.Filtro;
import ec.com.banco.cuenta.share.cuenta.dto.FiltroDto;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface FiltroMapper {

    Filtro dtoToDomain(FiltroDto dto);

    FiltroDto domainToDto(Filtro domain);


}
