const express = require('express')
const app = express()
const port = 3000

const products = [
    {"id":1, "name":"a"},
    {"id":2, "name":"b"},
    {"id":3, "name":"c"}
]

app.get('/', (req, res) => {
  res.send('Hello World!')
});

app.get('/products', (req, res) => {
    res.json(products);
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
