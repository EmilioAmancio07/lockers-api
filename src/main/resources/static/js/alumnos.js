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
                    `;
                    tbodyAlumnos.appendChild(fila);
                });
            });
    }

    // Ejecutamos la carga al iniciar la página
    cargarTablaAlumnos();

    // Evento de guardado
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
});