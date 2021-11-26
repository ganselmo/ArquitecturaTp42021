
import { apiURL } from "../script.js";

const resource = "/ventas";


let productos;

let productselects;
let total = 0;

let cliente;

const getClientes = async () => {
    let res = await fetch(apiURL + "/clientes")
    let json = await res.json();
    const selector = document.querySelector(".cliente")
    json.forEach((cliente) => {
        const opt = document.createElement("option")
        opt.value = cliente.id;
        opt.innerHTML = cliente.apellido + ", " + cliente.nombre
        selector.appendChild(opt)
    })


}


const getProductos = async () => {
    let res = await fetch(apiURL + "/productos")
    let json = await res.json();
    productos = json.filter((prod) => prod.cantidad)
    agregarProducto()
}

const productera = document.querySelector(".productos-agregar")
const agregarOtro = document.querySelector("#agregarProduto")


const itemsArray = []
const agregarProducto = () => {

    const newSelect = document.createElement("select");
    newSelect.setAttribute("class", "form-select mb-2 productos-selector")
    productos.forEach(producto => {
        const option = document.createElement("option");
        option.innerHTML = producto.nombre
        option.setAttribute("data-value", producto.precio)
        option.value = producto.id
        newSelect.appendChild(option)
    });
    productera.appendChild(newSelect)
    productselects = document.querySelectorAll(".productos-selector")
    let selected = newSelect.options[newSelect.selectedIndex]

    let longitud = itemsArray.push(selected.getAttributeNode("data-value").value)

    total = itemsArray.reduce((last, news) => parseFloat(last) + parseFloat(news))
    document.querySelector("#total").innerHTML = parseFloat(total).toFixed(2)
    newSelect.addEventListener("change", (e) => {
        let selected = e.target.options[e.target.selectedIndex]
        itemsArray[longitud - 1] = selected.getAttributeNode("data-value").value

        total = itemsArray.reduce((last, news) => parseFloat(last) + parseFloat(news))
        document.querySelector("#total").innerHTML = parseFloat(total).toFixed(2)
    }
    );
    if (productselects.length >= 3) {
        agregarOtro.style.visibility = "hidden"
    }

}

agregarOtro.addEventListener("click", agregarProducto)


const reset = () => {

    total = 0;
    productera.innerHTML =""
    agregarProducto()

}



const addVenta = async () => {

    const toAdd = {}
    toAdd.cliente = {id:document.querySelector(".cliente").value}
    const producteros = document.querySelectorAll(".productos-selector")
    toAdd.productos = []
    producteros.forEach(element => {
        toAdd.productos.push({id:element.value})
    });
    
    

    let res = await fetch(apiURL + resource, {
        "method": "POST",
        "mode": "cors",
        "headers": { "Content-Type": "application/json" },
        "body": JSON.stringify(toAdd)
    })
    if(res.ok)
    {
 
        toastOkGuardado.show();
        toastNotOkGuardado.hide();
        reset();
    }
    else{
        toastNotOkGuardado.show();
        toastOkGuardado.hide();
    }
       


}

const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const toastNotGuardado = document.getElementById('notGuardado')

const toastNotOkGuardado = new bootstrap.Toast(toastNotGuardado)

const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", addVenta);

toastNotOkGuardado.hide()
toastNotOkGuardado.hide()

document.addEventListener("DOMContentLoaded", getClientes);
document.addEventListener("DOMContentLoaded", getProductos);