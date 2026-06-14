//limpiar input
document.getElementById("input").value = ""

//Promesas
let mayor = edad => {
    return new Promise((resolve, reject) => {//RECUERDA RETURN
        if (edad >= 18)
            resolve("Bienvenid@")
        else
            reject("Menor de edad, vuelva en " + (18 - edad) + " años")
    })
}
mayor(12).then(
    res => {
        alert(res)
    }
).catch(//for rejections or errors
    rej => {
        alert(rej)
    }
)

//Arrays methods
discos.sort((d1, d2) => {
    if (d1.grupo > d2.grupo) { return 1; }
    else { return -1; }
})
pre70s = discos.filter(d => d.any < 1970)

//GEN METHODS
alert(); prompt()