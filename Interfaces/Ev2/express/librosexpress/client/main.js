const { app, BrowserWindow, ipcMain } = require('electron')

function openWindow() {
  let newwin = new BrowserWindow({
    width: 800,
    height: 630,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false
    }
  })
  newwin.setMenu(null);
  newwin.loadFile('index.html')
  newwin.setAutoHideMenuBar(true)
  newwin.webContents.openDevTools()
}


function openWindow2(){
let newwin2 = new BrowserWindow({
  width: 800,
  height: 630,
  webPreferences: {
    nodeIntegration: true,
    contextIsolation: false
  }
})
newwin2.setMenu(null)
newwin2.loadFile('libro.html')
newwin2.setAutoHideMenuBar(true)
newwin2.webContents.openDevTools()
}

app.on('ready', ()=>{
  openWindow()
  ipcMain.on("verLibro",(event,arg)=>{

    event.sender.send("pintarLibro",arg[0])
    
  })
  ipcMain.on("resaltarLibro", (event, arg)=>{
    event.sender.send("modLibro",arg)
  })
  
})