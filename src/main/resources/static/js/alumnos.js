// static/js/alumnos.js
document.addEventListener('DOMContentLoaded', () => {
    const formAlumno = document.getElementById('form-alumno');
    const mensajeForm = document.getElementById('mensaje-form');
    const tbodyAlumnos = document.querySelector('#tabla-alumnos tbody');
    const contadorAlumnos = document.getElementById('contador-alumnos');

    // Función para cargar la tabla
    function cargarTablaAlumnos() {
        fetch('/api/alumnos')
            .then(res => res.json())
            .then(data => {
                tbodyAlumnos.innerHTML = ''; // Limpiamos la tabla
                contadorAlumnos.textContent = data.length;

                data.forEach(alumno => {
                    const fila = document.createElement('tr');

                    // Solo mostramos la matrícula, ocultando el nombre por privacidad
                    const matriculaSegura = alumno.matricula ? alumno.matricula : 'Sin matrícula (Registro Antiguo)';

                    fila.innerHTML = `
                        <td>${matriculaSegura}</td>
                        <td style="color: ${alumno.estatus === 'Inscrito' ? 'green' : 'red'}; font-weight: bold;">
                            ${alumno.estatus}
                        </td>
                        <td>
                            <button onclick="verTarjeta(${alumno.id})" style="padding: 6px 12px; font-size: 12px; margin: 0; width: auto; border-radius: 4px; background-color: #17a2b8;">Ver Tarjeta</button>
                        </td>
                    `;
                    tbodyAlumnos.appendChild(fila);
                });
            });
    }

    // Ejecutamos la carga al iniciar la página
    cargarTablaAlumnos();

    // Evento de guardado del formulario
    formAlumno.addEventListener('submit', (e) => {
        e.preventDefault();

        const nuevoAlumno = {
            matricula: document.getElementById('matricula').value,
            nombre: document.getElementById('nombre').value,
            correo: document.getElementById('correo').value,
            licenciatura: document.getElementById('licenciatura').value,
            estatus: document.getElementById('estatus').value
        };

        fetch('/api/alumnos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoAlumno)
        })
            .then(response => {
                if (response.status === 201) return response.json();
                throw new Error('Error al registrar');
            })
            .then(data => {
                mensajeForm.style.color = 'green';
                mensajeForm.textContent = `¡Registrado!`;
                formAlumno.reset();
                cargarTablaAlumnos(); // Recargamos la tabla al instante
                setTimeout(() => { mensajeForm.textContent = ''; }, 3000);
            })
            .catch(error => {
                mensajeForm.style.color = 'red';
                mensajeForm.textContent = 'Hubo un problema al guardar.';
            });
    });

    // --- FUNCIONES DEL MODAL Y APIs EXTERNAS ---
    window.verTarjeta = function(idAlumno) {
        const modal = document.getElementById('modal-credencial');
        modal.style.display = 'flex'; // Mostramos el modal

        // Textos de carga por si el internet es lento
        document.getElementById('cred-nombre').textContent = 'Cargando...';
        document.getElementById('cred-matricula').textContent = '...';
        document.getElementById('cred-clima').textContent = '...';
        document.getElementById('cred-asueto').textContent = '...'; // Cambiado a asueto
        document.getElementById('cred-avatar').src = '';
        document.getElementById('cred-qr').src = '';

        // Consumimos el súper endpoint
        fetch(`/api/dashboard/alumno/${idAlumno}`)
            .then(res => res.json())
            .then(data => {
                document.getElementById('cred-nombre').textContent = data.nombreAlumno;
                document.getElementById('cred-matricula').textContent = 'Matrícula: ' + data.matricula;

                // Inyectamos el clima (ahora con emoji) y el asueto
                document.getElementById('cred-clima').textContent = data.climaCuajimalpa;
                document.getElementById('cred-asueto').textContent = data.proximoAsueto;

                // Inyectamos las imágenes
                document.getElementById('cred-avatar').src = data.avatarUrl;
                document.getElementById('cred-qr').src = data.qrCodeUrl;
            })
            .catch(error => {
                console.error("Error cargando el dashboard:", error);
                document.getElementById('cred-nombre').textContent = "Error al cargar los datos";
            });
    };

    // Función para cerrar el modal dando clic a la 'X'
    window.cerrarModal = function() {
        document.getElementById('modal-credencial').style.display = 'none';
    };
});