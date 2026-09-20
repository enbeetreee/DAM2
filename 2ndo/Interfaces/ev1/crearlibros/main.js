const { app, BrowserWindow } = require('electron')

require('@electron/remote/main').initialize()
const { dialog } = require('electron')

function createWindow() {
    // Crea la ventana del navegador.
    let win = new BrowserWindow({
        width: 800,
        height: 700,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            enableRemoteModule: true
        }
    })
    // y carga el index.html de la aplicación.

    win.loadFile('index.html')

    win.setResizable(false);
    require("@electron/remote/main").enable(win.webContents);
    //para mostrar en la ventana la herramientas de desarrollo de chrome:
    //win.webContents.openDevTools()
}
//cuando la aplicación electron está lista (todos los procesos generados)
//mediante app.on llamamos a la función que se va ha encargar de lanzar las
//ventanas:
app.on('ready', createWindow)
