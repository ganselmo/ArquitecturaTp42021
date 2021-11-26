import { apiURL, getTabla } from "../script.js";

const resource = "/productos";

let container = document.querySelector("#tabla");
const drawTablaProducto = async ()=>
{
    await getTabla(container,apiURL + resource);

    const botonesModificar = document.querySelectorAll(".btn-modificar")
    botonesModificar.forEach(btn => {
        btn.addEventListener("click", function (e) {
            editarProducto(e.target.getAttributeNode("data-id").value)
    
        })
    })
    
    const botonesEliminar = document.querySelectorAll(".btn-eliminar")
    botonesEliminar.forEach(btn => {
        btn.addEventListener("click", function (e) {
            
            borrarProducto(e.target.getAttributeNode("data-id").value)
    
        })
    })
    
}
document.addEventListener("DOMContentLoaded", drawTablaProducto);


const borrarProducto = id => {

    fetch(apiURL + resource + "/" + id, {
        method: "DELETE",
        mode: 'cors',
    })
        .then(r => {
            if (r.ok) {

                toastOK.show()
                drawTablaProducto()
            }
            else {
                toastNotOK.show()
            }
            return r.json()
        })
        .catch(Exc => {

        });
}



const toastOKLive = document.getElementById('eliminado')
const toastOKNotLive = document.getElementById('noEliminado')
const toastOK = new bootstrap.Toast(toastOKLive)
const toastNotOK = new bootstrap.Toast(toastOKNotLive)




let toEdit = {};
const editarProducto = id => {

    fetch(apiURL + resource + "/" + id, {
        method: "GET",
        mode: 'cors',
    })
        .then(r => {
            if (!r.ok) {
                console.log("error")
            }
            return r.json()
        })
        .then(json => {
            toEdit = json
            openEditModal()

        })
}

const modalToggle = new bootstrap.Modal(document.getElementById('editModal'), {
    keyboard: false
})

const formNombre = document.querySelector("input[name='nombre']");

const formDescripcion = document.querySelector("textarea[name='descripcion']");

const formCantidad = document.querySelector("input[name='cantidad']");

const formPrecio = document.querySelector("input[name='precio']");

const openEditModal = () => {
    console.log(formDescripcion)
    
    formNombre.value = toEdit.nombre;
    formDescripcion.value = toEdit.descripcion;
    formCantidad.value = toEdit.cantidad;
    formPrecio.value = toEdit.precio;

    modalToggle.toggle()
}


const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const updateCliente = () => {

    toEdit.nombre=formNombre.value;
    toEdit.descripcion=formDescripcion.value;
    toEdit.cantidad = parseInt(formCantidad.value);
    toEdit.precio= formPrecio.value ;
    console.log(toEdit)
    fetch(apiURL + resource + "/" + toEdit.id, {
        "method": "PUT",
        "mode": "cors",
        "headers": { "Content-Type": "application/json" },
        "body": JSON.stringify(toEdit)
    })
        .then(r => {
            if (!r.ok) {
                console.log("error")
            }
            return r.json()
        })
        .then(json => {
            toastOkGuardado.show()

            drawTablaProducto()
        })
        .catch(Exc => console.log(Exc));
}

const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", updateCliente);


