"use strict";
const { dialog } = require('@electron/remote')
// Obtenemos el botón de buscar y el input de búsqueda
let elementoBoton = document.getElementById('boton-buscar');
let elementoInput = document.getElementById('pokemon-nombre');
let apiUrl = "https://pokeapi.co/api/v2/pokemon/";

let noEncontrado = false
// Ponemos el foco en el input de búsqueda
elementoInput.focus();


elementoBoton.addEventListener('click', () => {
    console.log(buscarPokemon(false));
})

elementoInput.addEventListener('keypress', (event) => {
    if (event.key == "Enter") {
        buscarPokemon(true)
    }
})
elementoInput.addEventListener('keyup', (event) => {
    if (event.key!="Enter") {
        buscarPokemon(false)
    }
        
    
})

function resetInput(){
    elementoInput.value = ""
    document.getElementById('pokemon-info').innerHTML =""
}

// Función para buscar un Pokémon
let buscarPokemon = (enter) => {
    // Recupera el nombre del Pokémon introducido en el input de búsqueda, convierte el nombre a minúsculas y añádelo a la URL de la API
    // Hacemos una petición a la API
    if(noEncontrado){
        document.getElementById('pokemon-info').innerHTML =""
        noEncontrado = false
    }


    if (enter && elementoInput.value == "") {
        dialog.showMessageBoxSync({ title: "Error", message: "No puedes buscar sin introducir un nombre" })
    } else {

        fetch(apiUrl + elementoInput.value.toLowerCase())
            .then(response => {
                // Si la respuesta no es correcta, lanzamos un error
                if (!response.ok) {
                    throw new Error('Pokémon no encontrado');
                }
                // Si la respuesta es correcta, devolvemos el JSON
                return response.json();
            })
            .then(data => {
                // Crea una cadena con la información del Pokémon y asigna el HTML al div con el id
                console.log(data)
                let imgSrc = JSON.stringify(data.sprites.front_default)
                
                let tipos = ""
                data.types.forEach(el => {
                    console.log(el)
                    tipos += JSON.stringify(el.type.name) + ", ";
                });
                tipos = tipos.substring(0, tipos.length - 2).replaceAll('"', '')
                document.getElementById('pokemon-info').innerHTML = `<h2>${data.name}</h2><img src=${imgSrc} title="${data.name}; tipos: ${tipos}; peso: ${data.weight.toFixed(2) / 10} Kg"/>`
                document.getElementById('pokemon-info').innerHTML += `<p><b>Tipos:</b> ${tipos}</p>`
                document.getElementById('pokemon-info').innerHTML += `<p><b>Peso:</b> ${data.weight.toFixed(2) / 10} Kg</p>`
            })
            .catch(error => {
                if(enter){
                    console.log(error)
                document.getElementById('pokemon-info').innerHTML = `<p>Pokemon no encontrado</p>`
                noEncontrado= true
            }
                // Si hay un error en la respuesta del servidor, muestra un mensaje de error en el div con el id 'pokemon-info'
            });
    }
}
// Añade un evento al botón de buscar para que al pulsar busque el Pokémon introducido en el input de búsqueda