package ru.pogosian.business.repositories;

import org.springframework.data.domain.Pageable;
import ru.pogosian.business.assembly.AssemblyOrder;
import ru.pogosian.business.cars.CarConfiguration;

import java.util.List;
import java.util.UUID;

public interface AssemblyOrderRepository {
    void save(AssemblyOrder assemblyOrder);
    AssemblyOrder findById(UUID id);
    List<AssemblyOrder> findAll(Pageable pageable);
    void deleteById(UUID id);
}
