package ru.pogosian.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pogosian.business.cars.CarConfiguration;
import ru.pogosian.business.cars.CarModel;
import ru.pogosian.business.detail.CarDetails;
import ru.pogosian.business.repositories.CarConfigurationRepository;
import ru.pogosian.business.repositories.CarModelRepository;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class buildConfigurationServiceImpl implements BuildConfigurationService {
    private final CarModelRepository usingCarModelRepository;
    private final CarConfigurationRepository usingConfigurationRepository;

    @Transactional
    @Override
    public CarConfiguration buildCarConfiguration(UUID modelId, Set<CarDetails> usedDetails) {
        CarModel model = usingCarModelRepository.findById(modelId);
        
        BigDecimal totalPrice = model.getBasePrice();
        for (CarDetails detail : usedDetails) {
            totalPrice = totalPrice.add(detail.getDeltaPrice());
        }

        CarConfiguration configuration = CarConfiguration.builder()
                .configurationId(UUID.randomUUID())
                .configurationModelId(modelId)
                .totalPrice(totalPrice)
                .usedDetails(new HashSet<>(usedDetails))
                .build();
        usingConfigurationRepository.save(configuration);
        return configuration;
    }
}