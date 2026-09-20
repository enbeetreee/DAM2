function menu() {
    let n = 5;
    while (n != 5) {
        n = prompt("1. Cara o Cruz\n2. Entrar a discoteca\n3. Compara Cadenas\n4. Discos\n5. Salir");
        switch (n) {
            case 1:
                p1();
                break;
            case 2:
                p2();
                break;
            case 3:
                p3();
                break;
            case 4:
                p4();
                break;
            case 5:
                alert("Saliendo...");
                break;
            default:
                alert("Error: valor inválido")
                break;
        }
    }

}

function p1() {
    let caraCruz = new Promise((resolve, reject) => {
        if (Math.floor(Math.random() * 1 == 1)) {
            resolve("Cara");
        } else {
            reject("Cruz");
        }
    });
    caraCruz().then(res => { alert(res); }).catch(error => { alert(error); });
}

function p2() {
    let mayor = edad => {
        return new Promise((resolve, reject) => {//RECUERDA RETURN
            if (edad >= 18)
                resolve("Bienvenid@");
            else
                reject("Menor de edad, vuelva en " + (18 - edad) + " años");
        });
    };
    let edad = prompt("Edad?");
    mayor(edad).then(
        res => { alert(res); }
    ).catch(
        rej => { alert(rej); }
    );
}

function p3() {
    let equalss = (s1, s2) => {
        return new Promise((resolve, reject) => {
            if (s1 == s2)
                resolve("Ya");
            else
                reject("Nay");
        });
    };
    let s1 = prompt("Son la misma cadena?\n Cadena primera:");
    let s2 = prompt("Cadena segunda:");
    equalss(s1, s2).then(ya => { alert(ya); }).catch(nay => { alert(nay); });
}

function ej4() {
    function filtrar(tipo) {
        let pIndies = new Promise((resolve, reject) => {
            let aux = discos.filter(d => d.tipo == tipo);
            if (aux.length > 0) {

                resolve([tipo, aux]);
            } else {
                reject("No hay " + tipo);
            }
        });
        pIndies.then(res => {
            mostrar(res[0], res[1]);
        }).catch(rej => console.log(rej));
    }
    function ordenGrupo() {
        discos.sort((d1, d2) => {
            if (d1.grupo > d2.grupo) { return 1; }
            else { return -1; }
        });
    }
    function splitAnyo() {
        pre70s = discos.filter(d => d.any < 1970);
        post70s = discos.filter(d => d.any >= 1970);
        mostrar("Pre años 70: ", pre70s);
        mostrar("Post años 70: ", post70s);
    }
    function mostrar(tit, array) {
        let s = "";
        array.forEach(d => {
            s += "nombre: " + d.nombre + " grupo: " + d.grupo + " año: " + d.any + " tipo: " + d.tipo + "\n";

        })
        alert(tit + "\n" + s);
    }
    let discos = [
        { nombre: "a", grupo: "a", any: 1968, tipo: "indie", local: 1, prestado: false },
        { nombre: "b", grupo: "n", any: 1970, tipo: "rock", local: 2, prestado: false },
        { nombre: "c", grupo: "b", any: 1917, tipo: "punk", local: 3, prestado: false },
        { nombre: "d", grupo: "ac", any: 1971, tipo: "indie", local: 4, prestado: false },
        { nombre: "e", grupo: "j", any: 1980, tipo: "indie", local: 5, prestado: false }
    ];
    mostrar("Premod:", discos);
    filtrar("rock");
    ordenGrupo();
    mostrar("Ordenado: ", discos);
    splitAnyo();
}