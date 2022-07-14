import { useState } from 'react'
import NotificationButton from './components/NotificationButton'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
   <h1>ola mundo</h1>
   <NotificationButton/>
   </>
  ) 
} 


export default App
