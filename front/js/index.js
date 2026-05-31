const input =document.getElementById("input-original")
const imagen_original=document.getElementById("img-original")
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
    imagen_original.src = url;
    p_estado.textContent = `Imagen cargada`;
});
