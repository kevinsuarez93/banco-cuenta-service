package ec.com.banco.cuenta.infrastructure.cuenta.out.db;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Cuenta;
import ec.com.banco.cuenta.domain.cuenta.models.Filtro;
import ec.com.banco.cuenta.domain.cuenta.repositories.CuentaRepository;
import ec.com.banco.cuenta.infrastructure.common.repositories.JPABaseRepository;
import ec.com.banco.cuenta.infrastructure.cuenta.entities.CuentaEntity;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.CuentaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static ec.com.banco.cuenta.infrastructure.cuenta.entities.QCuentaEntity.cuentaEntity;
import static ec.com.banco.cuenta.infrastructure.cuenta.entities.QMovimientoEntity.movimientoEntity;
@Repository
@Transactional
@Slf4j
public class CuentaRepositoryImpl extends JPABaseRepository<CuentaEntity, Long>
        implements CuentaRepository {

    private final CuentaMapper clienteMapper;

    private static final String NO_EXISTEN_REGISTROS = "No existen registros con los datos proporcionados";


    @PersistenceContext
    private final EntityManager entityManager;

    public CuentaRepositoryImpl(CuentaMapper clienteMapper, EntityManager entityManager) {
        super(CuentaEntity.class, entityManager);
        this.clienteMapper = clienteMapper;
        this.entityManager = entityManager;
    }

    @Override
    public void crearCuenta(Cuenta cliente) {
        this.save(clienteMapper.domainToEntity(cliente));
    }

    @Override
    public void actualizarCuenta(Cuenta cliente) throws EntidadNoEncontradaException {
        if (obtenerCuenta(cliente.getCuentaId()) == null) {
            throw new EntidadNoEncontradaException(
                    String.format("No existe compania con del codigo %s ", cliente.getCuentaId()));
        }

        CuentaEntity entity = getQueryFactory().selectFrom(cuentaEntity)
                .where(cuentaEntity.cuentaId.eq(cliente.getCuentaId())).fetchOne();
        clienteMapper.domainToEntity(cliente, entity);
        this.save(entity);
    }

    @Override
    public Cuenta obtenerCuenta(Long clienteId) {
        CuentaEntity entities = getQueryFactory().selectFrom(cuentaEntity)
                .where(cuentaEntity.cuentaId.eq(clienteId)).fetchOne();
        return clienteMapper.entityToDomain(entities);
    }

    @Override
    public void eliminarCuenta(Long clienteId) throws EntidadNoEncontradaException {
        try {
            CuentaEntity entity = getReferenceById(clienteId);
            this.delete(entity);
        } catch (EntityNotFoundException e) {
            log.error(NO_EXISTEN_REGISTROS, e.getMessage());
            throw new EntidadNoEncontradaException(e.getMessage());
        }
    }

    @Override
    public List<Cuenta> obtenerCuentas(Filtro filtro) {
        JPQLQuery<CuentaEntity> jpqlQuery = getQueryFactory().selectFrom(cuentaEntity)
                .leftJoin(cuentaEntity.movimientos, movimientoEntity).fetchJoin()
                .where(buildQuery(filtro)).distinct();
        List<CuentaEntity> entities = jpqlQuery.fetch();
        return clienteMapper.entitiesToDomains(entities);
    }

    @Override
    public List<Cuenta> obtenerCuentaPorFiltros(Filtro filtro) {
        JPQLQuery<CuentaEntity> jpqlQuery = getQueryFactory().selectFrom(cuentaEntity)
                .leftJoin(cuentaEntity.movimientos, movimientoEntity).fetchJoin()
                .where(buildQuery(filtro)).distinct();
        List<CuentaEntity> entities = jpqlQuery.fetch();
        return clienteMapper.entitiesToDomains(entities);
    }



    private BooleanBuilder buildQuery(Filtro filtro) {

        BooleanBuilder where = new BooleanBuilder();

        if (filtro.getClienteId() != null) {
            where.and(cuentaEntity.clienteId.eq(filtro.getClienteId()));
        }

        if (filtro.getFechaInicio() != null && filtro.getFechaFinal()  != null) {
            // 📌 Convertir Date a LocalDate y asegurar 00:00:00 en fechaInicio
            LocalDateTime inicioDateTime = filtro.getFechaInicio() .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .atStartOfDay(); // 📌 2025-01-11 00:00:00

            // 📌 Convertir Date a LocalDate y asegurar 23:59:59 en fechaFin
            LocalDateTime finDateTime = filtro.getFechaFinal().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .atTime(23, 59, 59); // 📌 2025-01-11 23:59:59

            // 📌 Convertir LocalDateTime de nuevo a Date
            Date inicio = Date.from(inicioDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Date fin = Date.from(finDateTime.atZone(ZoneId.systemDefault()).toInstant());

            // 📌 Ahora between() funciona correctamente con Date
            where.and(movimientoEntity.fecha.between(inicio, fin));
        }

        return where;
    }
}
