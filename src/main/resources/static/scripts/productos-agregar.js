
import { apiURL } from "../script.js";

const resource = "/productos";



const formNombre = document.querySelector("input[name='nombre']");

const formDescripcion = document.querySelector("textarea[name='descripcion']");

const formCantidad = document.querySelector("input[name='cantidad']");

const formPrecio = document.querySelector("input[name='precio']");

const addCliente = () => {

    if(validateInputs()){
        toastNotOkGuardado.show()
        toastOkGuardado.hide()
    }
    else{
        const toEdit = {}
        toEdit.nombre = formNombre.value;
        toEdit.descripcion = formDescripcion.value;
        toEdit.cantidad = formCantidad.value;
        toEdit.precio = formPrecio.value;
    
    
        fetch(apiURL + resource , {
            "method": "POST",
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
                toastOkGuardado.show();
                toastNotOkGuardado.hide();

                formNombre.value ="";
                formDescripcion.value = "";
                formCantidad.value="";
                formPrecio.value='';
            })
            .catch(Exc => console.log(Exc));
    }
    
}

const validateInputs = () => {
    return !(formNombre.value.trim()!="" &&  formCantidad.value.trim()!="" && formPrecio.value.trim()!="")  
}
const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const toastNotGuardado = document.getElementById('notGuardado')
const toastNotOkGuardado = new bootstrap.Toast(toastNotGuardado)

const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", addCliente);
toastNotOkGuardado.hide()
toastNotOkGuardado.hide()