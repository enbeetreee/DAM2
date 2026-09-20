const { app, BrowserWindow } = require('electron')
function createWindow() {
    // Crea la ventana del navegador.
    let win = new BrowserWindow({
        width: 870,
        height: 870,
        margin: 0,
        padding:0,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,

        }
    })
    // y carga el index.html de la aplicación.
    win.loadFile('index.html')
    //para mostrar en la ventana la herramientas de desarrollo de chrome:
    //win.webContents.openDevTools()
}
//cuando la aplicación electron está lista (todos los procesos generados)
//mediante app.on llamamos a la función que se va ha encargar de lanzar las
//ventanas:
app.on('ready', createWindow)