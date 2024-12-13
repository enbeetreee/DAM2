function arrow(){
let cansado = true;
let aburrido = false;
let siesta = (var1, var2) =>{ (var1||var2)? console.log(true): console.log(false) };
siesta(true,false);
}

function object(){
    let user = {
    };
    user.name = "John";
    user.surname = "Smith";
    alert(user.name);
    user.name = "Pete";
    delete(user.name);
    alert(user.name);
}