import { apiURL, constructTable } from "../script.js";



let container = document.querySelector("#drawer");
let resource = window.location.href.split("?")[1]
const drawTablaReporte = async () => {


    container.innerHTML = "<h1>Loading...</h1>";
    let data;
    try {
        let response = await fetch(apiURL + "/" + resource, {
            method: "GET",
            mode: 'cors',
        });
        if (response.ok) {
            data = await response.json();
            container.innerHTML = ""



            let tabla = document.createElement("table")
            tabla.setAttribute("id", "tabla")
            tabla.setAttribute("class", "table table-dark table-striped table-hover")

            if (Array.isArray(data)) {
                constructTable(tabla, data)
            } else {
                constructTable(tabla, [data])
            }
            container.appendChild(tabla)
        }




        return data;
    }



    catch (error) {
        console.error(error);
        container.innerHTML = "<h1>Connection error</h1>";
    };




}


const constructCard = () => {

}
document.addEventListener("DOMContentLoaded", drawTablaReporte);


