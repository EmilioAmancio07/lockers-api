package mx.cua.uam.lockersapi.service;

import mx.cua.uam.lockersapi.dto.DashboardDTO;

public interface DashboardService {
    DashboardDTO obtenerResumenAlumno(Integer idAlumno);
}