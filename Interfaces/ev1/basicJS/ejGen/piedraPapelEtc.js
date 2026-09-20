let gana;
let u = prompt("Piedra(1), papel(2) o tijeras(3)")
let c = Math.floor(Math.random() * 3) + 1;
let s = ['piedra', 'papel', 'tijeras'];
document.write('Has sacado ' + s.at(u - 1) + ' contra ' + s.at(c - 1) + ', ');
if (u == c) {
    document.write("ha habido un empate.");
} else {
    switch (c) {
        case 1:
            gana = u == 2 ? true : false;
            break;
        case 2:
            gana = u == 3 ? true : false;
            break;
        case 3:
            gana = u == 1 ? true : false;
            break;
    }
    document.write(gana ? 'has ganado' : 'has perdido' + '.');
}