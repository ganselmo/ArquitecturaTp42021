document.addEventListener("DOMContentLoaded", iniciarindex);

function iniciarindex() {

    //getters
    document.getElementById("btn-get-producto").addEventListener("click", ()=>{getJson("http://localhost:8080/producto/all")});
    document.getElementById("btn-get-cliente").addEventListener("click", ()=>{getJson("http://localhost:8080/cliente/all")});

    //alta
    document.getElementById("save-producto").addEventListener("click", agregarProducto);
    document.getElementById("save-cliente").addEventListener("click", agregarCliente);

    //baja
    document.getElementById("borrar-producto").addEventListener("click", ()=>{borrar("http://localhost:8080/producto")});
    document.getElementById("borrar-cliente").addEventListener("click", ()=>{borrar("http://localhost:8080/cliente")});

    //modificacion
    document.getElementById("edit-producto").addEventListener("click", editarProducto);
    document.getElementById("edit-cliente").addEventListener("click", editarCliente);

    async function getJson(url) {
        let container = document.querySelector("#js-cuerpo");
        container.innerHTML = "<h1>Loading...</h1>";
        try {
            let response = await fetch(url, {
                method: "GET",
                mode: 'cors',
            });
            if (response.ok) {
                let data = await response.json();
                container.textContent = JSON.stringify(data, undefined, 2);
            }
            else
                container.innerHTML = "<h1>Error - Failed URL!</h1>";
        }
        catch (error) {
            console.log(error);
            container.innerHTML = "<h1>Connection error</h1>";
        };
    }

    function agregarProducto() {
        let url = "http://localhost:8080/producto";
        let data = {
            "nombre": document.getElementById("input-nombre-producto").value,
            "descripcion": document.getElementById("input-descripcion").value,
            "cantidad": parseInt(document.getElementById("input-cantidad").value),
            "precio": parseInt(document.getElementById("input-precio").value)
        }
        post(data, url);
    }

    function agregarCliente() {
        let url = "http://localhost:8080/cliente";
        let data = {
            "nombre": document.getElementById("input-nombre-cliente").value,
            "apellido": document.getElementById("input-apellido").value,
            "dni": parseInt(document.getElementById("input-dni").value)
        }
        post(data, url);
    }

    function editarProducto() {
        let id = parseInt(document.getElementById("edit-id-producto").value)
        let url = "http://localhost:8080/producto"+id;
        let data = {
            "nombre": document.getElementById("edit-nombre-producto").value,
            "descripcion": document.getElementById("edit-descripcion").value,
            "cantidad": parseInt(document.getElementById("edit-cantidad").value),
            "precio": parseInt(document.getElementById("edit-precio").value)
        }
        put(data, url);
    }

    function editarCliente() {
        let id = parseInt(document.getElementById("edit-id-cliente").value)
        let url = "http://localhost:8080/cliente/"+id;
        let data = {
            "nombre": document.getElementById("edit-nombre-cliente").value,
            "apellido": document.getElementById("edit-apellido").value,
            "dni": parseInt(document.getElementById("edit-dni").value)
        }
        put(data, url);
    }

    //metodos http
    //alta
    function post(data, url) {
        fetch(url, {
            "method": "POST",
            "mode": "cors",
            "headers": { "Content-Type": "application/json" },
            "body": JSON.stringify(data)
        })
            .then(r => {
                if (!r.ok) {
                    console.log("error")
                }
                return r.json()
            })
            .then(() => {
            })
            .catch(Exc => console.log(Exc));
    } 

    //modificacion
    function put(data, url) {
        fetch(url, {
            "method": "PUT",
            "mode": "cors",
            "headers": { "Content-Type": "application/json" },
            "body": JSON.stringify(data)
        })
            .then(r => {
                if (!r.ok) {
                    console.log("error")
                }
                return r.json()
            })
            .then(json => {
                console.log(json);
            })
            .catch(Exc => console.log(Exc));
    }

    //baja
    function borrar(url) {
        let id = parseInt(document.getElementById("input-id").value)
        fetch(url + "/" + id, {
            method: "DELETE",
            mode: 'cors',
        })
            .then(r => {
                if (!r.ok) {
                    console.log("error")
                }
                return r.json()
            })
            .then(json => {
                console.log(json);
            })
            .catch(Exc => console.log(Exc));
    }


}