
const fs = require('fs')
const { MongoClient, Double } = require('mongodb');
const { title } = require('process');


const url = 'mongodb://localhost:27017';
const client = new MongoClient(url);
const dbName = 'libros';
const colName = 'libros';
let findResult = null
let editando = false
let elemEditado = null


main()


async function main() {

    // Use connect method to connect to the server
    await client.connect();
    console.log('Connected successfully to server');
    const db = client.db(dbName);
    const collection = db.collection(colName);
    //insert. Note, if collection or db no exist, it's created

    let promesas = []
    /**
         Iván: como ahora la funcion iniciarBD ya devuelve un array con las promesas que ha creado
         el array promesas tiene todas las ejecutadas dentro del bucle de la función
     */

   let p1= collection.find().toArray()
    .then(values => {//comprueba si hay datos, si no inserta books.json
        if (values.length == 0) {
            return iniciarBD();
        }
    })

    promesas.push(p1)
    
    async function iniciarBD() {
        let books = JSON.parse(fs.readFileSync('./books.json'));

        /**
            Iván: Creamos un array para guardar las inserciones
        */
        let inserciones = [];

        // Devolvemos 
        return collection.drop().then(()=>{
            books.forEach(b => {
                let newBook = {
                    title: b.title,
                    author: b.author,
                    img: b.img
                }
                
                let p = collection.insertOne(newBook).then(() => {
                    console.log("Insertado");
                }).catch(err => {
                    console.log(err);
                });

                inserciones.push(p);
            });

            /**
                Iván: esperamos a que se completen todas las inserciones y las devolvemos tras la ejecución
                de la funcion iniciarBD()
            */
            return Promise.all(inserciones);
        })

    }



    Promise.all(promesas).then(values => {
        collection.find({}).toArray().then(values => {
            findResult = values.sort((a, b) => { return a.title > b.title ? 1 : -1 })
            console.log('Found documents =>', findResult);
            printTabla()
        }
        )
    }).catch(err => {
        console.log(err)
    });
}




function printTabla() {

    let inTabla = "";

    for (let i = 0; i < findResult.length; i++) {
        inTabla += `<tr >
        <td id="title${i}">${findResult[i].title}</td>
        <td id="author${i}">${findResult[i].author}</td>
        <td id="img${i}">${findResult[i].img}</td>
        <td id="del${i}">&#10006;</td>
        </tr>`;

    }

    document.getElementById("tabla").innerHTML = inTabla;
    for (let i = 0; i < findResult.length; i++) {
        editarListeners(i);
    }
}

//Updates
function editar(el, i) {
    editando = true
    elemEditado = { id: `${el}${i}`, i: i, att: el }
    let elem = document.getElementById(elemEditado.id)
    let value = elem.innerHTML
    elem.innerHTML = `<input autofocus type="text" id="edit" value="${value}"/>`
    elem.addEventListener('keypress', (event) => {
        if (event.key == "Enter" && editando) {
            inputEditar();
        }
    })

}


function inputEditar() {
    let update
    let newValue = document.getElementById("edit").value
    let elem = document.getElementById(elemEditado.id)

    elem.innerHTML = newValue

    editando = false

    if (elemEditado.att == "title") {
        update = { $set: { title: newValue } }
    } else if (elemEditado.att == "author") {
        update = { $set: { author: newValue } }
    } else {
        update = { $set: { img: newValue } }

    }

    const db = client.db(dbName);
    const collection = db.collection(colName);

    collection.findOneAndUpdate(
        { title: findResult[elemEditado.i].title },
        update

    ).then((resultado) => { console.log("Contacto Actualizado", resultado) })
    findResult[elemEditado.i].title = newValue

}


//Busquedas
function buscar() {
    const db = client.db(dbName);
    const collection = db.collection(colName);
    let newDoc = getDoc();
    collection.find(
        newDoc
    ).toArray().then(values => {

        findResult = values.sort((a, b) => { return a.title > b.title ? 1 : -1 })
        console.log('Found documents =>', findResult);
        printTabla()


    }
    )

}

function getDoc() {
    let tit = document.getElementById("newTitle")
    let au = document.getElementById("newAuthor")
    let img = document.getElementById("newImg")
    let newDoc = {};
    if (tit.value != "") {
        newDoc.title = tit.value
    }
    if (au.value != "") {
        newDoc.author = au.value
    }
    if (img.value != "") {
        newDoc.img = img.value
    }
    tit.value = ""
    au.value = ""
    img.value = ""
    return newDoc
}

//Listeners
function editarListeners(i) {
    let tit = `title${i}`
    document.getElementById(tit).addEventListener('click', () => {

        if (!editando) {
            editar("title", i)
        } else {
            if (tit != elemEditado.id) {
                inputEditar();
                editar("title", i)
            }
        }

    })
    let au = `author${i}`
    document.getElementById(au).addEventListener('click', () => {
        if (!editando) {
            editar("author", i);
        } else {
            if (au != elemEditado.id) {
                inputEditar();
                editar("author", i);
            }

        }
    })
    let img = `img${i}`
    document.getElementById(img).addEventListener('click', () => {
        if (!editando) {
            editar("img", i);
        } else {
            if (img != elemEditado.id) {
                inputEditar();
                editar("img", i);
            }

        }
    })

    let del = `del${i}`
    document.getElementById(del).addEventListener('click', () => {
        deleteBook(i)
    })

}
document.getElementById("btn-buscar").addEventListener('click', () => {
    buscar()
})
document.getElementById("btn-crear").addEventListener('click', () => {
    const db = client.db(dbName);
    const collection = db.collection(colName);

    let newDoc = getDoc();

    collection.insertOne(
        newDoc
    ).then(values => {
        findResult = values
        console.log('Created documents =>', findResult);
        printTabla()
        buscar()
    }
    )


})
function deleteBook(i) {
    const db = client.db(dbName);
    const collection = db.collection(colName);

    let newDoc = getDoc();

    collection.deleteOne(
        { title: findResult[i].title }
    ).then(values => {
        findResult = values
        console.log('Deleted document =>', findResult);
        printTabla()
        buscar();
    })
}