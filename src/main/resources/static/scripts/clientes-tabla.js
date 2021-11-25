import { apiURL, capitalize } from "../script.js";

const resource = "/clientes";
const getTabla = async () => {

    let container = document.querySelector("#tabla");
    container.innerHTML = "<h1>Loading...</h1>";
    let data;
    try {
        let response = await fetch(apiURL + resource, {
            method: "GET",
            mode: 'cors',
        });
        if (response.ok) {
            data = await response.json();
            container.innerHTML = ""
            constructTable(container, data)
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

const constructTable = (container, data) => {


    const trhead = document.createElement('tr');

    const headers = Object.keys(data[0]).map(element => capitalize(element));

    headers.push("Acción")

    headers.forEach(element => {
        const th = document.createElement('th');
        th.innerHTML = element
        trhead.appendChild(th);
        if (element === "Acción") {
            th.setAttribute("style", "width:230px;")
        }
    })

    const thead = document.createElement("thead")
    thead.appendChild(trhead)
    thead.setAttribute("align", "center")
    container.appendChild(thead)

    const tbody = document.createElement("tbody")
    data.forEach(element => {

        const tr = document.createElement('tr');
        Object.values(element).forEach(element => {
            const td = document.createElement('td');
            td.innerHTML = element;
            tr.appendChild(td)
        });

        const tdAccion = document.createElement("td")
        tdAccion
        // tdAccion.setAttribute("class","d-flex justify-content-around")
        tdAccion.setAttribute("style", "width:230px;")

        const btnModificar = document.createElement("button");
        btnModificar.setAttribute("type", "button");
        btnModificar.setAttribute("class", "btn btn-warning me-4");

        btnModificar.innerHTML = "Modificar";



        btnModificar.addEventListener("click", function () {

            editarCliente(element.id)

        })
        tdAccion.appendChild(btnModificar)

        const btnEliminar = document.createElement("button");
        btnEliminar.setAttribute("type", "button");
        btnEliminar.setAttribute("class", "btn btn-danger");

        btnEliminar.innerHTML = "Eliminar";

        tdAccion.appendChild(btnEliminar)

        btnEliminar.addEventListener("click", function () {
            borrarCliente(element.id)

        })



        tr.appendChild(tdAccion)
        tbody.appendChild(tr)

    });

    tbody.setAttribute("align", "center")
    container.appendChild(tbody)


}



const borrarCliente = id => {

    fetch(apiURL + resource + "/" + id, {
        method: "DELETE",
        mode: 'cors',
    })
        .then(r => {
            if (r.ok) {

                toastOK.show()
                getTabla()
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
document.addEventListener("DOMContentLoaded", getTabla);



let toEdit = {};
const editarCliente = id => {

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

const formApellido = document.querySelector("input[name='apellido']");

const formDni = document.querySelector("input[name='dni']");

const openEditModal = () => {
    console.log(toEdit)
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
                console.log("error")
            }
            return r.json()
        })
        .then(json => {
            toastOkGuardado.show()

            getTabla()
        })
        .catch(Exc => console.log(Exc));
}
const guardarbtn = document.querySelector("#guardar")
guardarbtn.addEventListener("click", updateCliente);

