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
console.log("La suma de todos los primos hasta 50 es ${suma}");