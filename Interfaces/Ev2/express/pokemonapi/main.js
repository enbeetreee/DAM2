// Modules to control application life and create native browser window
"use strict";
const { app, BrowserWindow, dialog } = require('electron');
require('@electron/remote/main').initialize();
let mainWindow;
function createWindow() {
// Create the browser window.
mainWindow = new BrowserWindow({
width: 800,
height: 600,
webPreferences: {
nodeIntegration: true,
contextIsolation: false
}
});
require("@electron/remote/main").enable(mainWindow.webContents);
// and load the index.html of the app.
mainWindow.loadFile('index.html');
// Open the DevTools.
mainWindow.webContents.openDevTools()
mainWindow.setMenu(null);
// Emitted when the window is closed.
mainWindow.on('closed', function () {
mainWindow = null;
})
}
// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow);