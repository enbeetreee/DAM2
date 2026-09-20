var eventos = require('events');
var EmisorEventos = eventos.EventEmitter;
var ee = new EmisorEventos();
const fs = require('fs');


let editando = false;
let eventoActual; //id del evento con el que se está trabajando
let diaEvento;
let evLista = new Map()
let eventosArray = []//array auxiliar de eventos para que lo detecte el forEach de insertarEvento()

let horaAM = false;

let tabla = document.getElementById("tabla");

var fechaHoy = new Date();
var fecha = new Date(fechaHoy.getFullYear(), fechaHoy.getMonth(), 1);
var mesActual = fecha.getMonth();
var anyActual = fecha.getFullYear();
let hoyActive = false;
let sigActive = true;
let antActive = true;

const meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"];
function diasEnMes(m) {
    switch (m) {
        case 1:
            if (anyActual % 4 == 0 && (anyActual % 100 != 0 || anyActual % 400 == 0)) {
                return 29;
            }
            return 28;
        case 0:
        case 2:
        case 4:
        case 6:
        case 7:
        case 9:
        case 11:
            return 31;
        default:
            return 30;
    }
}

ee.emit('reloj', new Date());
leer();
printMes();


//RELOJ
ee.on('reloj', function (hora) {
    let h;
    if (horaAM) {
        h = hora.toLocaleString('es-ES', { hour: 'numeric', minute: 'numeric', hour12: true })
        document.getElementById("reloj")
            .innerHTML = h;
    } else {
        h = hora.getHours().toString();
        h = h.length == 1 ? "0" + h : h;
        let m = hora.getMinutes().toString();
        m = m.length == 1 ? "0" + m : m;
        document.getElementById("reloj")
            .innerHTML = `${h} :  ${m}`;
    }

});
setInterval(function () {
    ee.emit('reloj', new Date());
}, 1000);
document.getElementById("btnReloj").addEventListener('click',
    () => {
        if (horaAM) {
            horaAM = false;
            document.getElementById('btnReloj').innerHTML = "12h";
            ee.emit('reloj', new Date());

        } else {
            horaAM = true;
            document.getElementById('btnReloj').innerHTML = "24h";
            ee.emit('reloj', new Date());
        }
    })

//LISTENERS
document.getElementById("btnSig").addEventListener('click',
    () => {
        if (sigActive) {
            if (mesActual == 11) {
                mesActual = 0;
                anyActual++;
            } else {
                mesActual++;
            }

            fecha = new Date(anyActual, mesActual, 1);
            actBtns();
            printMes();
        }
    })
document.getElementById("btnAnt").addEventListener('click',
    () => {
        if (antActive) {
            if (mesActual == 0) {
                mesActual = 11;
                anyActual--;
            } else {
                mesActual--;
            }

            fecha = new Date(anyActual, mesActual, 1);
            actBtns();
            printMes();
        }
    })
document.getElementById("btnHoy").addEventListener('click',
    () => {
        if (hoyActive) {
            fecha = new Date(fechaHoy.getFullYear(), fechaHoy.getMonth(), 1);
            document.getElementById("btnHoy").className = "btn inactive";

            mesActual = fecha.getMonth();
            anyActual = fecha.getFullYear();
            actBtns();
            printMes();
        }

    })



//TABLA
function printMes() {
    let contPost = 1;
    let fin = false;
    let inTabla =
        `<tr>
        <th>lun</th>
        <th>mar</th>
        <th>mié</th>
        <th>jue</th>
        <th>vie</th>
        <th>sáb</th>
        <th>dom</th>
    </tr>`;
    tabla.innerHTML = inTabla;

    if (fecha.getDay() != 1) {
        let diasAnt = fecha.getDay() == 0 ? 6 : fecha.getDay() - 1;
        for (let i = 0; i < diasAnt; i++) {
            restaDia();
        }
        anterior();
    }
    while (!fin) {
        let tr = tabla.insertRow();
        for (let j = 0; j < 7; j++) {
            let td = tr.insertCell();
            if (!fin) {
                if (fecha.getDate() == fechaHoy.getDate() && fecha.getMonth() == fechaHoy.getMonth() && fecha.getFullYear() == fechaHoy.getFullYear()) {
                    td.className = "td hoy";
                    td.id = "td" + fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate();
                    td.innerHTML = fecha.getDate();

                    td.addEventListener('click', () => {
                        crearEvento(td.id.substring(2));
                    })

                    if (existeEvento(fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate())) {
                        insertarEvento(td.id);
                    }

                }


                else {
                    td.className = "td";
                    td.id = "td" + fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate();
                    td.innerHTML = fecha.getDate();

                    td.addEventListener('click', () => {
                        crearEvento(td.id.substring(2));
                    })

                    if (existeEvento(fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate())) {
                        insertarEvento(td.id);
                    }
                }
                fin = sumaDia();
            } else {
                td.className = "ant";
                td.innerHTML = contPost++;
            }
        }
    }
    document.getElementById("mes").innerHTML = meses[mesActual] + " de " + anyActual;

}
function anterior() {
    let tr = tabla.insertRow();

    for (let j = 0; j < 7; j++) {
        let td = tr.insertCell();
        if (fecha.getDate() >= 6) {
            td.className = "ant";
            td.innerHTML = fecha.getDate();
        } else {
            td.className = "td";
            td.id = "td" + fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate();
            td.innerHTML = fecha.getDate();

            td.addEventListener('click', () => {
                crearEvento(td.id.substring(2));
            })
            if (existeEvento(fecha.getFullYear() + "-" + fecha.getMonth() + "-" + fecha.getDate())) {
                insertarEvento(td.id);
            }


        }
        sumaDia();
    }
}
//OPERACIONES DATE
function sumaDia() {
    if (fecha.getDate() == diasEnMes(fecha.getMonth())) {
        fecha.setDate(1);
        if (fecha.getMonth() == 11) {
            fecha.setMonth(0);
            fecha.setFullYear(fecha.getFullYear() + 1);
        } else {
            fecha.setMonth(fecha.getMonth() + 1);
        }

        return true;
    } else {
        fecha.setDate(fecha.getDate() + 1);
        return false;
    }

}

function restaDia() {
    if (fecha.getDate() == 1) {
        if (fecha.getMonth() == 0) {
            fecha.setMonth(11);
            fecha.setFullYear(fecha.getFullYear() - 1);
        } else {
            fecha.setMonth(fecha.getMonth() - 1);
        }


        fecha.setDate(diasEnMes(fecha.getMonth()));
    } else {
        fecha.setDate(fecha.getDate() - 1);
    }
}
//BOTONES
function actBtns() {
    setHoyInactive();
    setSigInactive();
    setAntInactive();
}
function setHoyInactive() {
    if (fecha.getMonth() == fechaHoy.getMonth() && fecha.getFullYear() == fechaHoy.getFullYear()) {
        document.getElementById("btnHoy").className = "btn inactive";
        hoyActive = false;
    } else {
        document.getElementById("btnHoy").className = "btn active";
        hoyActive = true;
    }

}
function setSigInactive() {
    if (mesActual == 5) {
        document.getElementById("btnSig").className = "btn inactive";
        sigActive = false;
    } else {
        document.getElementById("btnSig").className = "btn active";
        sigActive = true;
    }
}
function setAntInactive() {
    if (mesActual == 8) {
        document.getElementById("btnAnt").className = "btn inactive";
        antActive = false;
    } else {
        document.getElementById("btnAnt").className = "btn active";
        antActive = true;
    }
}


// EVENTOS
function clearPopup() {
    editando = false;
    document.getElementById("popup1").style.display = "none";

    document.getElementById("nEvento").value = "";
    document.getElementById("dEvento").value = "";
}

document.getElementById("btnVolver").addEventListener('click',
    () => {
        if (editando) {
            document.getElementById(eventoActual).remove();
            evLista.delete(eventoActual);
            editando = false;
        }
        clearPopup();
    })

document.getElementById("btnGuardar").addEventListener('click',
    () => {
        //eventoActual parte de crearEvento() o editarEvento()
        let vacio = false;
        let evDesc = document.getElementById("dEvento");
        let evNom = document.getElementById("nEvento");

        if (editando) {
            let evDatos = evLista.get(eventoActual);
            evLista.set(eventoActual, { "id": eventoActual, "dia": evDatos.dia, "nombre": evNom.value, "desc": evDesc.value });
            document.getElementById(eventoActual).innerHTML = evNom.value;
            editando = false;
        } else {
            let nombre = evNom.value;
            let desc = evDesc.value;
            if (evNom.value.length>0) {//comprueba que el nombre tenga datos
                if (existeEvento(diaEvento)) {//comprueba si el dia tiene eventos
                    let c0 = true;
                    let c1 = true;

                    let color;
                    arrayEventos.forEach(e => {
                        let n = e.substring(e.length - 1);
                        console.log(n);
                        switch (n) {
                            case "0":
                                c0 = false;
                                break;
                            case "1":
                                c1 = false;
                                break;
                            case "2":

                        }
                    });
                    if(c0){
                        color = 0;
                    }else if(c1){
                        color = 1;
                    }else{
                        color = 2;
                    }

                    eventoActual += "_" + color
                    evLista.set(eventoActual, {
                        "id": eventoActual, "dia": diaEvento, "nombre": nombre, "desc": desc
                    });//añade evento con id+nId p.e: e2024-05-03_}
    
                } else {
    
                    eventoActual += "_0";
    
                    evLista.set(eventoActual, { "id": eventoActual, "dia": diaEvento, "nombre": nombre, "desc": desc });
                }
    
                arrayEventos = [eventoActual]
                insertarEvento("td" + diaEvento);
            }
           

            clearPopup();


        }



    })

function existeEvento(dia) {
    let b = false;

    arrayEventos = []
    evLista.keys().forEach(e => {
        if (evLista.get(e).dia == dia) {
            arrayEventos.push(e)
            b = true;
        }

    })

    return b;
}
function creaPopup1(){

    document.getElementById("popup2").style.display = "none";
    let popup = document.getElementById("popup1");
    popup.style.display = "block";

    document.getElementById("btnGuardar").innerText = "Guardar";
    document.getElementById("btnVolver").innerText = "Volver";
}
function creaPopup2(){
    document.getElementById("popup2").style.display = "block";

}
document.getElementById("btnError").addEventListener('click',()=>{
    document.getElementById("popup2").style.display = "none";
})
function crearEvento(dia) {
    if (!editando) {
        diaEvento = dia;
        eventoActual = "e" + diaEvento;
        if (existeEvento(diaEvento)) {//comprueba si el dia tiene eventos
            if (arrayEventos.length == 3) {
                creaPopup2();
            } else {
                creaPopup1();
            }
        }else{
            creaPopup1();
        }

    }

}
function editarEvento(idEvento) {
    editando = true;

    let evDesc = document.getElementById("dEvento");
    let evNom = document.getElementById("nEvento");

    diaEvento = idEvento.substring(1, 11);
    eventoActual = idEvento;

    document.getElementById("btnGuardar").innerText = "Editar";
    document.getElementById("btnVolver").innerText = "Eliminar";

    let popup = document.getElementById("popup1")
    popup.style.display = "block";

    evNom.value = evLista.get(eventoActual).nombre;
    evDesc.value = evLista.get(eventoActual).desc;
}

function insertarEvento(idTd) {
    let td = document.getElementById(idTd);
    console.log(arrayEventos)
    arrayEventos.forEach(evId => {
        let evDatos = evLista.get(evId);
        let evDiv = document.createElement("div");
        evDiv.id = evId;
        evDiv.className = "ev ev" + evId.substring(evId.length - 1);//ev + numero de evento
        console.log(evDiv.className);
        evDiv.innerHTML = evDatos.nombre
        td.appendChild(evDiv);
        guardar();
        evDiv.addEventListener('click', () => {
            editando = true;
            editarEvento(evId);
        })

    })
}

//FICHEROS
function guardar() {
    let aux = [];
    console.log(evLista.values());
    evLista.values().forEach(e => {
        aux.push({ "id": e.id, "dia": e.dia, "nombre": e.nombre, "desc": e.desc })
    });
    console.log(aux)
    fs.writeFileSync('./files/eventos.json', JSON.stringify(aux));
    console.log("Se han guardado los datos en el fichero 'eventos.json'")
}
function leer() {
    if (fs.existsSync('./files')) {
        if (fs.existsSync('./files/eventos.json')) {
            let aux = JSON.parse(fs.readFileSync('./files/eventos.json', 'utf8'));
            aux.forEach(e => {
                evLista.set(e.id, e)
            });
        }
    }
    else {
        fs.mkdirSync('./files', { recursive: true });

    }
}