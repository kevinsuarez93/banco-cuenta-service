package ec.com.banco.cuenta.infrastructure.ejemplo.mappers;

import org.mapstruct.Mapper;

import ec.com.banco.cuenta.domain.ejemplo.producto.Categoria;
import ec.com.banco.cuenta.infrastructure.ejemplo.entities.CategoriaEntity;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toResource(CategoriaEntity categoryData);
    CategoriaEntity toData(Categoria categoryData);
}