const fs = require('fs');
const { dialog } = require('@electron/remote');
const sound = require("sound-play");


let vel;
let dificultad;

let scoreDif = {}
let ticks;
let tickActual
let sequencia = [];
let jugar = false;
let pulsable = true;
let isRecord = false;

printTabla();

//LISTENERS
document.getElementById("dificultad").addEventListener('click', () => {
    printTabla();
})

document.getElementById("btnStart").addEventListener('click', () => {
    start();
})

document.getElementById("btnPlay").addEventListener('click', () => {
    document.getElementById("menu").style.display = "none"
    document.getElementById("game").style.display = "block"
})
/*document.getElementById("btnScore").addEventListener('click', () => {
    
})*/
document.getElementById("btnBack").addEventListener('click', () => {
    document.getElementById("menu").style.display = "flex"
    document.getElementById("game").style.display = "none"
})
document.getElementById("btnQuit").addEventListener('click', () => {
    close();
})

function btnListeners(i) {
    let btn = document.getElementById(`b${i}`);

    btn.addEventListener('click', () => {
        if (jugar) {
            check(i);
        }
    })


    btn.addEventListener('mousedown', () => {
        if (pulsable && jugar) {
            btn.style.backgroundColor = 'lightgray'
        }
    })
    btn.addEventListener('mouseup', () => {
        if (pulsable)
            btn.style.backgroundColor = 'white'
    })
    btn.addEventListener('mouseout', () => {
        if (pulsable)
            btn.style.backgroundColor = 'white'
    })
}


//JUEGO
function start() {
    score = 0;
    ticks = 1;
    vel = 1000;
    play();
}
function play() {
    let n;
    jugar = false;
    sequencia = [];
    tickActual = 0;
    for (let i = 0; i < ticks; i++) {
        setTimeout(function () {
            n = Math.floor(Math.random() * dificultad);
            sequencia.push(n);
            document.getElementById(`b${n}`).style.backgroundColor = "sandybrown ";
        }, vel * i)
        setTimeout(function () {
            document.getElementById(`b${n}`).style.backgroundColor = 'white'

        }, vel * i + vel / 2);
    }
    setTimeout(function () {
        jugar = true;
    }, vel * (ticks) - vel * 0.5)

}
function check(res) {
    if (res != sequencia[tickActual]) {
        fail();
    } else {
        if (tickActual == ticks - 1) {
            success();
            

        } else {
            tickActual++;
        }
    }

}

function fail() {
    jugar = false;
    let score = setScore();
    if(isRecord){
        isRecord = false;
        setTimeout(dialog.showMessageBoxSync({ title: 'Fallo', message: `Enhorabuena, has superado tu record! \nRecord : ${score[1]}` }), 100 * dificultad)
    }else
        setTimeout(dialog.showMessageBoxSync({ title: 'Fallo', message: `Enhorabuena, has superado ${score[0]} sequencias.\nRecord : ${score[1]}` }), 100 * dificultad)
    
    btnFallo();

    ticks = 0;
    document.getElementById("score").innerHTML = ticks;
}

function success() {
    vel -= 10 * ticks / 2;
    document.getElementById("score").innerHTML = ticks;
    ticks++;
    setTimeout(play(), vel+2000);
}


//SCORE

function setScore() {
    let hScore;
    let score = ticks -1;
    if (fs.existsSync('score')) {
        scoreDif = JSON.parse(fs.readFileSync('score'));
    } else {
        scoreDif = {
            "facil": 0,
            "medio": 0,
            "dificil": 0
        }
    }
    switch (Math.sqrt(dificultad)) {
        case 3:
            hScore = scoreDif.facil;
            if (scoreDif.facil<score) {
                scoreDif.facil = score
            }            
            break;
        case 4:
            hScore = scoreDif.medio;
            if (scoreDif.medio<score) {
                scoreDif.medio = score
            }
            break;
        case 5:
            hScore = scoreDif.dificil;
            if (scoreDif.dificil<score) {
                scoreDif.dificil = score
            }
            break;
    }

    if (hScore < score) {
        saveScore(scoreDif);
        hScore = score;
        isRecord = true;
    }
    
    return [score, hScore];
}
function saveScore(scoreDif) {
    console.log(scoreDif);
    fs.writeFileSync('score', JSON.stringify(scoreDif))
}


//INTERFAZ
function printTabla() {
    let rows = document.getElementById("dificultad").value;
    dificultad = rows * rows;
    let n = 0;
    let inTable = "";
    for (let i = 0; i < rows; i++) {
        inTable += `<tr>`;
        for (let j = 0; j < rows; j++) {
            inTable += `<td id='b${n++}' class='btn'></td>`
        }
        inTable += `</tr>`
    }
    document.getElementById("tabla").innerHTML = inTable;
    for (let i = 0; i < rows * rows; i++) {
        btnListeners(i);
    }
}

function btnFallo() {
    pulsable = false;
    for (let i = 0; i < dificultad; i++) {
        setTimeout(function () {
            document.getElementById(`b${i}`).style.backgroundColor = "sandybrown ";
        }, 50 * i)
    }
    setTimeout(function () {
        for (let i = 0; i < dificultad; i++) {
            setTimeout(function () {
                document.getElementById(`b${dificultad - i - 1}`).style.backgroundColor = "white";
            }, 50 * i)
        }
        pulsable = true;
    }, 75 * dificultad)

}
