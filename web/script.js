//Todo este apartado fue hecho por IA
async function cargarHabitaciones() {

    const respuesta = await fetch("/habitaciones");
    const datos = await respuesta.text();

    const contenedor = document.getElementById("habitaciones");

    contenedor.innerHTML = "";

    const habitaciones = datos.trim().split("\n");

    for (const habitacion of habitaciones) {

        const [numero, tipo, estado] = habitacion.split("|");

        const tarjeta = document.createElement("div");

        tarjeta.className = "habitacion";

        tarjeta.innerHTML = `
            <div class="foto ${tipo.toLowerCase()}"></div>

            <div class="informacion">

                <h3>Habitación ${numero}</h3>

                <p>${tipo}</p>

                <span class="estado ${estado.toLowerCase()}">
                    ${estado}
                </span>

            </div>
        `;

        contenedor.appendChild(tarjeta);
    }
}

document.getElementById("formulario").addEventListener(
    "submit", async function(evento) {

        evento.preventDefault();

        const datos = new URLSearchParams(new FormData(this));

        const respuesta = await fetch("/reservar", {
            method: "POST",
            body: datos
        });

        const mensaje = document.getElementById("mensaje");

        mensaje.textContent = await respuesta.text();

        if (respuesta.ok) {

            mensaje.className = "exito";

            this.reset();

            cargarHabitaciones();

        } else {

            mensaje.className = "error";
        }
    }
    
);


async function reiniciarSistema() {

    if (confirm("¿Desea eliminar todas las reservas y huéspedes registrados?")) {

        const respuesta = await fetch("/reiniciar", {
            method: "POST"
        });

        alert(await respuesta.text());

        if (respuesta.ok) {
            document.getElementById("formulario").reset();
            document.getElementById("mensaje").textContent = "";

            cargarHabitaciones();
        }
    }
}

cargarHabitaciones();