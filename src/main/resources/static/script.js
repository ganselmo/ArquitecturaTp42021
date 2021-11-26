
export const apiURL = "https://tudai-arqui-tp5.herokuapp.com/api/v1";
// export const apiURL = "http://localhost:8080/api/v1";

export const capitalize = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

const getHeader = async () => {
    const header = document.querySelector("#header")

    if (header) {
        let res = await fetch("/header.html");
        let headerHtml = await res.text()

        header.innerHTML = headerHtml
    }


}

getHeader()

export const getTabla = async (container, url, botones = []) => {


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
            constructTable(container, data, botones)

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



export const constructTable = (container, data, botones = []) => {

    if (data.length < 1) {
        return
    }
    const trhead = document.createElement('tr');

    const headers = Object.keys(data[0]).map(element => capitalize(element));


    headers.forEach(element => {
        const th = document.createElement('th');
        th.innerHTML = element
        trhead.appendChild(th);

    })


    if (botones.length != 0) {
        const accion = document.createElement('th');
        accion.innerHTML = "Acción";
        accion.setAttribute("class", "accion");
        trhead.appendChild(accion);
    }


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

        // tdAccion.setAttribute("class","d-flex justify-content-around")
        // tdAccion.setAttribute("style", "width:230px;")



        botones.forEach(
            (boton) => {

                boton.setAttribute("data-id", element.id);
                tdAccion.appendChild(boton.cloneNode(true))
            }
        )





        if (botones.length != 0) {
            tr.appendChild(tdAccion)
        }


        tbody.appendChild(tr)

    });

    tbody.setAttribute("align", "center")
    container.appendChild(tbody)


}