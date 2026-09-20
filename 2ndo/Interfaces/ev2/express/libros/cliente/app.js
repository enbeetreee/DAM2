'use strict';
const fetch = require('node-fetch');
const recurso = "http://127.0.0.1:8080"

//get libros
fetch(recurso + '/libros')
    .then(res => res.json())
    .then(json => inicio(json));