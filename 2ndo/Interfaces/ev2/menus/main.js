const electron = require("electron");
const { dialog } = require('electron')
const { type } = require("os");
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;
const Menu = electron.Menu;

function createWindow() {
    const mainWindow = new BrowserWindow({
        width: 300,
        height: 450,
        webPreferences: {
            nodeIntegration: true,
            constextIsolation: false
        }
    })

    mainWindow.loadFile('index.html')

    //mainWindow.webContents.openDevTools()
}

app.on('ready', function () {
    createWindow();
    const template = [{
        label: 'Casa',
        submenu: [
            {
                label: 'Habitaciones',
                submenu: [
                    {
                        label: 'Habitacion 1',
                        click: function () { dialogo("la habitación 1") }
                    },
                    {
                        label: 'Habitacion 2',
                        click: function () { dialogo("la habitación 2") }
                    }
                ]
            },
            {
                label: 'Salón',
                accelerator: 'CmdOrCtrl + S',
                click: function () { dialogo("el salón") }
            },
            { type: 'separator' },
            {
                label: 'Cocina',
                accelerator: 'CmdOrCtrl + C',
                click: function () { dialogo("la cocina") }
            },
            {
                label: 'Baño',
                accelerator: 'CmdOrCtrl + B',
                click: function () { dialogo("el baño") }
            }
        ]
    },
    {
        label: 'Extras',
        submenu: [
            {
                label: 'Garaje',
                accelerator: 'Alt + G',
                click: function () { dialogo("el garaje") }
            },
            {
                label: 'Trastero',
                accelerator: 'CmdOrCtrl + T',
                click: function () { dialogo("el trastero") }
            }
        ]
    },
    {
        label:'Descripción',
        click: function(){
            desc();
        }
    }];
    const menu = Menu.buildFromTemplate(template);
    Menu.setApplicationMenu(menu);
});


let log = "";
function dialogo(s) {
    dialog.showMessageBoxSync({ message: "Has entrado en " + s });
    log+= "Se ha hecho click en "+s+"\n";
}
function desc(){
    dialog.showMessageBoxSync({ message: log });
}