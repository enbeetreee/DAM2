const mongoose = require('mongoose')
const fs = require('fs')

mongoose.Promise = globalThis.Promise;
mongoose.connect('mongodb://localhost:27017/books');

let bookSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true,
        trim: true
    },
    author: {
        type: String,
        default: 'anonymous',
        trim: true
    },
    img: {
        type: String,
        trim: true
    }
})

let Book = mongoose.model('books', bookSchema)

function iniciarBD() {
    let books = JSON.parse(fs.readFileSync('./books.json'))
    let p1;

    books.forEach(b => {
        let book = new Book({
            title: b.title,
            author: b.author,
            img: b.img
        })

        p1 = book.save().then(res => {
            console.log("Libro añadido:", res)
        }).catch(err => {
            console.log("ERROR añadiendo contacto:", err)
        })
    });

    Promise.all([p1]).then(() => {
        mongoose.connection.close()
    })
}