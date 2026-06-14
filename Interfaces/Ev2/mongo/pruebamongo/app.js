const mongoose = require("mongoose")
mongoose.Promise = global.Promise
mongoose.connect('mongodb://localhost:27017/contactos')

let contactoSchema = new mongoose.Schema({
    nombre: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    telefono: {
        type: String,
        required: true,
        unique: true,
        trim: true,
        match: /^\d{9}$/
    },
    edad: {
        type: Number,
        min: 18,
        max: 120
    }
})

let Contacto = mongoose.model('contactos', contactoSchema)
let contacto1 = new Contacto({
    _id: '5ede78b4c5e89d072c3d1a71',
    nombre: 'Boris',
    telefono: 946112231,
    edad: 49,
    __v: 0
})

let p1 = contacto1.save().then(res => {
    console.log("Contacto añadido:", res)
}).catch(error => {
    console.log("ERROR añadiendo contacto:", error)
})

let p2 = Contacto.find().then(res => {
    console.log(res)
}).catch(error => {
    console.log("Error", error)
})

let p3 = Contacto.find({
    nombre: 'Boris',
    edad: 49
}).then(res => console.log(res)).catch(err => console.log("Error", err))
let p4 = Contacto.deleteOne({ nombre: 'Boris' }).then(resultado => {
    console.log(resultado);
}).catch(error => {
    console.log("ERROR:", error);
});

let p5 = Contacto.findOneAndUpdate(
    { nombre: 'Boris' },
    { nombre: 'Boris Anaya', edad: 50 },
    { new: true })
    .then(res => { console.log("Contacto actualizado:", res) })

/*
let p5 = Contacto.findByIdAndUpdate('5ede78b4c5e89d072c3d1a71',
    { nombre: 'Boris Anaya Moreno', edad: 51 }, { new: true })
    .then(resultado => {
        console.log("Modificado contacto:", resultado);
    }).catch(error => {
        console.log("ERROR:", error);
    });
*/

Promise.all([p1, p2, p3, p4, p5]).then(val => {
    mongoose.connection.close()
})