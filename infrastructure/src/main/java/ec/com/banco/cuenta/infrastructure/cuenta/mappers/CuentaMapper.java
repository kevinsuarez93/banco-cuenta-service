package ec.com.banco.cuenta.infrastructure.cuenta.mappers;

import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.infrastructure.cuenta.entities.CuentaEntity;
import ec.com.banco.cuenta.share.cuenta.dto.CuentaDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {


    Cuenta entityToDomain(CuentaEntity entity);

    CuentaEntity domainToEntity(Cuenta domain);

    List<Cuenta> entitiesToDomains(List<CuentaEntity> entidades);

    Cuenta dtoToDomain(CuentaDto dto);

    CuentaDto domainToDto(Cuenta domain);

    List<CuentaDto> domainsToDtos(List<Cuenta> domains);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void domainToEntity(Cuenta domain, @MappingTarget CuentaEntity entity);

}
