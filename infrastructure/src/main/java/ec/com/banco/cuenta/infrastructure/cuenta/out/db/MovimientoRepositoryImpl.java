package ec.com.banco.cuenta.infrastructure.cuenta.out.db;

import ec.com.banco.cuenta.domain.common.exception.EntidadNoEncontradaException;
import ec.com.banco.cuenta.domain.cuenta.models.Movimiento;
import ec.com.banco.cuenta.domain.cuenta.repositories.MovimientoRepository;
import ec.com.banco.cuenta.infrastructure.common.repositories.JPABaseRepository;
import ec.com.banco.cuenta.infrastructure.cuenta.entities.MovimientoEntity;
import ec.com.banco.cuenta.infrastructure.cuenta.mappers.MovimientoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static ec.com.banco.cuenta.infrastructure.cuenta.entities.QMovimientoEntity.movimientoEntity;

@Repository
@Transactional
@Slf4j
public class MovimientoRepositoryImpl extends JPABaseRepository<MovimientoEntity, Long>
        implements MovimientoRepository {

    private final MovimientoMapper movimientoMapper;

    private static final String NO_EXISTEN_REGISTROS = "No existen registros con los datos proporcionados";


    @PersistenceContext
    private final EntityManager entityManager;

    public MovimientoRepositoryImpl(MovimientoMapper movimientoMapper, EntityManager entityManager) {
        super(MovimientoEntity.class, entityManager);
        this.movimientoMapper = movimientoMapper;
        this.entityManager = entityManager;
    }

    @Override
    public void crearMovimiento(Movimiento cliente) {
        this.save(movimientoMapper.domainToEntity(cliente));
    }

    @Override
    public void actualizarMovimiento(Movimiento cliente) throws EntidadNoEncontradaException {
        if (obtenerMovimiento(cliente.getMovimientoId()) == null) {
            throw new EntidadNoEncontradaException(
                    String.format("No existe compania con del codigo %s ", cliente.getMovimientoId()));
        }

        MovimientoEntity entity = getQueryFactory().selectFrom(movimientoEntity)
                .where(movimientoEntity.movimientoId.eq(cliente.getMovimientoId())).fetchOne();
        movimientoMapper.domainToEntity(cliente, entity);
        this.save(entity);
    }

    @Override
    public Movimiento obtenerMovimiento(Long movimientoId) {
        MovimientoEntity entities = getQueryFactory().selectFrom(movimientoEntity)
                .where(movimientoEntity.movimientoId.eq(movimientoId)).fetchOne();
        return movimientoMapper.entityToDomain(entities);
    }

    @Override
    public void eliminarMovimiento(Long movimientoId) throws EntidadNoEncontradaException {
        try {
            MovimientoEntity entity = getReferenceById(movimientoId);
            this.delete(entity);
        } catch (EntityNotFoundException e) {
            log.error(NO_EXISTEN_REGISTROS, e.getMessage());
            throw new EntidadNoEncontradaException(e.getMessage());
        }
    }
}
