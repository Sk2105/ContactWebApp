import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'


export default function AddContactPage() {
  const { id } = useParams();
  const [contact, setContact] = useState({});

  useEffect(() => {
    if (id) {
      fetchContact();
    }
  }, [id]);

  const fetchContact = async () => {
    try {
      const res = await fetch(`http://localhost:8080/contact/${id}`);
      if (!res.ok) throw new Error("Failed to fetch contact");
      const data = await res.json();
      setContact(data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleSubmit = async () => {
    const url = id ? `http://localhost:8080/contact/${id}` : "http://localhost:8080/contact";
    const method = id ? "PUT" : "POST";

    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(contact),
      });
      if (!res.ok) throw new Error("Network response was not ok");
      window.location.href = "/";
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleChange = (e) => {
    setContact({ ...contact, [e.target.name]: e.target.value });
  };

  return (
    <div className='w-full h-screen flex justify-center items-center bg-sky-200 flex-col'>
      <div className='w-1/2 h-fit bg-white rounded-xl flex justify-center items-center flex-col shadow-xl p-4'>
        <h1 className='w-full text-center p-2 text-xl font-bold'>{id ? "Update Contact" : "Add New Contact"}</h1>
        <div className='w-full grid grid-cols-2 justify-around m-2 gap-4'>
          {["name", "email", "phone", "address", "age"].map((field) => (
            <input
              key={field}
              type={field === "email" ? "email" : field === "age" ? "number" : "text"}
              name={field}
              id={field}
              placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
              className='p-2 border text-[16px] rounded-md border-black'
              onChange={handleChange}
              value={contact[field] || ""}
            />
          ))}
        </div>
        <button onClick={handleSubmit} className='w-1/3 m-2 shadow-md bg-sky-600 p-2 hover:bg-sky-900 text-white rounded-md'>
          {id ? "Update Contact" : "Add New Contact"}
        </button>
      </div>
    </div>
  );
}
