const input =document.getElementById("input-original")
const imagen_Original=document.getElementById("img-original")
const imagen_Editada=document.getElementById("img-editada")
const boton=document.getElementById("btn-subir-Imagen")
const p_estado=document.getElementById("p-estado")


input.addEventListener("change", () => {
    const img = input.files[0];
    if (!img) {
        p_estado.textContent = "No se ha seleccionado ninguna imagen";
        return;
    }
    const url = URL.createObjectURL(img);
    imagen_Original.src = url;
    p_estado.textContent = `Imagen cargada`;
});


boton.addEventListener("click", () => {
    subirImagen();
});

async function subirImagen() {
    const img = input.files[0];
    if (!img) {
        p_estado.textContent = "No se ha seleccionado ninguna imagen para enviar";
        return;
    }

    const formData = new FormData();
    formData.append("image", img);
    p_estado.textContent = "Enviando imagen al servidor...";
    try {
        const respuesta = await fetch("http://127.0.0.1:8080/imagen/save", {
        method: "POST",
        body: formData,
        });
        if (!respuesta.ok) {
        const errorData = await respuesta.json();
        console.error("Error del servidor:", errorData);
        p_estado.textContent=errorData.message;
        return;
        }
        if (respuesta.ok) {
        const data = await respuesta.json();
        console.log(data);
        p_estado.textContent = "Imagen procesada exitosamente";
        imagen_Original.src ="http://127.0.0.1:8080/imagen/DesdeElfron_ten/"+ data.nombreImagenOriginal;
        imagen_Editada.src ="http://127.0.0.1:8080/imagen/DesdeElfron_ten/"+ data.nombreImagenEditada;
        }
    } catch (error) {console.error("Error al enviar la imagen:", error);}
}