function concatenar(){
    let c1 = prompt("Introduce cadena 1");
    let c2 = prompt("Introduce cadena 2");
    console.log(c1);
    console.log(c2);
    console.log(c1.concat(c2));
}
function tablas(){
    let m;
    for(i = 0; i<10; i++){
        for(j = 0; j<10; j++){
            m=i*j;
            document.write(i+" * "+j+" = "+m+"<br>");
            }
        document.write("_________ <br>");
    }
}
function pares(){
    for(i = 1; i<=100; i++){
        if(i%2==0){
            console.log(i);
        }
    }
}
function primosHasta50(){
    let suma=0;
    let primo;
    let lista="Los números primos hasta 50 son ";
    for(i = 1; i<50; i++){
       primo = true;
        for(j = 2; j<i; j++){
           if(i%j ==0){
                primo = false;
            }
        }
        if(primo){
            lista+=+" "+i+",";
            suma+=i;
        }
    }
    console.log(lista.substring(0,lista.length()-1)+".");
    console.log("La suma de todos los primos hasta 50 es "+suma);
}