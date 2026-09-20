let http = require('http')
const fs = require('fs')

//leer clientes del archivo
let fichero = fs.readFileSync('./file/ingredientes.json');
ingredientes = JSON.parse(fichero);

//Crearemos un servidor que lo único que hace es enviar clientes.son
http.createServer(function (req, res) {
  res.writeHead(200, { 'Content-Type': 'application/json' })
  res.end(JSON.stringify(ingredientes))
}).listen(3000, "127.0.0.1")
console.log('Server running at http://127.0.0.1:3000/')


/*app.post('/pedido', (req,res)=>{
  let nuevoPedido =req.body;
  let content = '<h1>Pedido</h1>' +
		'<h1>Nombre:' + nuevoPedido.nombre + '<h1>' +
		'<h1>Teléfono:' + nuevoPedido.telefono + '<h1>' +
		'<h1>Dirección:' + nuevoPedido.direccion + '<h1>' +
		'<h1>Tamaño:' + nuevoPedido.tamaño + '<h1>' +
		'<h1>Masa:' + nuevoPedido.masa + '<h1>' +
		'<h1> Lista de ingredientes: <h1>';
	for (let i = 0; i < seleccionados.length; i++) {
		content += '<h1>' + seleccionados[i] + '<h1>';
	}
	pdf.create(content).toFile('./pdfpedidos/' + nombre + '.pdf', function (errPDF, resPDF) {
		if (err) {
      		res.send(err);
			console.log(err);
			//dialog.showErrorBox({title:"Error", message:err.message})
		} else {
      		res.send(resPDF)
			//setTimeout(dialog.showMessageBoxSync({title:"Atención",message:"Pedido guardado"}),1000)
			console.log(res);
		}
	});
  
})*/
