function ej1() {
    let cont = false;
    let n = 0;
    let c = "";
    const s = prompt("Introduce un string");
    while (!cont) {
        c = prompt("Introduce un carácter");
        cont = c.length == 1;
        if (!cont) {
            alert("Carácter inválido");
        }
    }
    for (let index = 0; index < s.length; index++) {
        if (s.toLowerCase().charAt(index) == c.toLowerCase()) {
            n++;
        }
    }
    if (n == 0) {
        document.write(c + "no aparece en \"" + s + "\"")
    } else {
        document.write(c.toUpperCase() + " aparece " + n + " veces en \"" + s + "\"");
    }
}

function ej2() {
    function newProducto(producto){
        producto.nombre = prompt("Introduce nombre de producto: ");
        producto.precio = prompt("Introduce precio: ");
        producto.impuesto = parseInt(prompt("Introduce porcentaje de impuesto: "))/100;
    }
    let producto = {
        nombre: 'Producto genérico',
        precio: 100,
        impuesto: 0.21,
        toString: function(){
            if (isNaN(this.precio) || isNaN(this.impuesto)) {
                return 'Error: el precio/impuesto no es un número.'
            }

            return this.nombre+', '+this.precio+'€, Impuesto: '+this.impuesto*100+'%';
        }
    };
    newProducto(producto);
    console.log(producto.toString());  
}

function ej3() {

    function introString(){
        let nombre = prompt('Introduce nombre');
        let apellidos = prompt('Introduce apellidos');
        let telefono = prompt('Introduce telefono');
        let email = prompt('Introduce email');
        let codigopostal = prompt('Introduce codigo postal');
        return nombre+':'+apellidos+':'+telefono+':'+email+':'+codigopostal;
    }
    const s = introString();
    console.log(s.toUpperCase());
    console.log(s.replaceAll(':',', '));
    console.log(s.split(':')[1]);
    console.log(s.split(':')[2].replaceAll('6','9'));
    console.log(s.split(':')[3].split('@')[1]);

}