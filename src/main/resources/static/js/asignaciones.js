document.addEventListener('DOMContentLoaded', () => {

    const inputMatricula = document.getElementById('input-matricula');
    const selectLocker = document.getElementById('select-locker');
    const selectTrimestre = document.getElementById('select-trimestre');
    const formAsignacion = document.getElementById('form-asignacion');
    const mensajeAsignacion = document.getElementById('mensaje-asignacion');
    const tbodyAsignaciones = document.querySelector('#tabla-asignaciones tbody');

    // Variables globales para mapear IDs a textos visuales sin modificar el backend
    let todosLosAlumnos = [];
    let todosLosLockers = [];

    async function inicializarPantalla() {
        // 1. Descargamos catálogos completos para poder hacer cruces de datos
        const resAl = await fetch('/api/alumnos');
        todosLosAlumnos = await resAl.json();

        const resLo = await fetch('/api/lockers');
        todosLosLockers = await resLo.json();

        // 2. Llenamos las listas desplegables (Solo lockers disponibles)
        llenarSelectLockers();

        fetch('/api/trimestres').then(res => res.json()).then(data => {
            selectTrimestre.innerHTML = '<option value="" disabled selected>Selecciona el trimestre...</option>';
            data.forEach(trim => selectTrimestre.innerHTML += `<option value="${trim.id}">${trim.id}</option>`);
        });

        // 3. Cargamos la tabla de préstamos activos
        cargarTablaAsignaciones();
    }

    function llenarSelectLockers() {
        selectLocker.innerHTML = '<option value="" disabled selected>Selecciona un locker...</option>';
        const disponibles = todosLosLockers.filter(l => l.estado === 'Disponible');
        disponibles.forEach(locker => {
            selectLocker.innerHTML += `<option value="${locker.id}">${locker.numeroLocker} (${locker.ubicacion})</option>`;
        });
    }

    function cargarTablaAsignaciones() {
        fetch('/api/asignaciones')
            .then(res => res.json())
            .then(asignaciones => {
                tbodyAsignaciones.innerHTML = '';

                // Filtramos para mostrar solo los que están "Activos"
                const activas = asignaciones.filter(a => a.estatusEntrega === 'Activo');

                if(activas.length === 0) {
                    tbodyAsignaciones.innerHTML = '<tr><td colspan="4" style="text-align:center;">No hay préstamos activos.</td></tr>';
                    return;
                }

                activas.forEach(asignacion => {
                    // Buscamos los textos correspondientes a los IDs
                    const alumnoObj = todosLosAlumnos.find(a => a.id === asignacion.idAlumno);
                    const lockerObj = todosLosLockers.find(l => l.id === asignacion.idLocker);

                    const textoMatricula = alumnoObj ? (alumnoObj.matricula || 'N/A') : 'Desconocido';
                    const textoLocker = lockerObj ? lockerObj.numeroLocker : 'Desconocido';

                    const fila = document.createElement('tr');
                    fila.innerHTML = `
                        <td><strong>${textoLocker}</strong></td>
                        <td>${textoMatricula}</td>
                        <td>${asignacion.idTrimestre}</td>
                        <td>
                            <button onclick="devolverLocker(${asignacion.id})" 
                                    style="background-color: #6c757d; padding: 6px 12px; font-size: 12px; margin: 0; width: auto; border-radius: 4px;">
                                Devolver
                            </button>
                        </td>
                    `;
                    tbodyAsignaciones.appendChild(fila);
                });
            });
    }

    // --- MANEJO DEL FORMULARIO DE ASIGNACIÓN ---
    formAsignacion.addEventListener('submit', (e) => {
        e.preventDefault();
        mensajeAsignacion.textContent = '';

        const matriculaIngresada = inputMatricula.value.trim();

        // Buscamos al alumno por su matrícula en nuestro arreglo descargado
        const alumnoEncontrado = todosLosAlumnos.find(a => a.matricula === matriculaIngresada);

        if (!alumnoEncontrado) {
            mensajeAsignacion.style.color = 'red';
            mensajeAsignacion.textContent = 'Error: No existe un alumno registrado con esa matrícula.';
            return;
        }

        const nuevaAsignacion = {
            idAlumno: alumnoEncontrado.id, // ¡Aquí hacemos la traducción de matrícula a ID!
            idLocker: selectLocker.value,
            idTrimestre: selectTrimestre.value
        };

        fetch('/api/asignaciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevaAsignacion)
        })
            .then(async response => {
                if (!response.ok) throw new Error(await response.text());
                return response.json();
            })
            .then(data => {
                mensajeAsignacion.style.color = 'green';
                mensajeAsignacion.textContent = '¡Locker asignado correctamente!';
                formAsignacion.reset();

                inicializarPantalla();
                setTimeout(() => { mensajeAsignacion.textContent = ''; }, 4000);
            })
            .catch(error => {
                mensajeAsignacion.style.color = 'red';
                mensajeAsignacion.textContent = error.message;
            });
    });

    // --- FUNCIÓN GLOBAL PARA EL BOTÓN DE DEVOLVER ---
    window.devolverLocker = function(idAsignacion) {
        if(confirm('¿Estás seguro de registrar la devolución de este locker?')) {
            fetch(`/api/asignaciones/${idAsignacion}/devolver`, {
                method: 'PATCH'
            })
                .then(res => {
                    if(res.ok) {
                        // Si la devolución es exitosa, recargamos la pantalla
                        inicializarPantalla();
                    } else {
                        alert('Hubo un error al intentar devolver el locker.');
                    }
                });
        }
    };

    // Arrancamos el motor
    inicializarPantalla();
});