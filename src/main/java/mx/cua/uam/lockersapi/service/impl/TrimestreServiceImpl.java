package mx.cua.uam.lockersapi.service.impl;

import mx.cua.uam.lockersapi.dto.TrimestreDTO;
import mx.cua.uam.lockersapi.entity.Trimestre;
import mx.cua.uam.lockersapi.mapper.TrimestreMapper;
import mx.cua.uam.lockersapi.repository.TrimestreRepository;
import mx.cua.uam.lockersapi.service.TrimestreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrimestreServiceImpl implements TrimestreService {

    private final TrimestreRepository trimestreRepository;

    public TrimestreServiceImpl(TrimestreRepository trimestreRepository) {
        this.trimestreRepository = trimestreRepository;
    }

    @Override
    public List<TrimestreDTO> obtenerTodos() {
        return trimestreRepository.findAll().stream()
                .map(TrimestreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrimestreDTO guardar(TrimestreDTO trimestreDTO) {
        Trimestre trimestreEntity = TrimestreMapper.toEntity(trimestreDTO);
        Trimestre trimestreGuardado = trimestreRepository.save(trimestreEntity);
        return TrimestreMapper.toDTO(trimestreGuardado);
    }

    @Override
    public TrimestreDTO obtenerPorId(String id) {
        Trimestre trimestre = trimestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trimestre no encontrado con ID: " + id));
        return TrimestreMapper.toDTO(trimestre);
    }
}