//install express, body-parser
const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');

let app = express();
// Body-parser para procesar datos JSON desde el cuerpo de las peticiones

app.use(bodyParser.json());

app.get('/bienvenida', (req, res) => {
    res.send('Hola, bienvenido/a');
});

app.get('/clientes', (req, res) => {
    let fichero = fs.readFileSync('./clientes.json');
    let clientes = JSON.parse(fichero);
    res.send(clientes);
})

app.get('/clientes/:dni', (req, res) => {
    let fichero = fs.readFileSync('./clientes.json');
    let clientes = JSON.parse(fichero);
    let clientesfl = clientes.filter(c => c.dni == req.params.dni);

    if (clientesfl.length > 0) {
        res.send(clientesfl);
    } else {
        res.send("No se han encontrado clientes con ese DNI")
    }
})

app.post('/clientes', (req, res) => {
    try {
        //obtener el cliente dado con la petición post
        let nuevoCliente = req.body;
        //leer clientes del archivo
        let fichero = fs.readFileSync('./clientes.json');
        let clientes = JSON.parse(fichero);
        //añadir el nuevo cliente:
        clientes.push(nuevoCliente);
        //guardar el fichero completo:
        fs.writeFileSync('./clientes.json', JSON.stringify(clientes));
        res.send({ ok: true });
    }
    catch (err) {
        res.send({ ok: false });
    }
});
//iniciamos el servicio en el puerto 8080
app.listen(8080);