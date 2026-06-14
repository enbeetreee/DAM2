const fs = require('fs');
const model=require('./model')
let Libro=model.Libro
const nf=require('node-fetch');
const { ipcRenderer } = require('electron');


const recurso = "http://127.0.0.1:8080";

let representaLibros = (books => {
    let cadenaDOM = "";
    let id = 0
    books.forEach(book => {
        cadenaDOM +=
            `<div id='${book.title}'>
                
                    <img src="${recurso}/public/${book.img}" height="170" width="108">
                    <br>
                    <label><strong>${book.title}</strong></label>
                    <br>
                    <label>${book.author}</label>
                
            </div>`;
    
           


        });

    document.getElementById("wrapper").innerHTML = cadenaDOM;

    books.forEach(book =>{
        document.getElementById(book.title).addEventListener('click', ()=>{
            let n1 = document.querySelector("#notification");
            let n2 = document.querySelector("#notification2");
            let n3 = document.querySelector("#notification3");
            if(!n1.opened&&!n2.opened&&!n3.opened){
                verLibro(book.title)
            }
            
        })
    })
});



let buscarTodos = () => {
 
console.log("Pintando todo")
//Get para todos los libros:
nf(recurso + '/libros')
    .then(res => res.json())
    .then(json =>representaLibros(json));


   
}


//escuchador del boton buscar libro por autor

document.getElementById("btnBuscarAutor").addEventListener('click', () => {
    let txtBuscarAutor = document.getElementById("txtBuscarAutor").value;
    if (txtBuscarAutor == "") {
        let notification = document.querySelector("#notification");
        notification.innerHTML = "Debe escribir algo";
        notification.opened = true;
    } else {
       
    }
});
//escuchador del boton buscar libro por titulo
document.getElementById("btnBuscarTitulo").addEventListener('click', () => {
    console.log("buscando")
    let txtBuscar = document.getElementById("txtBuscarTitulo").value;
    if (txtBuscar == "") {
        let notification = document.querySelector("#notification");
        notification.innerHTML = "Debe escribir algo";
        notification.opened = true;
    } else {
        console.log(recurso+'/libros/'+txtBuscar)
        //buscamos el libro o libros
        nf(recurso+'/libros/'+txtBuscar)
        .then(res => res.json())
        .then(json => representaLibros(json));
    
    }
});
document.getElementById("btnNuevoLibro").addEventListener('click', () => {
    let txtNuevoTitulo = document.getElementById("txtNuevotitulo").value;
    let txtNuevoAutor = document.getElementById("txtNuevoAutor").value;
    let txtNuevaImagen = document.getElementById("txtNuevaImagen").value;

    if (txtNuevoTitulo == "" || txtNuevoAutor == "" || txtNuevaImagen == "") {
        let notification = document.querySelector("#notification2");
        notification.innerHTML = "Debe escribir algo";
        notification.opened = true;
    } else {
        //Insertamos el libro
        let nuevo = {
            title: txtNuevoTitulo,
            author: txtNuevoAutor,
            img: txtNuevaImagen
        };

        nf(recurso + '/libros', {
            method: 'post',
            body: JSON.stringify(nuevo),
            headers: { 'Content-Type': 'application/json' },
          })
            .then(res => res.json())
            .then(json => {
                let notification = document.querySelector("#notification2");
                 notification.innerHTML = "Libro Añadido";
                 notification.opened = true;
                 buscarTodos();
                console.log(json)}).catch(error => {
                    let notification = document.querySelector("#notification2");
                    notification.innerHTML = "NO se ha podido añadir el libro";
                    notification.opened = true;
                });;

    }
});
document.getElementById("btnBorrarLibro").addEventListener('click', () => {
    let txtBorrar = document.getElementById("txtBorrarTitulo").value;
    if (txtBorrar == "") {
        let notification = document.querySelector("#notification3");
        notification.innerHTML = "Debe escribir algo";
        notification.opened = true;
    } else {
        console.log(recurso+'/libros/'+txtBorrar)
        nf(recurso+'/libros/'+txtBorrar,{
            method:'delete',
            headers: { 'Content-Type': 'application/json' }

        }).then(result => {
            let notification = document.querySelector("#notification3");
            notification.innerHTML = "Libro Borrado";
            notification.opened = true;
        }).catch(error => {
            let notification = document.querySelector("#notification");
            notification.innerHTML = "NO se ha podido borrar el libro";
            notification.opened = true;
        });
        buscarTodos();
    }
})

//escuchador del boton buscar todos
document.getElementById("btnTodos").addEventListener('click', () => {
    buscarTodos();
});

document.getElementById("btnVer").addEventListener('click', () => {
    let txtVerTitulo = document.getElementById("txtVer").value;
    verLibro(txtVerTitulo)

});

function verLibro(title){
    let win
    
    nf(recurso+'/libros/'+title)
    .then(res => res.json())
    .then(json => ipcRenderer.send("verLibro",json))
    .then(()=>ipcRenderer.on("pintarLibro",(event,arg)=>
    {
        win=window.open();
        let resalta=  document.getElementById(arg.title).style.backgroundColor!= "lightgray"?`Resaltar ${arg.title}`:`Eliminar resalte`
        win.document.write(`<div class="libro">
            <img src="${recurso}/public/${arg.img}"/>
            <h1>El titulo del libro es <i>${arg.title}</i></h1>
            <h2>Su autor es ${arg.author}</h2>
            <button id="btnResaltar">
                ${resalta}
            </button>
            </div>`)
        win.document.head.innerHTML = `<link rel="stylesheet" href="./css/libro.css">`
        
       win.document.getElementById("btnResaltar").addEventListener('click', ()=>{
            resaltar(arg)
            
        })
        
        win.document.getElementById('click',()=>{

        })
    }));
    ipcRenderer.removeAllListeners()//para evitar que se repita la señal

    ipcRenderer.on("modLibro", (event, arg)=>{
        console.log(arg)
        let divLibro = document.getElementById(arg.title)
        if(divLibro.style.backgroundColor== "lightgray"){
            divLibro.style.backgroundColor= "transparent";
            win.document.getElementById("btnResaltar").innerHTML = `Resaltar ${arg.title}`
            
        }else{
            divLibro.style.backgroundColor= "lightgray";
            win.document.getElementById("btnResaltar").innerHTML = "Eliminar resalte"
        }
        
    })
 
}

function resaltar(arg){
    ipcRenderer.send("resaltarLibro", arg)
}

buscarTodos()