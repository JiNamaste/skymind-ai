import { useEffect, useState } from "react";
import axios from "axios";

function App() {

  const [message, setMessage] = useState("");

  useEffect(() => {

    axios.get("http://localhost:8080/api/test")
      .then((response) => {
        setMessage(response.data);
      })
      .catch((error) => {
        console.log(error);
      });

  }, []);

  return (
    <div>
      <h1>Frontend Connected</h1>
      <h2>{message}</h2>
    </div>
  );
}

export default App;