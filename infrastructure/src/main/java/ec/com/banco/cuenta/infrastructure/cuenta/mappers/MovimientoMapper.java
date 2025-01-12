package ec.com.banco.cuenta.infrastructure.cuenta.mappers;

import ec.com.banco.cuenta.domain.cuenta.models.Movimiento;
import ec.com.banco.cuenta.infrastructure.cuenta.entities.MovimientoEntity;
import ec.com.banco.cuenta.share.cuenta.dto.MovimientoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "cuentaId", source = "entity.cuenta.cuentaId")
    Movimiento entityToDomain(MovimientoEntity entity);

    @InheritInverseConfiguration
    MovimientoEntity domainToEntity(Movimiento domain);

    List<Movimiento> entitiesToDomains(List<MovimientoEntity> entidades);

    Movimiento dtoToDomain(MovimientoDto dto);

    MovimientoDto domainToDto(Movimiento domain);

    List<MovimientoDto> domainsToDtos(List<Movimiento> domains);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void domainToEntity(Movimiento domain, @MappingTarget MovimientoEntity entity);

}
