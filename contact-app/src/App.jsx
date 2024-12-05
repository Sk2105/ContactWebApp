import { BrowserRouter, Routes, Route } from 'react-router-dom'
import HomePage from './components/HomePage'
import AddContactPage from './components/AddContactPage'
import ContactView from './components/ContactView'

function App() {

  return (

    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomePage/>} />
        <Route path='/addContact' element={<AddContactPage/>} />
        <Route path='/:id' element={<ContactView/>} />
        <Route path='/edit/:id' element={<AddContactPage/>} />
      </Routes>
    </BrowserRouter>

  )
}

export default App
