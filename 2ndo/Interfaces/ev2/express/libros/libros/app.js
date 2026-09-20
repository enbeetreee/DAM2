const express = require('express');
const { readFileSync } = require('fs');
const app = express()
const port = 8080

const books = JSON.parse(readFileSync("./libros/books.json"));

app.get('/libros', (req, res) => {
    res.send(books)
});

app.use('/public', express.static(__dirname+'/public'))

app.listen(port, () => {
  console.log(`Listening on port ${port}`)
})
