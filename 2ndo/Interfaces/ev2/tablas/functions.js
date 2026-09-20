let fs = require('fs');

let tbody = document.getElementById("ctabla");

let editando = false;
let datos = JSON.parse(fs.readFileSync('./datos.json', 'utf8'));

printTabla();

function printTabla() {
    let ctabla = "";
    for (let i = 0; i < datos.length; i++) {
        ctabla += `<tr id="tr${i}">${trcont(i)}</tr>`
        trcont(i);
    }
    tbody.innerHTML = ctabla;
    for (let i = 0; i < datos.length; i++) {
        editarEv(i);
    }
}

function trcont(i) {
    return `<td>${datos[i].Alimento}</td>
    <td>${datos[i].Calorias}</td>
    <td>${datos[i].Grasas}</td>
    <td>${datos[i].Proteina}</td>
    <td>${datos[i].Carbohidratos}</td>
    <td><span class="editar" id="editar${i}">Editar</<span></td>`;

}

function editarEv(i) {
    document.getElementById(`editar${i}`).addEventListener('click', () => {
        if (!editando) {
            editando = true;
            editar(i);
        }
    })
}


function editar(i) {
    if (editando) {
        document.getElementById("tr" + i).innerHTML = `<td><input type="text" id="alimento" value="${datos[i].Alimento}"/></td>
            <td><input type="text" id="calorias" value="${datos[i].Calorias}"/></td>
            <td><input type="text" id="grasas" value="${datos[i].Grasas}"/></td>
            <td><input type="text" id="proteina" value="${datos[i].Proteina}"/></td>
            <td><input type="text" id="carbohidratos" value="${datos[i].Carbohidratos}"/></td>
            <td><span class="editar" id="guardar">Guardar</<span></td>`;

        document.getElementById("guardar").addEventListener('click', () => {
            guardar(i);
        })
    } else {
        dialog.showErrorBox("ERROR", "Solo puede editarse una fila a la vez")
    }
}

function guardar(i) {
    datos[i] = {
        "Alimento": document.getElementById("alimento").value,
        "Calorias": document.getElementById("calorias").value,
        "Grasas": document.getElementById("grasas").value,
        "Proteina": document.getElementById("proteina").value,
        "Carbohidratos": document.getElementById("carbohidratos").value
    }
    editando = false;
    fs.writeFileSync("./datos.json", JSON.stringify(datos));
    document.getElementById("tr" + i).innerHTML = trcont(i);
    editarEv(i);
}