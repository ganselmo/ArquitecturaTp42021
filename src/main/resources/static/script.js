document.addEventListener("DOMContentLoaded", iniciarindex);

function iniciarindex() {

    document.getElementById("btn-get").addEventListener("click", getJson);
    document.getElementById("save-producto").addEventListener("click", agregar);
    

    async function getJson(event) {
        event.preventDefault();
        let container = document.querySelector("#js-cuerpo");
        container.innerHTML = "<h1>Loading...</h1>";
        try {
            let response = await fetch("http://localhost:8080/producto/all", {
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

    function agregar() {
        let data = {
            "nombre": document.getElementById("input-nombre").value,
            "descripcion": document.getElementById("input-descripcion").value,
            "cantidad": parseInt(document.getElementById("input-cantidad").value),
            "precio": parseInt(document.getElementById("input-precio").value)
        }
        console.log(data);
        // post(data);
    }

    function post(data) {
        fetch("http://localhost:8080/producto", {
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
                get();
            })
            .catch(Exc => console.log(Exc));
    } 
}