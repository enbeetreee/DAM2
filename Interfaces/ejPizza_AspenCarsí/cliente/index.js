const pdf = require('html-pdf');
const { dialog } = require('@electron/remote')

let http = require('http')
let data = ""
let options = {
	host: '127.0.0.1',
	port: 3000,
	path: '/'
}


let ingredientes
let tipoPiza = {
	"tamaño": 0,
	"masa": 0,
	"ingredientes": []
}

let request = http.get(options, function (res) {
	// Un fragmento de datos ha sido recibido.
	res.on('data', function (rparcial) {
		data += rparcial
	})

	// Toda la respuesta ha sido recibida. Imprimir el resultado.
	res.on('end', function () {
		data = JSON.parse(data)
		ingredientes = data
		printIngredientes()

		printPrecio()
		initListeners()
	})
	//en caso de error
	res.on('error', function (e) {
		console.log("There was an error: " + e.message)
		dialog.showErrorBox({ title: "Error", message: err.message })
	})
})

function printIngredientes() {
	let cad = "";
	for (let i = 0; i < ingredientes.ingredientes.length; i++) {
		cad += `<div class="checkbox"><label><input type="checkbox" id="ingrediente${i}" value="${ingredientes.ingredientes[i].nombre}">${ingredientes.ingredientes[i].nombre}</label></div>`;
	}
	document.getElementById("ingredientes").innerHTML = cad;

	for (let i = 0; i < ingredientes.ingredientes.length; i++) {
		document.getElementById(`ingrediente${i}`).addEventListener('click', () => {
			if (document.getElementById(`ingrediente${i}`).checked) {
				tipoPiza.ingredientes.push(ingredientes.ingredientes[i])
			} else {
				tipoPiza.ingredientes = tipoPiza.ingredientes.filter(i => i == ingredientes.ingredientes[i])
			}
			printPrecio()
		})

	}
}
function initListeners() {
	document.getElementById("aceptar")
		.addEventListener('click', () => {
			if (checkDatos()) {
				let nombre = document.getElementById("nombre").value

				//recuperar elementos fijos
				let content = '<h1>Pedido</h1>' +
					'<h1>Nombre:' + document.getElementById("nombre").value + '<h1>' +
					'<h1>Teléfono:' + document.getElementById("telefono").value + '<h1>' +
					'<h1>Dirección:' + document.getElementById("direccion").value + '<h1>' +
					'<h1>Tamaño:' + ingredientes.tamaño[tipoPiza.tamaño].nombre + '<h1>' +
					'<h1>Masa:' + ingredientes.masa[tipoPiza.masa].masa + '<h1>' +
					'<h1> Lista de ingredientes: <h1>';
				for (let i = 0; i < tipoPiza.ingredientes.length; i++) {
					content += '<h1>' + tipoPiza.ingredientes[i][0] + '<h1>';
				}
				content += '<h1>Precio:' + precio + '<h1>'
				let fecha = new Date()
				console.log(content)
				nombre = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate() + "_" + nombre
				dialog.showMessageBoxSync({ title: "Atención", message: "Creando el pedido" })
				pdf.create(content).toFile('./pdfpedidos/' + nombre + '.pdf', function (err, res) {
					if (err) {
						console.log(err);
						dialog.showErrorBox({ title: "Error", message: err.message })
					} else {
						setTimeout(dialog.showMessageBoxSync({ title: "Atención", message: "Pedido guardado" }), 500)
						console.log(res);
					}

				});
			}

		});
	document.getElementById("cancelar").addEventListener('click', () => {
		document.getElementById("nombre").value = ""
		document.getElementById("telefono").value = ""
		document.getElementById("direccion").value = ""
		tipoPiza = {
			"tamaño": 0,
			"masa": 0,
			"ingredientes": []
		}
		document.getElementById(`tamaño${1}`).checked = true
		document.getElementById(`masa${1}`).checked = true

		for (let i = 0; i < ingredientes.ingredientes.length; i++) {
			document.getElementById(`ingrediente${i}`).checked = false;
	
		}

	})

	for (let i = 1; i <= 3; i++) {
		document.getElementById(`tamaño${i}`).addEventListener('click', () => {
			if (document.getElementById(`tamaño${i}`)) {
				tipoPiza.tamaño = i - 1
			}
			printPrecio()
		})
	}
	for (let i = 1; i <= 2; i++) {
		document.getElementById(`masa${i}`).addEventListener('click', () => {
			if (document.getElementById(`masa${i}`)) {
				tipoPiza.masa = i - 1
			}
			printPrecio()
		})
	}

}

function printPrecio() {
	let precio = 0
	console.log(tipoPiza)
	precio += ingredientes.tamaño[tipoPiza.tamaño].precio
	precio += ingredientes.masa[tipoPiza.masa].precio
	tipoPiza.ingredientes.forEach((e) => {
		precio += e.precio
	})
	document.getElementById("precio").innerHTML = precio.toString()
}

function checkDatos() {
	try {
		if (document.getElementById("nombre").value == "") {
			throw new Error("El nombre no puede estar vacío")
		}
		if (document.getElementById("telefono").value == "") {
			throw new Error("El telefono no puede estar vacío")
		}
		if (document.getElementById("direccion").value == "") {
			throw new Error("El telefono no puede estar vacío")
		}
	} catch (error) {
		dialog.showMessageBoxSync({ title: "Error", message: error.message })
		return false
	}
	return true
}
