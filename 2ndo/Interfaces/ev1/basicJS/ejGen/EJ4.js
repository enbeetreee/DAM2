function ej1(){
    var Factura  = {
        nom: "",
        dir: "",
        tel: "",
        NIF: "",
        client: new Client(),
        lista: [],
    };
    var Client = {
        nom: "",
        dir: "",
        tel: "",
        NIF: ""
    };
    var Element = {
        preu: 0,
        quantitat: 0,
        desc: ""
    };
    function inDatos(f1){
        let elementos = true;
        f1.client = inCliente();
        f1.nom = prompt();
        f1.dir = prompt();
        f1.tel = prompt();
        f1.NIF = prompt();
        while (elementos) {
            alert("Datos del elemento");
            f1.lista.add(inElemento());
            elementos = prompt("Continuar introduciendo elementos? (s/n)")=="s"?true:false;
        }
    }
    function inElemento(){
        let preu = prompt();
        let quantitat = prompt();
        let desc = prompt();
        return new Element(preu, quantitat, desc);
    }
    function inCliente(){
        let nom = prompt();
        let dir = prompt();
        let tel = prompt();
        let NIF = prompt();
        return new Client(nom, dir, tel, NIF);
    }
    let f1 = new Factura();
    inDatos(f1);
}

function ej2(){
    var Animal = {
        nom: "",
        especie: "",
        nPotes : 0,
        cua : false
    };
    var Vaca = {

    }
}