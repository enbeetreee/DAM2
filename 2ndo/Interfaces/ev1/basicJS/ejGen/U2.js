
function factorial() {
    let n = prompt("Factorial de:");
    let factorial = 1;
    for (let i = 1; i >= n; i++) {
        factorial *= i;
    }
    console.log("Su factorial es ${factorial}");
}
function esParOPrimo() {
    let n = prompt("Introduce n:");
    let primo = "";
    let parImpar = n % 2 == 0 ? "par" : "impar";
    for (let i = 2; i < n; i++) {
        if (n % i == 0) {
            primo = "no ";
        }
    }
    console.log("El número es " + parImpar + " y " + primo + "es primo");
}
function piramides() {
    let s;
    for (let i = 1; i < 10; i++) {
        s = '';
        if (i % 2 != 0) {
            s = '\xa0'.repeat(10 - i) + i.toString().repeat(i) + '<br>';
            document.write(s);
            s = '\xa0'.repeat(10 - i / 2) + i.toString().repeat(i) + '\n';
            console.log(s);
        }
    }
    for (let i = 0; i < 10; i++) {
        let s = '';
        s = '\xa0'.repeat(10 - i) + i.toString().repeat(i) + '<br>';
        document.write(s);
        s = '\xa0'.repeat(10 - i / 2) + i.toString().repeat(i) + '\n';
        console.log(s);

    }
}
function tabla() {
    let f = prompt("Filas: ");
    let c = prompt("Columnas: ");
    let n = 1;
    for (let i = 0; i < f; i++) {
        for (let j = 0; j < c; j++) {
            document.write(n + '\xa0'.repeat(10 - n.toString().length * 2));
            //falla en filas concretas -> 1 es un carácter más pequeño???
            console.log(n + '\xa0'.repeat(10 - n.toString().length * 2));
            n++;
        }
        document.write('<br>');
    }

}
function tabla2() {
    const body = document.body;
    const tbl = document.createElement('table');
    let n = 1;
    tbl.style.border = '1px solid black';
    let f = prompt("Filas: ");
    let c = prompt("Columnas: ");

    for (let i = 0; i < f; i++) {
        const tr = tbl.insertRow();
        for (let j = 0; j < c; j++) {
            const td = tr.insertCell();
            td.appendChild(document.createTextNode(n.toString()));
            td.style.border = '1px solid black';
            n++;
        }
        body.appendChild(tbl);
    }
}
function palindromo() {
    let s1 = prompt('Introduce un string').replace(' ', '').toLowerCase();
    s1 = s1.replace('á', 'a');
    s1 = s1.replace('é', 'e');
    s1 = s1.replace('í', 'i');
    s1 = s1.replace('ó', 'o');
    s1 = s1.replace('ú', 'u');
    let pal = true;

    for (let i = 0; i < s1.length; i++) {
        if (s1.at(i) != s1.at(s1.length - i - 1)) {
            pal = false;
        }

    }

    document.write(pal ? 'Es un palindromo' : 'No es un palindromo');

}

function loteria() {
    let array=[];
    let auxarray;
    let cont;
    let auxn = "";
    let res = "";
    for (let i = 1; i <= 10; i++) {
        cont = false;
        while (!cont) {
            auxn = prompt('Introduce un valor');
            cont = !isNaN(auxn);
            console.log(cont);
        }
        array.push(auxn);
    }
    auxarray = array.toSorted(function(a,b){
        return (0.5-Math.random())
    });
    for (let index = 0; index < 5; index++) {
        res += auxarray[index].toString()+", ";
    }
    document.write(res.substring(0, res.length-2)+'.');

}