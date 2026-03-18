// static/js/lockers.js
document.addEventListener('DOMContentLoaded', () => {

    const tbodyDisponibles = document.querySelector('#tabla-disponibles tbody');
    const tbodyOcupados = document.querySelector('#tabla-ocupados tbody');
    const contDisponibles = document.getElementById('contador-disponibles');
    const contOcupados = document.getElementById('contador-ocupados');

    // Función principal para cargar y dibujar las tablas
    function cargarInventario() {
        fetch('/api/lockers')
            .then(res => res.json())
            .then(data => {
                tbodyDisponibles.innerHTML = '';
                tbodyOcupados.innerHTML = '';

                let cuentaDisponibles = 0;
                let cuentaOcupados = 0;

                data.forEach(locker => {
                    const fila = document.createElement('tr');

                    if (locker.estado === 'Disponible') {
                        fila.innerHTML = `
                            <td><strong>${locker.numeroLocker}</strong></td>
                            <td>${locker.ubicacion}</td>
                            <td>${locker.dimensiones}</td>
                            <td>
                                <button onclick="cambiarEstadoLocker(${locker.id}, 'Mantenimiento')" 
                                        style="background-color: #6c757d; padding: 5px 10px; font-size: 12px; margin: 0; width: auto; border-radius: 4px;">
                                    A Mantenimiento
                                </button>
                            </td>
                        `;
                        tbodyDisponibles.appendChild(fila);
                        cuentaDisponibles++;
                    } else {
                        const colorEstado = locker.estado === 'Mantenimiento' ? '#cf3c3a' : '#666';

                        // Solo mostramos el botón de "Hacer Disponible" si está en Mantenimiento.
                        const botonAccion = locker.estado === 'Mantenimiento'
                            ? `<button onclick="cambiarEstadoLocker(${locker.id}, 'Disponible')" style="background-color: #28a745; padding: 5px 10px; font-size: 12px; margin: 0; width: auto; border-radius: 4px;">Marcar Disponible</button>`
                            : `<span style="font-size: 12px; color: #999;">En uso</span>`;

                        fila.innerHTML = `
                            <td><strong>${locker.numeroLocker}</strong></td>
                            <td>${locker.ubicacion}</td>
                            <td>${locker.dimensiones}</td>
                            <td style="color: ${colorEstado}; font-weight: bold;">${locker.estado}</td>
                            <td>${botonAccion}</td>
                        `;
                        tbodyOcupados.appendChild(fila);
                        cuentaOcupados++;
                    }
                });

                contDisponibles.textContent = cuentaDisponibles;
                contOcupados.textContent = cuentaOcupados;
            })
            .catch(error => console.error('Error al cargar el inventario:', error));
    }

    // Arrancamos la carga al abrir la página
    cargarInventario();

    // Función global para que el botón HTML la pueda encontrar y ejecutar
    window.cambiarEstadoLocker = function(idLocker, nuevoEstado) {
        if(confirm(`¿Estás seguro de cambiar el estado de este locker a ${nuevoEstado}?`)) {
            // Hacemos la petición PATCH a la ruta que armaste en el LockerController
            fetch(`/api/lockers/${idLocker}/estado?estado=${nuevoEstado}`, {
                method: 'PATCH'
            })
                .then(res => {
                    if(res.ok) {
                        cargarInventario(); // Recargamos las tablas mágicamente
                    } else {
                        alert('Error al actualizar el estado del locker. Verifica la consola.');
                    }
                })
                .catch(error => console.error('Error de red:', error));
        }
    };
});