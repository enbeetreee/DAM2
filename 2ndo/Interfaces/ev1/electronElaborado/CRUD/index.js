const fs = require('fs')

var eventos = require('events');
var EmisorEventos = eventos.EventEmitter;
var ee = new EmisorEventos();



let fichero = fs.readFileSync('./files/clientes.json');
let clientes = [];
clientes = JSON.parse(fichero);

let pos = -1;

let bfirst = document.getElementById("bfirst");
bfirst.addEventListener('click', () => {
    fill(0, clientes);
})
let bback = document.getElementById("bback");
bback.addEventListener('click', () => {
    fill((pos > 0 ? --pos : pos = 0), clientes);
})
let bnext = document.getElementById("bnext");
bnext.addEventListener('click', () => {
    console.log(pos);
    if (pos > clientes.length - 1) {
        pos = clientes.length;
        floss();
    } else {
        fill(++pos, clientes);
    }
})
let blast = document.getElementById("bnext");
blast.addEventListener('click', () => {
    pos = clientes.length - 1;
    floss();

})

function fill(pos, clientes) {
    let dni = document.getElementById("inDni");
    let nombre = document.getElementById("inName");
    let tel = document.getElementById("inTel");
    dni.value = clientes[pos].dni;
    nombre.value = clientes[pos].nombre;
    tel.value = clientes[pos].telefono;
}
function floss() {
    let dni = document.getElementById("inDni");
    let nombre = document.getElementById("inName");
    let tel = document.getElementById("inTel");
    dni.value = "";
    nombre.value = "";
    tel.value = "";
}