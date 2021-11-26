
import { apiURL } from "../script.js";

const resource = "/clientes";


const formNombre = document.querySelector("input[name='nombre']");
const formApellido = document.querySelector("input[name='apellido']");
const formDni = document.querySelector("input[name='dni']");

const addCliente = () => {

    if(validateInputs()){
        toastNotOkGuardado.show()
        toastOkGuardado.hide()
    }
    else{
        const toEdit = {}
        toEdit.nombre = formNombre.value;
        toEdit.apellido = formApellido.value;
        toEdit.dni = formDni.value;
    
        fetch(apiURL + resource , {
            "method": "POST",
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
                toastOkGuardado.show();
                toastNotOkGuardado.hide();
                formNombre.value ="";
                formApellido.value = "";
                formDni.value="";
            })
            .catch(Exc => console.error(Exc));
    }
    
}

const validateInputs = () => {
    return !(formNombre.value.trim()!="" && formApellido.value.trim()!="" && formDni.value.trim()!="")  
}
const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const toastNotGuardado = document.getElementById('notGuardado')
const toastNotOkGuardado = new bootstrap.Toast(toastNotGuardado)

const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", addCliente);
toastNotOkGuardado.hide()
toastNotOkGuardado.hide()