document.addEventListener('DOMContentLoaded', () => {
    const btnCargar = document.getElementById('btn-cargar-lockers');
    const contenedor = document.getElementById('contenedor-lockers');

    btnCargar.addEventListener('click', () => {
        // Hacemos la petición GET a tu API local
        fetch('/api/lockers/disponibles')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error de red al intentar obtener los lockers');
                }
                return response.json();
            })
            .then(data => {
                // Limpiamos el contenedor
                contenedor.innerHTML = '';

                if (data.length === 0) {
                    contenedor.innerHTML = '<p>No hay lockers disponibles en este momento.</p>';
                    return;
                }

                // Recorremos el JSON y creamos tarjetas HTML para cada locker
                data.forEach(locker => {
                    const tarjeta = document.createElement('div');
                    tarjeta.classList.add('locker-card');

                    tarjeta.innerHTML = `
                        <h3>${locker.numeroLocker}</h3>
                        <p><strong>Ubicación:</strong> ${locker.ubicacion}</p>
                        <p><strong>Tamaño:</strong> ${locker.dimensiones}</p>
                    `;

                    contenedor.appendChild(tarjeta);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                contenedor.innerHTML = '<p style="color: red;">Error al conectar con el servidor.</p>';
            });
    });

    // --- LÓGICA DEL FORMULARIO DE ALUMNOS ---
    const formAlumno = document.getElementById('form-alumno');
    const mensajeForm = document.getElementById('mensaje-form');

    formAlumno.addEventListener('submit', (e) => {
        e.preventDefault(); // Evita que la página web se recargue al enviar el formulario

        // 1. Recopilamos los datos de las cajas de texto
        const nuevoAlumno = {
            matricula: document.getElementById('matricula').value, // ¡Agregado!
            nombre: document.getElementById('nombre').value,
            correo: document.getElementById('correo').value,
            licenciatura: document.getElementById('licenciatura').value,
            estatus: document.getElementById('estatus').value
        };

        // 2. Hacemos la petición POST a tu API
        fetch('/api/alumnos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevoAlumno)
        })
            .then(response => {
                if (response.status === 201) {
                    return response.json();
                } else {
                    throw new Error('Error al registrar al alumno');
                }
            })
            .then(data => {
                // 3. Mostramos mensaje de éxito y limpiamos
                mensajeForm.style.color = 'green';
                mensajeForm.textContent = `¡Éxito! Alumno registrado con el ID: ${data.id}`;

                formAlumno.reset(); // Limpia los campos

                // Ocultar el mensaje de éxito después de 4 segundos
                setTimeout(() => {
                    mensajeForm.textContent = '';
                }, 4000);
            })
            .catch(error => {
                console.error('Error:', error);
                mensajeForm.style.color = 'red';
                mensajeForm.textContent = 'Hubo un problema al intentar guardar el registro.';
            });
    });

    // --- LÓGICA DE ASIGNACIONES ---
    const selectAlumno = document.getElementById('select-alumno');
    const selectLocker = document.getElementById('select-locker');
    const selectTrimestre = document.getElementById('select-trimestre');
    const formAsignacion = document.getElementById('form-asignacion');
    const mensajeAsignacion = document.getElementById('mensaje-asignacion');

    // Función para llenar los selects con datos reales de tu base de datos
    function cargarListasDesplegables() {
        // 1. Cargar Alumnos (Solo mostramos los Inscritos)
        fetch('/api/alumnos').then(res => res.json()).then(data => {
            selectAlumno.innerHTML = '<option value="" disabled selected>Selecciona un alumno...</option>';
            const inscritos = data.filter(a => a.estatus === 'Inscrito');
            inscritos.forEach(alumno => {
                const matriculaTexto = alumno.matricula ? ` - ${alumno.matricula}` : '';
                selectAlumno.innerHTML += `<option value="${alumno.id}">${alumno.nombre}${matriculaTexto}</option>`;
            });
        });

        // 2. Cargar Lockers Disponibles
        fetch('/api/lockers/disponibles').then(res => res.json()).then(data => {
            selectLocker.innerHTML = '<option value="" disabled selected>Selecciona un locker...</option>';
            data.forEach(locker => {
                selectLocker.innerHTML += `<option value="${locker.id}">${locker.numeroLocker} (${locker.ubicacion})</option>`;
            });
        });

        // 3. Cargar Trimestres
        fetch('/api/trimestres').then(res => res.json()).then(data => {
            selectTrimestre.innerHTML = '<option value="" disabled selected>Selecciona el trimestre...</option>';
            data.forEach(trim => {
                selectTrimestre.innerHTML += `<option value="${trim.id}">${trim.id}</option>`;
            });
        });
    }

    // Ejecutamos la carga de listas apenas se abre la página
    cargarListasDesplegables();

    // Evento para guardar la asignación
    formAsignacion.addEventListener('submit', (e) => {
        e.preventDefault();

        const nuevaAsignacion = {
            idAlumno: selectAlumno.value,
            idLocker: selectLocker.value,
            idTrimestre: selectTrimestre.value
        };

        fetch('/api/asignaciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevaAsignacion)
        })
            .then(async response => {
                // Si hay un error de regla de negocio (Status 400), leemos el texto del backend
                if (!response.ok) {
                    const errorMessage = await response.text();
                    throw new Error(errorMessage);
                }
                return response.json();
            })
            .then(data => {
                mensajeAsignacion.style.color = 'green';
                mensajeAsignacion.textContent = '¡Locker asignado correctamente!';
                formAsignacion.reset();

                // ¡Magia! Recargamos las listas para que el locker que acabamos de usar desaparezca de las opciones
                cargarListasDesplegables();

                setTimeout(() => { mensajeAsignacion.textContent = ''; }, 4000);
            })
            .catch(error => {
                console.error('Error:', error);
                mensajeAsignacion.style.color = 'red';
                // Mostramos exactamente el error que diseñaste en Java (ej. "El alumno ya tiene un locker")
                mensajeAsignacion.textContent = error.message;
            });
    });
});