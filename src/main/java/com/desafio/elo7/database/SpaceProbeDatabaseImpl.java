package com.desafio.elo7.database;

import com.desafio.elo7.database.domain.SpaceProbeData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SpaceProbeDatabaseImpl implements SpaceProbeDatabase {

    final SpaceProbeRepository spaceProbeRepository;

    @Override
    public SpaceProbeData save(SpaceProbeData spaceProbe) {
        return spaceProbeRepository.save(spaceProbe);
    }

    @Override
    public Optional<SpaceProbeData> findById(Long id) {
        return spaceProbeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        spaceProbeRepository.deleteById(id);
    }

    @Override
    public List<SpaceProbeData> findAll() {
        return spaceProbeRepository.findAll();
    }
}
