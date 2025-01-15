let Wad = require('web-audio-daw')
const fs = require('fs')
const { dialog } = require('@electron/remote')

let vel
let dificultad

let notas = []
let note = null

let scoreDif = {}
let ticks = 0
let tickActual
let sequencia = []
let jugar = false
let pulsable = true
let isRecord = false
let enPartida = false

printTabla()

//NOTAS
notas = fs.readdirSync("./mp3")



//LISTENERS
document.getElementById("dificultad").addEventListener('click', () => {
    if (note != null) {
        note.stop()
    }

    reset()
    printTabla()
})

document.getElementById("btnStart").addEventListener('click', () => {
    if (note != null) {
        note.stop()
        note= null
    }
    if (enPartida) {
        setTimeout(function () {
            start()
        }, 1000)
    } else {
        start()
    }
})

document.getElementById("btnPlay").addEventListener('click', () => {
    document.getElementById("menu").style.display = "none"
    document.getElementById("game").style.display = "block"
    cambio = false
})
document.getElementById("btnScore").addEventListener('click', () => {
    document.getElementById("menu").style.display = "none"
    document.getElementById("divScore").style.display = "block"
    printScore()
})
document.getElementById("btnBack1").addEventListener('click', () => {
    reset()
    document.getElementById("menu").style.display = "flex"
    document.getElementById("game").style.display = "none"
    document.getElementById("divScore").style.display = "none"
})
document.getElementById("btnBack2").addEventListener('click', () => {
    reset()
    document.getElementById("menu").style.display = "flex"
    document.getElementById("game").style.display = "none"
    document.getElementById("divScore").style.display = "none"
})
document.getElementById("btnQuit").addEventListener('click', () => {
    close()
})




//JUEGO
function start() {
    reset()
    play()
}
function play() {
    let n
    enPartida = true
    jugar = false
    sequencia = []
    tickActual = 0
    for (let i = 0; i < ticks && enPartida; i++) {
        setTimeout(function () {
            n = Math.floor(Math.random() * dificultad)
            sequencia.push(n)
            note = new Wad({ source: `./mp3/${notas[n]}` })
            if(enPartida){
            note.play()
            document.getElementById(`b${n}`).style.backgroundColor = "sandybrown"
        }
        }, vel * i)
        setTimeout(function () {
            document.getElementById(`b${n}`).style.backgroundColor = 'white'
            note.stop()
        }, vel * i + vel / 2)

    }

    setTimeout(function () {
        jugar = true
    }, vel * (ticks) - vel * 0.5)

}
function check(res) {
    if(enPartida){
    if (res != sequencia[tickActual]) {
        fail()
    } else {
        if (tickActual == ticks - 1) {
            success()

        } else {
            tickActual++
        }
    }
    }
}
function reset() {
    enPartida = false
    jugar = false
    ticks = 1
    tickActual = 0
    vel = 1000
    document.getElementById("score").innerHTML = 0
}


function fail() {
    jugar = false
    enPartida = false
    let score = setScore()
    if (isRecord) {
        isRecord = false
        setTimeout(dialog.showMessageBoxSync({ title: 'Fallo', message: `Enhorabuena, has superado el record! \nRecord : ${score[1]}` }), 100 * dificultad)
    } else
        setTimeout(dialog.showMessageBoxSync({ title: 'Fallo', message: `Enhorabuena, has superado ${score[0]} sequencias.\nRecord : ${score[1]}` }), 100 * dificultad)

    animacionFallo()
    reset()
}

function success() {
    vel -= 10 * ticks / 2
    setTimeout(function () {
        document.getElementById("score").innerHTML = ticks
        ticks++
        play()
    }, vel * 0.75)
}


//SCORE

function setScore() {
    let hScore
    let score = ticks - 1
    if (fs.existsSync('score')) {
        scoreDif = JSON.parse(fs.readFileSync('score'))
    } else {
        scoreDif = {
            "facil": 0,
            "medio": 0,
            "dificil": 0
        }
    }
    switch (Math.sqrt(dificultad)) {
        case 3:
            hScore = scoreDif.facil
            if (scoreDif.facil < score) {
                scoreDif.facil = score
            }
            break
        case 4:
            hScore = scoreDif.medio
            if (scoreDif.medio < score) {
                scoreDif.medio = score
            }
            break
        case 5:
            hScore = scoreDif.dificil
            if (scoreDif.dificil < score) {
                scoreDif.dificil = score
            }
            break
    }

    if (hScore < score) {
        saveScore(scoreDif)
        hScore = score
        isRecord = true
    }

    return [score, hScore]
}
function saveScore(scoreDif) {
    console.log(scoreDif)
    fs.writeFileSync('score', JSON.stringify(scoreDif))
}


//INTERFAZ
function printTabla() {
    let rows = document.getElementById("dificultad").value
    dificultad = rows * rows
    let n = 0
    let inTable = ""
    for (let i = 0; i < rows; i++) {
        inTable += `<tr>`
        for (let j = 0; j < rows; j++) {
            inTable += `<td id='b${n++}' class='btnTabla'></td>`
        }
        inTable += `</tr>`
    }
    document.getElementById("tabla").innerHTML = inTable
    for (let i = 0; i < rows * rows; i++) {
        btnListeners(i)
    }
}

function btnListeners(i) {
    let note
    let btn = document.getElementById(`b${i}`)

    btn.addEventListener('click', () => {

        if (jugar) {
            check(i)
        }
    })

    btn.addEventListener('mousedown', () => {
        if (pulsable && jugar) {
            btn.style.backgroundColor = 'sandybrown'
            note = new Wad({ source: `./mp3/${notas[i]}` })
            note.play()
        }
    })
    btn.addEventListener('mouseup', () => {
        if (pulsable&&jugar) {
            btn.style.backgroundColor = 'white'
            note.stop()
        }
    })
    btn.addEventListener('mouseout', () => {
        if (pulsable&&jugar)
            btn.style.backgroundColor = 'white'
    })
}

function animacionFallo() {
    pulsable = false
    for (let i = 0; i < dificultad; i++) {
        setTimeout(function () {
            document.getElementById(`b${i}`).style.backgroundColor = "sandybrown "
        }, 50 * i)
    }
    setTimeout(function () {
        for (let i = 0; i < dificultad; i++) {
            setTimeout(function () {
                document.getElementById(`b${dificultad - i - 1}`).style.backgroundColor = "white"
            }, 50 * i)
        }
        pulsable = true
    }, 75 * dificultad)

}

function printScore(){
    if (fs.existsSync('score')) {
        scoreDif = JSON.parse(fs.readFileSync('score'))
    } else {
        scoreDif = {
            "facil": 0,
            "medio": 0,
            "dificil": 0
        }
    }
    document.getElementById("scoreTable").innerHTML = 
    `<tr><th>Easy</th>
        <td>${scoreDif.facil}</td>
    </tr>
    <tr><th>Medium</th>
    <td>${scoreDif.medio}</td>
    </tr>
    <tr><th>Hard</th>
    <td>${scoreDif.dificil}</td>
    </tr>`
}
