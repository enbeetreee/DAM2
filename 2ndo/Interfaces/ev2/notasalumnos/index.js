const { dialog } = require('@electron/remote')
const fs = require('fs');

let datos = [];
let editando = false;
let tActual;

//LISTENERS
document.getElementById("btnGuardar").addEventListener('click', () => {
    let fileSave = dialog.showSaveDialogSync({
        title: "Guardando archivo json",
        defaultPath: "./files/",
        filters: [
            { name: 'JSON Files', extensions: ['json'] }
        ]
    });
    fs.writeFileSync(fileSave, JSON.stringify(datos), 'utf-8')
})

document.getElementById("btnAbrir").addEventListener('click', () => {
    let file = dialog.showOpenDialogSync({
        properties: ['openFile'],
        title: "Abriendo archivos json",
        defaultPath: "./files/",
        filters: [
            { name: 'JSON Files', extensions: ['json'] }
        ]
    });
    file.forEach(e => {
        JSON.parse(fs.readFileSync(e, 'utf-8')).forEach(a => {
            datos.push(a);
        });
    });
    printTabla();
})

function editarListeners(i) {
    document.getElementById(`tr${i}`).addEventListener('click', () => {
        if (!editando) {
            editar(i);
        } else {
            if (i != tActual) {
                resetEditar(tActual);
                editar(i);
            }

        }
    })
}

// FUNCIONES
function printTabla() {
    let inTabla = "";

    for (let i = 0; i < datos.length; i++) {
        inTabla += `<tr >
            <td>${datos[i].grupo}</td>
            <td>${datos[i].nombre}</td>
            <td id="tr${i}">${datos[i].nota}</td>
            </tr>`;

    }

    document.getElementById("tabla").innerHTML = inTabla;
    for (let i = 0; i < datos.length; i++) {
        editarListeners(i);
    }
}

function editar(i) {
    console.log(i);
    editando = true;
    tActual = i;
    let tr = document.getElementById(`tr${i}`);
    tr.innerHTML = `<input type="text" id="edit" value="${datos[i].nota}"/>`;
    tr.addEventListener('keypress', (event) => {
        if (event.key == "Enter" && editando) {
            editando = false;
            resetEditar(i);
        }
    })

}

function resetEditar(i) {

    console.log(i);
    let tr = document.getElementById(`tr${i}`);
    datos[i].nota = document.getElementById("edit").value;

    tr.innerHTML = datos[i].nota;
    editarListeners(i);

}
