const fs = require('fs')
let btn = document.getElementById("comp");
let preguntas = JSON.parse(fs.readFileSync('./preguntas.json'));

btn.addEventListener('click', ()=>{
   leer(preguntas);
});
lista(preguntas);


function lista(preguntas) {
    let contLista = "";
    for (let i = 0; i < preguntas.length; i++) {
        contLista +=
            `<li class="list-group-item">
                <img class="img-circle media-object pull-left" src="./images/${i+1}.png" width="32" height="32">
                    <div class="media-body">
                        <p>${preguntas[i].pregunta}</p>
                        <p id="trA${i}"><input type="radio" id="rA${i}"> ${preguntas[i].rA}</p>
                        <p><input type="radio" id="rB${i}"> ${preguntas[i].rB}</p>
                        <p><input type="radio" id="rC${i}"> ${preguntas[i].rB}</p>
                    </div>
                </li>`;
    }
    let lista = document.getElementById('lista');
    lista.innerHTML = contLista;
}
function leer(preguntas){
    let q = [];
    for (let i = 0; i < preguntas.length; i++) {
        let a = document.getElementById("rA"+i);
        if (a.checked) {
            let ta = document.getElementById("trA"+i);
            if (preguntas[i].correcta=="a") {
                console.log("ya");
                ta.style.backgroundColor = "#00FF00";
                
            }else{
                ta.style.backgroundColor = "red";
            }
            continue;
        }
        let b = document.getElementById("rB"+i);
        if (b.checked) {
            if (preguntas[i].correcta=="b") {
                b.style.backgroundColor = "green";
            }else{
                b.style.backgroundColor = "red";
            }
            continue;
        }
        let c = document.getElementById("rC"+i);
        if (a.checked) {
            if (preguntas[i].correcta=="c") {
                c.style.backgroundColor = "green";
            }else{
                c.style.backgroundColor = "red";
            }
        }
    }
}
