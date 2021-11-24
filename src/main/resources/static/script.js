document.addEventListener("DOMContentLoaded", iniciarindex);

function iniciarindex() {

    //env variables
    const apiURL = "http://tudai-arqui-tp5.herokuapp.com/api/v1";


    //getters
    document.getElementById("btn-get-producto").addEventListener("click", () => { getJson(apiURL + "/productos") });
    document.getElementById("btn-get-cliente").addEventListener("click", () => { getJson(apiURL + "/clientes") });
    document.getElementById("btn-get-ventas").addEventListener("click", () => { getJson(apiURL + "/ventas") });
    document.getElementById("btn-get-producto-mas-vendido").addEventListener("click", () => { getJson(apiURL + "/productos/mas-vendido") });
    document.getElementById("btn-get-ventas-por-dia").addEventListener("click", () => { getJson(apiURL + "/ventas/ventas-por-dia") });
    document.getElementById("btn-get-ventas-por-cliente").addEventListener("click", () => { getJson(apiURL + "/ventas/cliente-ventas") });

    //alta
    document.getElementById("save-producto").addEventListener("click", agregarProducto);
    document.getElementById("save-cliente").addEventListener("click", agregarCliente);
    document.getElementById("save-venta").addEventListener("click", agregarVenta);

    //baja
    document.getElementById("borrar-producto").addEventListener("click", () => { borrar(apiURL + "/productos") });
    document.getElementById("borrar-cliente").addEventListener("click", () => { borrar(apiURL + "/clientes") });
    document.getElementById("borrar-venta").addEventListener("click", () => { borrar(apiURL + "/ventas") });

    //modificacion
    document.getElementById("edit-producto").addEventListener("click", editarProducto);
    document.getElementById("edit-cliente").addEventListener("click", editarCliente);
    document.getElementById("edit-venta").addEventListener("click", editarVenta);

    async function getJson(url) {
        let container = document.querySelector("#js-cuerpo");
        container.innerHTML = "<h1>Loading...</h1>";
        let data;
        try {
            let response = await fetch(url, {
                method: "GET",
                mode: 'cors',
            });
            if (response.ok) {
                data = await response.json();
                container.textContent = JSON.stringify(data, undefined, 2);
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

    function agregarVenta() {
        let url = apiURL + "/ventas";
        let arr = [];
        let select1 = document.getElementById("select-alta-producto1").value;
        if (select1 != 0)
            arr.push({ id: select1 });
        let select2 = document.getElementById("select-alta-producto2").value;
        if (select2 != 0)
            arr.push({ id: select2 });
        let select3 = document.getElementById("select-alta-producto3").value;
        if (select3 != 0)
            arr.push({ id: select3 });

        let data = {
            "cliente": {
                "id": document.getElementById("select-alta-clientes").value
            },
            "productos": arr
        }
        post(data, url);
    }

    function agregarProducto() {
        let url = apiURL + "/productos";
        let data = {
            "nombre": document.getElementById("input-nombre-producto").value,
            "descripcion": document.getElementById("input-descripcion").value,
            "cantidad": parseInt(document.getElementById("input-cantidad").value),
            "precio": parseInt(document.getElementById("input-precio").value)
        }
        post(data, url);
    }

    function agregarCliente() {
        let url = apiURL + "/clientes";
        let data = {
            "nombre": document.getElementById("input-nombre-cliente").value,
            "apellido": document.getElementById("input-apellido").value,
            "dni": parseInt(document.getElementById("input-dni").value)
        }
        post(data, url);
    }

    function editarVenta() {
        let id = parseInt(document.getElementById("edit-id-venta").value)
        let url = apiURL + "/ventas/" + id;
        let arr = [];
        let select1 = document.getElementById("select-edit-producto1").value;
        if (select1 != 0)
            arr.push({ id: select1 });
        let select2 = document.getElementById("select-edit-producto2").value;
        if (select2 != 0)
            arr.push({ id: select2 });
        let select3 = document.getElementById("select-edit-producto3").value;
        if (select3 != 0)
            arr.push({ id: select3 });

        let data = {
            "cliente": {
                "id": document.getElementById("select-edit-clientes").value
            },
            "productos": arr
        }
        put(data, url);
    }

    function editarProducto() {
        let id = parseInt(document.getElementById("edit-id-producto").value)
        let url = apiURL + "/productos/" + id;
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
        let url = apiURL + "/clientes/" + id;
        let data = {
            "nombre": document.getElementById("edit-nombre-cliente").value,
            "apellido": document.getElementById("edit-apellido").value,
            "dni": parseInt(document.getElementById("edit-dni").value)
        }
        put(data, url);
    }

    function getClientes() {
        fetch(apiURL + "/clientes", {
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
                let altaClientes = document.getElementById("select-alta-clientes");
                let editClientes = document.getElementById("select-edit-clientes");
                json.forEach(element => {
                    let opt = document.createElement('option');
                    opt.value = element.id;
                    opt.innerHTML = element.nombre;
                    altaClientes.appendChild(opt);

                    let opt2 = document.createElement('option');
                    opt2.value = element.id;
                    opt2.innerHTML = element.nombre;
                    editClientes.appendChild(opt2);
                });
            })
            .catch(Exc => console.log(Exc));
    }

    function getProductos() {
        fetch(apiURL + "/productos", {
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
                let altaProducto1 = document.getElementById("select-alta-producto1");
                let altaProducto2 = document.getElementById("select-alta-producto2");
                let altaProducto3 = document.getElementById("select-alta-producto3");

                let editProducto1 = document.getElementById("select-edit-producto1");
                let editProducto2 = document.getElementById("select-edit-producto2");
                let editProducto3 = document.getElementById("select-edit-producto3");
                json.forEach(element => {
                    let opt = document.createElement('option');
                    opt.value = element.id;
                    opt.innerHTML = element.nombre;
                    altaProducto1.appendChild(opt);

                    let opt2 = document.createElement('option');
                    opt2.value = element.id;
                    opt2.innerHTML = element.nombre;
                    editProducto1.appendChild(opt2);

                    let opt3 = document.createElement('option');
                    opt3.value = element.id;
                    opt3.innerHTML = element.nombre;
                    altaProducto2.appendChild(opt3);

                    let opt4 = document.createElement('option');
                    opt4.value = element.id;
                    opt4.innerHTML = element.nombre;
                    editProducto2.appendChild(opt4);

                    let opt5 = document.createElement('option');
                    opt5.value = element.id;
                    opt5.innerHTML = element.nombre;
                    altaProducto3.appendChild(opt5);

                    let opt6 = document.createElement('option');
                    opt6.value = element.id;
                    opt6.innerHTML = element.nombre;
                    editProducto3.appendChild(opt6);

                });
            })
            .catch(Exc => console.log(Exc));
    }
    getClientes();
    getProductos();

    //metodos http
    //alta
    function post(data, url) {
        console.log(data);
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
                console.log(json);
            })
            .catch(Exc => console.log(Exc));
    }

    //modificacion
    function put(data, url) {
        console.log(data);
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