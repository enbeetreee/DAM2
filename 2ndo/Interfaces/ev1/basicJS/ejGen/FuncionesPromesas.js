function ej1(){
    
    
    let datos = [
        {nombre: "Nacho", telefono: "966112233", edad: 40},
        {nombre: "Ana", telefono: "911223344", edad: 35},
        {nombre: "Mario", telefono: "611998877", edad: 15},
        {nombre: "Laura", telefono: "633663366", edad: 17}
    ] 
   
    function nuevaPersona(p){
        datos.push(p);
    }
    function borrarPersona(tlf){
        datos = datos.filter(persona => persona.telefono!=tlf);
    }

    nuevaPersona({nombre:"Juan",telefono:"9656615564", edad: 60});
    nuevaPersona({nombre:"Rodolfo",telefono:"910011001", edad: 20});
    borrarPersona("910011001");
    console.log(datos);

    
}

function ej2(){
    function nuevaPersona(p) {

        let promesaTlf = (array, p) => {
            return new Promise((resolve, reject) => {
                let res = array.filter(persona => persona.telefono == p.telefono);
                if (res.length == 0) {
                    array.push(p);
                    console.log(p.nombre+" añadid@");
                    resolve(datos);
                } else {
                    reject("El telefono ya existe");
                };
            });
        };

        promesaTlf(datos, p).then(result => {
            datos = result;
        }).catch(error => {
            console.log("Error: " + error);
        });
    }

    function borrarPersona(tlf) {
        let aux = datos.filter(persona => persona.telefono == tlf);
        datos = datos.filter(persona => persona.telefono != tlf);
        return aux[0];
    }

    let datos = [
        { nombre: "Nacho", telefono: "966112233", edad: 40 },
        { nombre: "Ana", telefono: "911223344", edad: 35 },
        { nombre: "Mario", telefono: "611998877", edad: 15 },
        { nombre: "Laura", telefono: "633663366", edad: 17 }
    ];
    
    nuevaPersona({ nombre: "Juan", telefono: "910011001", edad: 60 });
    nuevaPersona({ nombre: "Rodolfo", telefono: "633663366", edad: 20 });
    let aux = borrarPersona("910011001");
    console.log(aux.nombre + " ha sido borrad@");
    console.log(datos);
}