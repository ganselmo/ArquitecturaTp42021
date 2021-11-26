import { apiURL, constructTable } from "../script.js";

const resource = "/ventas";

let container = document.querySelector("#tabla");
const drawTablaProducto = async () => {


    const btnVer = document.createElement("button");
    btnVer.setAttribute("type", "button");
    btnVer.setAttribute("class", "btn btn-success me-4 btn-ver");
    btnVer.innerHTML = "Ver";

    const btnModificar = document.createElement("button");
    btnModificar.setAttribute("type", "button");
    btnModificar.setAttribute("class", "btn btn-warning me-4 btn-modificar");
    btnModificar.innerHTML = "Modificar";


    const btnEliminar = document.createElement("button");
    btnEliminar.setAttribute("type", "button");
    btnEliminar.setAttribute("class", "btn btn-danger btn-eliminar");
    btnEliminar.innerHTML = "Eliminar";


    await getTabla(container, apiURL + resource,[btnVer,btnModificar,btnEliminar]);

    const botonesVer = document.querySelectorAll(".btn-ver")
    botonesVer.forEach(btn => {
        btn.addEventListener("click", function (e) {
            verProducto(e.target.getAttributeNode("data-id").value)

        })
    })

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


const getTabla = async (container, url,botones) => {


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

            constructTable(container, newData,botones)


            console.log(data);
            return data;
        }
        else
            container.innerHTML = "<h1>Error - Failed URL!</h1>";
    }
    catch (error) {
        console.log(error);
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

const parseArgentinaDate = (f)=>{
    return f.getDate() + "-"+ f.getMonth()+ "-" +f.getFullYear();
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
let toView = {};
const verProducto = id => {

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
            toView = json
            openViewModal()

        })
}

const modalToggle = new bootstrap.Modal(document.getElementById('editModal'), {
    keyboard: false
})

const modalVer = new bootstrap.Modal(document.getElementById('verModal'), {
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


const openViewModal = () => {
    console.log(formDescripcion)

    formNombre.value = toEdit.nombre;
    formDescripcion.value = toEdit.descripcion;
    formCantidad.value = toEdit.cantidad;
    formPrecio.value = toEdit.precio;

    modalVer.toggle()
}


const toastGuardado = document.getElementById('guardado')
const toastOkGuardado = new bootstrap.Toast(toastGuardado)

const updateCliente = () => {

    toEdit.nombre = formNombre.value;
    toEdit.descripcion = formDescripcion.value;
    toEdit.cantidad = parseInt(formCantidad.value);
    toEdit.precio = formPrecio.value;
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


