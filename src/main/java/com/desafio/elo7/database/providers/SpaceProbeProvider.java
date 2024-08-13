package com.desafio.elo7.database.providers;

import com.desafio.elo7.database.SpaceProbeRepository;
import com.desafio.elo7.database.domain.SpaceProbeData;
import com.desafio.elo7.entities.SpaceProbe;
import com.desafio.elo7.utils.Converters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SpaceProbeProvider {
    final SpaceProbeRepository spaceProbeRepository;

    public SpaceProbe createSpaceProbe(final SpaceProbe spaceProbe){
        SpaceProbeData spaceProbeData = spaceProbeRepository.save(Converters.convertToSpaceProbeData(spaceProbe));
        return Converters.convertToSpaceProbe(spaceProbeData);
    }

    public SpaceProbe update(final SpaceProbe spaceProbe){
        SpaceProbeData spaceProbeData = spaceProbeRepository.save(Converters.convertToSpaceProbeData(spaceProbe));
        return Converters.convertToSpaceProbe(spaceProbeData);
    }

    public SpaceProbe findById(Long id){
        Optional<SpaceProbeData> spaceProbeData = spaceProbeRepository.findById(id);
        if (spaceProbeData.isPresent()){
            return Converters.convertToSpaceProbe(spaceProbeData.get());
        }
        return null;
    }

}
