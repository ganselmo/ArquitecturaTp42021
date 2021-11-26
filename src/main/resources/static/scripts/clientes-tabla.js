import { apiURL, getTabla } from "../script.js";

const resource = "/clientes";

let container = document.querySelector("#tabla");

const drawTablaCliente = async ()=>
{


    const btnModificar = document.createElement("button");
    btnModificar.setAttribute("type", "button");
    btnModificar.setAttribute("class", "btn btn-warning me-4 btn-modificar");
    btnModificar.innerHTML = "Modificar";


    const btnEliminar = document.createElement("button");
    btnEliminar.setAttribute("type", "button");
    btnEliminar.setAttribute("class", "btn btn-danger btn-eliminar");
    btnEliminar.innerHTML = "Eliminar";

    

    await getTabla(container,apiURL + resource,[btnModificar,btnEliminar]);

    const botonesModificar = document.querySelectorAll(".btn-modificar")
    botonesModificar.forEach(btn => {
        btn.addEventListener("click", function (e) {
            editarCliente(e.target.getAttributeNode("data-id").value)
    
        })
    })
    
    const botonesEliminar = document.querySelectorAll(".btn-eliminar")
    botonesEliminar.forEach(btn => {
        btn.addEventListener("click", function (e) {
            
            borrarCliente(e.target.getAttributeNode("data-id").value)
    
        })
    })
    
}



const borrarCliente = id => {

    fetch(apiURL + resource + "/" + id, {
        method: "DELETE",
        mode: 'cors',
    })
        .then(r => {
            if (r.ok) {

                toastOK.show()
                drawTablaCliente()
            }
            else {
                toastNotOK.show()
            }
            return r.json()
        })
        .then(json => {


        })
        .catch(Exc => {

        });
}



const toastOKLive = document.getElementById('eliminado')
const toastOKNotLive = document.getElementById('noEliminado')
const toastOK = new bootstrap.Toast(toastOKLive)

const toastNotOK = new bootstrap.Toast(toastOKNotLive)
document.addEventListener("DOMContentLoaded", drawTablaCliente);



let toEdit = {};
const editarCliente = id => {

    fetch(apiURL + resource + "/" + id, {
        method: "GET",
        mode: 'cors',
    })
        .then(r => {
            if (!r.ok) {
                console.error("error")
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

const formApellido = document.querySelector("input[name='apellido']");

const formDni = document.querySelector("input[name='dni']");

const openEditModal = () => {
    formNombre.value = toEdit.nombre;
    formApellido.value = toEdit.apellido;
    formDni.value = toEdit.dni;

    modalToggle.toggle()
}


const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const updateCliente = () => {

    toEdit.nombre = formNombre.value;
    toEdit.apellido = formApellido.value;
    toEdit.dni = formDni.value;

    fetch(apiURL + resource + "/" + toEdit.id, {
        "method": "PUT",
        "mode": "cors",
        "headers": { "Content-Type": "application/json" },
        "body": JSON.stringify(toEdit)
    })
        .then(r => {
            if (!r.ok) {
                console.error("error")
            }
            return r.json()
        })
        .then(json => {
            toastOkGuardado.show()

            drawTablaCliente()
        })
        .catch(Exc => console.error(Exc));
}
const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", updateCliente);

