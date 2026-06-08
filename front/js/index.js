const input = document.getElementById("input-original");
const imagen_Original = document.getElementById("img-original");
const imagen_Editada = document.getElementById("img-editada");
const imagen_Escaneada = document.getElementById("img-escaneada");
const boton = document.getElementById("btn-subir-Imagen");
const p_estado = document.getElementById("p-estado");
const tablaCoordenadas = document.getElementById("tabla-coordenadas");
const tbodyCoordenadas = document.getElementById("tbody-coordenadas");
const contenedorTablas = document.getElementById("contenedor-tablas");

input.addEventListener("change", () => {
  const img = input.files[0];
  contenedorTablas.style.display = "none";
  imagen_Editada.src = "media/demo.webp";
  imagen_Escaneada.src = "media/demo.webp";
  if (!img) {
    imagen_Original.src = "media/demo.webp";
    p_estado.textContent = "No se ha seleccionado ninguna imagen";
    p_estado.style.color = "red";
    return;
  }
  const url = URL.createObjectURL(img);
  imagen_Original.src = url;
  p_estado.textContent = `Imagen cargada`;
  p_estado.style.color = "green";
});

boton.addEventListener("click", () => {
  subirImagen();
});

async function subirImagen() {
  const img = input.files[0];
  if (!img) {
    p_estado.textContent = "No se ha seleccionado ninguna imagen para enviar";
    p_estado.style.color = "red";
    return;
  }

  const formData = new FormData();
  formData.append("image", img);
  p_estado.textContent = "Enviando imagen al servidor...";
  p_estado.style.color = "black";
  try {
    const respuesta = await fetch("http://127.0.0.1:8080/imagen/save", {
      method: "POST",
      body: formData,
    });
    if (!respuesta.ok) {
      const errorData = await respuesta.json();
      console.error("Error del servidor:", errorData);
      p_estado.textContent = errorData.message;
      p_estado.style.color = "red";
      return;
    }
    if (respuesta.ok) {
      const data = await respuesta.json();
      console.log(data);
      p_estado.textContent = "Imagen procesada exitosamente";
      p_estado.style.color = "green";
      imagen_Original.src =
        "http://127.0.0.1:8080/imagen/DesdeElfron_ten/" +
        data.nombreImagenOriginal;
      imagen_Editada.src =
        "http://127.0.0.1:8080/imagen/DesdeElfron_ten/" +
        data.nombreImagenEditada;
      imagen_Escaneada.src =
        "http://127.0.0.1:8080/imagen/DesdeElfron_ten/" +
        data.nombreImagenEscaneada;
      cargarTablaCoordenadas(data.coordenadas);
      cargarInfoAnalisis(data.anchoResultado, data.altoResultado, data.umbral);
      contenedorTablas.style.display = "flex";
    }
  } catch (error) {
    console.error("Error al enviar la imagen:", error);
  }
}

function cargarTablaCoordenadas(coordenadas) {
  tbodyCoordenadas.innerHTML = "";

  const nombresPuntos = [
    "Superior izquierda",
    "Superior derecha",
    "Inferior derecha",
    "Inferior izquierda",
  ];

  coordenadas.forEach((punto, indice) => {
    const fila = document.createElement("tr");

    fila.innerHTML = `
            <td>${nombresPuntos[indice]}</td>
            <td>${punto.x}</td>
            <td>${punto.y}</td>
        `;

    tbodyCoordenadas.appendChild(fila);
  });
}

function cargarInfoAnalisis(ancho, alto, umbral) {
  document.getElementById("ancho-resultado").textContent = ancho + " px";

  document.getElementById("alto-resultado").textContent = alto + " px";

  document.getElementById("umbral-resultado").textContent =
    Number(umbral).toFixed(2);
}
