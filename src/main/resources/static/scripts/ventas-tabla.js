import { apiURL, constructTable } from "../script.js";

const resource = "/ventas";

let container = document.querySelector("#tabla");
const drawTablaProducto = async () => {


    // const btnVer = document.createElement("button");
    // btnVer.setAttribute("type", "button");
    // btnVer.setAttribute("class", "btn btn-success me-4 btn-ver");
    // btnVer.innerHTML = "Ver";

    const btnModificar = document.createElement("button");
    btnModificar.setAttribute("type", "button");
    btnModificar.setAttribute("class", "btn btn-warning me-4 btn-modificar");
    btnModificar.innerHTML = "Modificar";


    const btnEliminar = document.createElement("button");
    btnEliminar.setAttribute("type", "button");
    btnEliminar.setAttribute("class", "btn btn-danger btn-eliminar");
    btnEliminar.innerHTML = "Eliminar";


    await getTabla(container, apiURL + resource, [btnModificar, btnEliminar]);

    const botonesVer = document.querySelectorAll(".btn-ver")
    botonesVer.forEach(btn => {
        btn.addEventListener("click", function (e) {
            verProducto(e.target.getAttributeNode("data-id").value)

        })
    })

    const botonesModificar = document.querySelectorAll(".btn-modificar")
    botonesModificar.forEach(btn => {
        btn.addEventListener("click", function (e) {
            editarVenta(e.target.getAttributeNode("data-id").value)

        })
    })

    const botonesEliminar = document.querySelectorAll(".btn-eliminar")
    botonesEliminar.forEach(btn => {
        btn.addEventListener("click", function (e) {

            borrarProducto(e.target.getAttributeNode("data-id").value)

        })
    })

}


const getTabla = async (container, url, botones) => {


    container.innerHTML = "<h1>Loading...</h1>";
    let data;
    try {
        let response = await fetch(url, {
            method: "GET",
            mode: 'cors',
        });
        if (response.ok) {
            data = await response.json();
            container.innerHTML = ""
            const newData = performParse(data)

            constructTable(container, newData, botones)


            return data;
        }
        else
            container.innerHTML = "<h1>Error - Failed URL!</h1>";
    }
    catch (error) {
        console.error(error);
        container.innerHTML = "<h1>Connection error</h1>";
    };

}
document.addEventListener("DOMContentLoaded", drawTablaProducto);

const performParse = data => {


    return data.map(
        venta => {



            venta.productos = venta.productos.map(function (producto) {
                return producto.nombre;
            }).join(", ")

            venta.fecha = parseArgentinaDate(new Date(venta.fecha))


            venta.cliente = venta.cliente.apellido + " " + venta.cliente.nombre
            return venta;
        }
    )
}

const parseArgentinaDate = (f) => {
    return f.getDate() + "-" + f.getMonth() + "-" + f.getFullYear();
}
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
const editarVenta = async (id) => {

    let res = await fetch(apiURL + resource + "/" + id, {
        method: "GET",
        mode: 'cors',
    })
    let json = await res.json()


    toEdit = json


    reset()
    openEditModal()

}
// let toView = {};
// const verProducto = id => {

//     fetch(apiURL + resource + "/" + id, {
//         method: "GET",
//         mode: 'cors',
//     })
//         .then(r => {
//             if (!r.ok) {
//                 console.error("error")
//             }
//             return r.json()
//         })
//         .then(json => {
//             toView = json
//             openViewModal()

//         })
// }

const modalToggle = new bootstrap.Modal(document.getElementById('editModal'), {
    keyboard: false,
    backdrop: 'static'
})



const formNombre = document.querySelector("input[name='nombre']");

const formDescripcion = document.querySelector("textarea[name='descripcion']");

const formCantidad = document.querySelector("input[name='cantidad']");

const formPrecio = document.querySelector("input[name='precio']");



const openEditModal = () => {


    modalToggle.toggle()
    let clienteSelector = document.querySelector(".cliente")
    clienteSelector.value = toEdit.cliente.id
    toEdit.productos.forEach(
        prod => {
            agregarProducto(prod.id)
        }

    )
    document.querySelector("#total").innerHTML = parseFloat(toEdit.total).toFixed(2)
    productselects = document.querySelectorAll(".productos-selector")
}


// const openViewModal = () => {

//     formNombre.value = toEdit.nombre;
//     formDescripcion.value = toEdit.descripcion;
//     formCantidad.value = toEdit.cantidad;
//     formPrecio.value = toEdit.precio;

//     modalVer.toggle()
// }


// const updateVenta = () => {


//     fetch(apiURL + resource + "/" + toEdit.id, {
//         "method": "PUT",
//         "mode": "cors",
//         "headers": { "Content-Type": "application/json" },
//         "body": JSON.stringify(toEdit)
//     })
//         .then(r => {
//             if (!r.ok) {
//                 console.error("error")
//             }
//             return r.json()
//         })
//         .then(json => {
//             toastOkGuardado.show()

//             drawTablaProducto()
//         })
//         .catch(Exc => console.error(Exc));
// }




///////////----------------------------------///////////

let productos;

let productselects;
let total = 0;

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
}

const productera = document.querySelector(".productos-agregar")
const agregarOtro = document.querySelector("#agregarProduto")


let itemsArray = []
const deleteProductoLine = (id) =>
{

}
const agregarProducto = (id=null) => {

    const masterDiv = document.createElement("div")
    masterDiv.setAttribute("class","input-group mb-2")


    
    const newSelect = document.createElement("select");
    newSelect.setAttribute("class", "form-select  productos-selector")
    
    masterDiv.appendChild(newSelect)

   
    productos.forEach(producto => {
     
        const option = document.createElement("option");
        option.innerHTML = producto.nombre
        option.setAttribute("data-value", producto.precio)
        option.value = producto.id
        newSelect.appendChild(option)
    });
    productera.appendChild(masterDiv)
    productselects = document.querySelectorAll(".productos-selector")
    if(id != null){
        newSelect.value = id
    }
  
    let selected = newSelect.options[newSelect.selectedIndex]
    let longitud = itemsArray.push(selected.getAttributeNode("data-value").value)
    const deleteButton = document.createElement("button");
    deleteButton.innerHTML = "Borrar"
    deleteButton.setAttribute("class","btn btn-danger")
    deleteButton.setAttribute("type","button")
    deleteButton.addEventListener("click",()=>
    {
        masterDiv.remove()
        agregarOtro.style.visibility = "visible"
        itemsArray.splice(longitud-1, 1)
        if(itemsArray.length==0)
        {
            document.querySelector("#total").innerHTML = parseFloat(0).toFixed(2)
        }
        else{
            total = itemsArray.reduce((last, news) => parseFloat(last) + parseFloat(news))
            document.querySelector("#total").innerHTML = parseFloat(total).toFixed(2)
        }

    })
    masterDiv.appendChild(deleteButton)

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
    else{
        agregarOtro.style.visibility ="visible"
    }
    
 

}

agregarOtro.addEventListener("click", ()=>
{
    agregarProducto()
})


const reset = () => {

    total = 0;
    productera.innerHTML = ""
    itemsArray=[]
    agregarOtro.style.visibility ="visible"
}



const updateVenta = async () => {

    const toAdd = {}
    toAdd.cliente = { id: document.querySelector(".cliente").value }
    const producteros = document.querySelectorAll(".productos-selector")
    toAdd.productos = []
    producteros.forEach(element => {
        toAdd.productos.push({ id: element.value })
    });



    let res = await fetch(apiURL + resource + "/" + toEdit.id, {
        "method": "PUT",
        "mode": "cors",
        "headers": { "Content-Type": "application/json" },
        "body": JSON.stringify(toAdd)
    })
    if (res.ok) {

        toastOkGuardado.show();
        toastNotOkGuardado.hide();
        drawTablaProducto()
    }
    else {
        toastNotOkGuardado.show();
        toastOkGuardado.hide();
    }



}

const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const toastNotGuardado = document.getElementById('notGuardado')

const toastNotOkGuardado = new bootstrap.Toast(toastNotGuardado)

const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", updateVenta);

toastNotOkGuardado.hide()
toastNotOkGuardado.hide()

document.addEventListener("DOMContentLoaded", getClientes);
document.addEventListener("DOMContentLoaded", getProductos);

